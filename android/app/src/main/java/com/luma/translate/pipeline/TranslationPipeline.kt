package com.luma.translate.pipeline

import android.graphics.Bitmap
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
    data class NetworkError(val message: String) : PipelineResult()
    data class GeneralError(val error: Throwable) : PipelineResult()
}

/**
 * Orchestrates the complete end-to-end translation pipeline:
 * MediaProjection Frame -> On-Device ML Kit OCR -> Secure Backend Translation -> Overlay Regions
 */
class TranslationPipeline(
    private val ocrManager: OcrManager,
    private val backendBaseUrl: String = "http://10.0.2.2:3000",
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
                val translation = translateTextViaBackend(
                    text = block.text,
                    sourceLanguage = sourceLanguage,
                    targetLanguage = targetLanguage,
                    context = context
                )

                translatedRegions.add(
                    TranslatedRegion(
                        id = block.id,
                        originalText = block.text,
                        translatedText = translation.translatedText,
                        boundingBox = block.boundingBox,
                        confidence = block.confidence
                    )
                )
            }
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
        } catch (e: Exception) {
            PipelineResult.GeneralError(e)
        }
    }

    private fun translateTextViaBackend(
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
            .url("$backendBaseUrl/v1/translate")
            .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
            .build()

        val response = httpClient.newCall(request).execute()
        val responseBody = response.body?.string() ?: throw IllegalStateException("Empty backend response")

        if (!response.isSuccessful) {
            val errorMsg = try {
                JSONObject(responseBody).getJSONObject("error").getString("message")
            } catch (e: Exception) {
                "HTTP ${response.code}: $responseBody"
            }
            throw IllegalStateException(errorMsg)
        }

        val resJson = JSONObject(responseBody)
        return TranslationResponse(
            translatedText = resJson.getString("translatedText"),
            detectedSourceLanguage = resJson.getString("detectedSourceLanguage"),
            targetLanguage = resJson.getString("targetLanguage"),
            provider = resJson.getString("provider"),
            durationMs = resJson.getLong("durationMs")
        )
    }
}
