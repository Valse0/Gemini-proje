package com.luma.translate.pipeline

import android.graphics.Bitmap
import android.util.Log
import com.luma.translate.BuildConfig
import com.luma.translate.ocr.NormalizedRect
import com.luma.translate.ocr.OcrManager
import com.luma.translate.ocr.OcrResult
import com.luma.translate.translation.TranslationRequest
import com.luma.translate.translation.TranslationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.concurrent.TimeUnit

/**
 * End-to-End Pipeline Result containing individual translated text regions
 */
data class TranslatedRegion(
    val id: String,
    val originalText: String,
    val translatedText: String,
    val boundingBox: NormalizedRect,
    val confidence: Float
)

/**
 * Latency Profiling Metrics for Phase 5 Performance Tracking
 */
data class PipelineMetrics(
    val captureTimeMs: Long,
    val ocrTimeMs: Long,
    val networkTimeMs: Long,
    val totalTimeMs: Long
)

/**
 * Pipeline Execution Outcome
 */
sealed class PipelineResult {
    data class Success(
        val regions: List<TranslatedRegion>,
        val sourceLanguage: String,
        val targetLanguage: String,
        val metrics: PipelineMetrics
    ) : PipelineResult()

    object EmptyOcr : PipelineResult()
    /** Classified, user-presentable failure (no raw exception text). */
    data class Failure(val kind: FailureKind) : PipelineResult() {
        val message: String get() = kind.userMessage
    }
    data class NetworkError(val message: String) : PipelineResult()
    data class GeneralError(val error: Throwable) : PipelineResult()
}

/**
 * Orchestrates the complete end-to-end translation pipeline:
 * MediaProjection Frame -> On-Device ML Kit OCR -> Secure Backend Translation -> Overlay Regions
 */
class TranslationPipeline(
    private val ocrManager: OcrManager,
    // Was hardcoded to the Android-emulator-only loopback alias
    // "http://10.0.2.2:3000" with no way to override it for a real device
    // or a non-local backend. Now sourced from BuildConfig.BACKEND_BASE_URL,
    // which is itself configurable per build (see app/build.gradle.kts /
    // -PLUMA_BACKEND_URL / android/local.properties) without touching code.
    private val backendBaseUrlProvider: () -> String = { BuildConfig.BACKEND_BASE_URL },
    private val isEmulator: Boolean = false,
    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()
) {

    /**
     * Executes the complete translation loop for a captured screen bitmap.
     * All vision operations remain strictly on-device.
     */
    suspend fun execute(
        bitmap: Bitmap,
        sourceLanguage: String = "ja",
        targetLanguage: String = "en",
        context: String = "gaming"
    ): PipelineResult = withContext(Dispatchers.IO) {
        val totalStart = System.currentTimeMillis()

        // Fail fast, with the real reason, when the configured address is the
        // emulator-only alias but we're on a physical phone (see
        // TranslationFailure.kt: this is exactly what Hata1/2/4 showed).
        val baseUrl = backendBaseUrlProvider().trim().trimEnd('/')
        if (baseUrl.isEmpty() || isEmulatorOnlyUrlOnRealDevice(baseUrl, isEmulator)) {
            return@withContext PipelineResult.Failure(FailureKind.NOT_CONFIGURED)
        }

        try {
            // 1. On-Device OCR Step
            val ocrStart = System.currentTimeMillis()
            val ocrResult: OcrResult = ocrManager.processImage(bitmap, sourceLanguage)
            val ocrDuration = System.currentTimeMillis() - ocrStart

            if (ocrResult.isEmpty) {
                return@withContext PipelineResult.EmptyOcr
            }

            // 2. Dispatch Text-Only Payload to Backend
            val netStart = System.currentTimeMillis()
            val translatedRegions = mutableListOf<TranslatedRegion>()

            for (block in ocrResult.blocks) {
                // Empty OCR blocks are skipped; text beyond the backend's
                // MAX_TEXT_LENGTH (2000) is trimmed instead of triggering a
                // guaranteed 400 for the whole screen.
                val text = block.text.trim().take(MAX_TEXT_LENGTH)
                if (text.isEmpty()) continue
                val translation = translateTextViaBackend(
                    baseUrl = baseUrl,
                    text = text,
                    sourceLanguage = sourceLanguage,
                    targetLanguage = targetLanguage,
                    context = context
                )

                translatedRegions.add(
                    TranslatedRegion(
                        id = block.id,
                        originalText = text,
                        translatedText = translation.translatedText,
                        boundingBox = block.boundingBox,
                        confidence = block.confidence
                    )
                )
            }
            if (translatedRegions.isEmpty()) return@withContext PipelineResult.EmptyOcr
            val netDuration = System.currentTimeMillis() - netStart
            val totalDuration = System.currentTimeMillis() - totalStart

            val metrics = PipelineMetrics(
                captureTimeMs = 15L, // local bitmap copy
                ocrTimeMs = ocrDuration,
                networkTimeMs = netDuration,
                totalTimeMs = totalDuration
            )

            PipelineResult.Success(
                regions = translatedRegions,
                sourceLanguage = sourceLanguage,
                targetLanguage = targetLanguage,
                metrics = metrics
            )
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            // Class name only: exception messages can contain hosts, ports
            // or (for parse errors) response/screen text.
            Log.w(TAG, "Translation failed: ${e.javaClass.simpleName}")
            PipelineResult.Failure(TranslationFailureClassifier.classify(e))
        }
    }

    private fun translateTextViaBackend(
        baseUrl: String,
        text: String,
        sourceLanguage: String,
        targetLanguage: String,
        context: String
    ): TranslationResponse {
        val jsonBody = JSONObject().apply {
            put("text", text)
            put("sourceLanguage", sourceLanguage)
            put("targetLanguage", targetLanguage)
            put("context", context)
        }

        val request = Request.Builder()
            .url("$baseUrl/v1/translate")
            .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
            .build()

        // `use` closes the response body on every path (no leaked connections).
        httpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw BackendHttpException(response.code)
            val body = response.body?.string()
            if (body.isNullOrBlank()) throw BadBackendResponseException()
            try {
                val resJson = JSONObject(body)
                return TranslationResponse(
                    translatedText = resJson.getString("translatedText"),
                    detectedSourceLanguage = resJson.optString("detectedSourceLanguage", sourceLanguage),
                    targetLanguage = resJson.optString("targetLanguage", targetLanguage),
                    provider = resJson.optString("provider", ""),
                    durationMs = resJson.optLong("durationMs", 0L)
                )
            } catch (e: org.json.JSONException) {
                throw BadBackendResponseException(e)
            }
        }
    }

    private companion object {
        const val TAG = "TranslationPipeline"
        const val MAX_TEXT_LENGTH = 2000
    }
}
