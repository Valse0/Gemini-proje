package com.luma.translate.ocr

import android.graphics.Bitmap
import android.graphics.Rect
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognizer
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * On-Device OCR Manager
 * Processes screen captures completely locally via Google ML Kit
 */
class OcrManager {

    private var currentLanguage: String? = null
    private var activeRecognizer: TextRecognizer? = null

    /**
     * Get or dynamically initialize the recognizer for the specified language
     */
    @Synchronized
    private fun getRecognizer(language: String): TextRecognizer {
        if (activeRecognizer == null || currentLanguage != language) {
            activeRecognizer?.close()
            currentLanguage = language
            activeRecognizer = LanguageRecognizerFactory.createRecognizer(language)
        }
        return activeRecognizer!!
    }

    /**
     * Process an Android Bitmap on-device and return normalized text blocks
     */
    suspend fun processImage(bitmap: Bitmap, language: String): OcrResult {
        val startTime = System.currentTimeMillis()
        val width = bitmap.width
        val height = bitmap.height

        if (width <= 0 || height <= 0) {
            return OcrResult(
                blocks = emptyList(),
                imageWidth = 0,
                imageHeight = 0,
                processingTimeMs = 0,
                detectedLanguage = language
            )
        }

        val recognizer = getRecognizer(language)
        val image = InputImage.fromBitmap(bitmap, 0)

        return suspendCancellableCoroutine { continuation ->
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    val blocks = normalizeVisionText(visionText, width, height, language)
                    val duration = System.currentTimeMillis() - startTime
                    continuation.resume(
                        OcrResult(
                            blocks = blocks,
                            imageWidth = width,
                            imageHeight = height,
                            processingTimeMs = duration,
                            detectedLanguage = language
                        )
                    )
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }

    /**
     * Convert ML Kit Text blocks to normalized resolution-independent rects
     */
    private fun normalizeVisionText(
        visionText: Text,
        imageWidth: Int,
        imageHeight: Int,
        language: String
    ): List<OcrTextBlock> {
        val resultBlocks = mutableListOf<OcrTextBlock>()

        for (textBlock in visionText.textBlocks) {
            val rawRect = textBlock.boundingBox ?: Rect(0, 0, imageWidth, imageHeight)
            val normalized = NormalizedRect(
                left = (rawRect.left.toFloat() / imageWidth).coerceIn(0f, 1f),
                top = (rawRect.top.toFloat() / imageHeight).coerceIn(0f, 1f),
                right = (rawRect.right.toFloat() / imageWidth).coerceIn(0f, 1f),
                bottom = (rawRect.bottom.toFloat() / imageHeight).coerceIn(0f, 1f)
            )

            resultBlocks.add(
                OcrTextBlock(
                    id = UUID.randomUUID().toString(),
                    text = textBlock.text.trim(),
                    boundingBox = normalized,
                    confidence = 0.95f,
                    language = language
                )
            )
        }

        return resultBlocks
    }

    /**
     * Cleanly release active ML Kit recognizer resources
     */
    @Synchronized
    fun release() {
        activeRecognizer?.close()
        activeRecognizer = null
        currentLanguage = null
    }
}
