package com.luma.translate.pipeline

import android.graphics.Bitmap
import android.util.Log
import com.luma.translate.ocr.NormalizedRect
import com.luma.translate.ocr.OcrManager
import com.luma.translate.ocr.OcrResult
import com.luma.translate.translation.TextTranslator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
    /** Kept for API compatibility; the on-device pipeline no longer produces it. */
    data class NetworkError(val message: String) : PipelineResult()
    data class GeneralError(val error: Throwable) : PipelineResult()
}

/**
 * Orchestrates the complete on-device translation pipeline:
 * MediaProjection frame -> ML Kit OCR -> ML Kit on-device translation -> overlay regions.
 * Nothing in this chain uses the network; recognized text never leaves the device.
 */
class TranslationPipeline(
    private val ocrManager: OcrManager,
    private val translator: TextTranslator
) {

    /**
     * Executes the translation loop for a captured screen bitmap.
     * OCR and translation both run on this device.
     */
    suspend fun execute(
        bitmap: Bitmap,
        sourceLanguage: String = "ja",
        targetLanguage: String = "en"
    ): PipelineResult = withContext(Dispatchers.Default) {
        val totalStart = System.currentTimeMillis()
        try {
            // 1. On-device OCR
            val ocrStart = System.currentTimeMillis()
            val ocrResult: OcrResult = ocrManager.processImage(bitmap, sourceLanguage)
            val ocrDuration = System.currentTimeMillis() - ocrStart

            if (ocrResult.isEmpty) {
                return@withContext PipelineResult.EmptyOcr
            }

            // 2. On-device translation, block by block
            val translateStart = System.currentTimeMillis()
            val translatedRegions = mutableListOf<TranslatedRegion>()
            for (block in ocrResult.blocks) {
                val text = block.text.trim().take(MAX_TEXT_LENGTH)
                if (text.isEmpty()) continue
                val translated = translator.translate(text, sourceLanguage, targetLanguage)
                translatedRegions.add(
                    TranslatedRegion(
                        id = block.id,
                        originalText = text,
                        translatedText = translated,
                        boundingBox = block.boundingBox,
                        confidence = block.confidence
                    )
                )
            }
            if (translatedRegions.isEmpty()) return@withContext PipelineResult.EmptyOcr
            val translateDuration = System.currentTimeMillis() - translateStart

            PipelineResult.Success(
                regions = translatedRegions,
                sourceLanguage = sourceLanguage,
                targetLanguage = targetLanguage,
                metrics = PipelineMetrics(
                    captureTimeMs = 0L,
                    ocrTimeMs = ocrDuration,
                    // Name kept for contract stability: this is now the on-device
                    // translation step's duration (no network involved).
                    networkTimeMs = translateDuration,
                    totalTimeMs = System.currentTimeMillis() - totalStart
                )
            )
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            // Class name only: exception messages could contain screen text.
            Log.w(TAG, "Translation failed: ${e.javaClass.simpleName}")
            PipelineResult.Failure(TranslationFailureClassifier.classify(e))
        }
    }

    private companion object {
        const val TAG = "TranslationPipeline"
        const val MAX_TEXT_LENGTH = 2000
    }
}
