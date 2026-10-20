package com.luma.translate.ocr

/**
 * Normalized Bounding Box with relative coordinates (0.0 to 1.0)
 * Allows resolution-independent overlay placement across different screen densities
 */
data class NormalizedRect(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float
) {
    val width: Float get() = right - left
    val height: Float get() = bottom - top
}

/**
 * Individual Recognized Text Block
 */
data class OcrTextBlock(
    val id: String,
    val text: String,
    val boundingBox: NormalizedRect,
    val confidence: Float = 1.0f,
    val language: String = "ja"
)

/**
 * Full Screen OCR Recognition Result
 */
data class OcrResult(
    val blocks: List<OcrTextBlock>,
    val imageWidth: Int,
    val imageHeight: Int,
    val processingTimeMs: Long,
    val detectedLanguage: String
) {
    val isEmpty: Boolean get() = blocks.isEmpty()
    val fullText: String get() = blocks.joinToString("\n") { it.text }
}
