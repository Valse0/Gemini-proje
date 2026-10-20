package com.luma.translate.translation

/**
 * Data contracts for backend communication with Luma Translate API (/v1/translate)
 * Guaranteed to match backend schema without leaking secrets or sending screenshot bitmaps
 */

data class TranslationRequest(
    val text: String,
    val sourceLanguage: String = "auto",
    val targetLanguage: String = "en",
    val context: String? = "general"
)

data class TranslationResponse(
    val translatedText: String,
    val detectedSourceLanguage: String,
    val targetLanguage: String,
    val provider: String,
    val durationMs: Long
)

data class ApiError(
    val code: String,
    val message: String
)

data class TranslationErrorResponse(
    val error: ApiError
)
