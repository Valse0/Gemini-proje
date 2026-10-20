package com.luma.translate

import com.luma.translate.translation.TranslationRequest
import com.luma.translate.translation.TranslationResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class TranslationContractTest {
    @Test
    fun translationRequest_defaultParameters() {
        val request = TranslationRequest(text = "こんにちは")
        assertEquals("auto", request.sourceLanguage)
        assertEquals("en", request.targetLanguage)
        assertEquals("general", request.context)
    }

    @Test
    fun translationResponse_fieldsMatch() {
        val response = TranslationResponse(
            translatedText = "Hello",
            detectedSourceLanguage = "ja",
            targetLanguage = "en",
            provider = "gemini",
            durationMs = 120L
        )
        assertEquals("Hello", response.translatedText)
        assertEquals(120L, response.durationMs)
    }
}
