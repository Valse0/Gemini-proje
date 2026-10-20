package com.luma.translate.ocr

import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.chinese.ChineseTextRecognizerOptions
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

/**
 * Factory for on-device ML Kit TextRecognizer instances
 * Implements dynamic lazy instantiation to minimize memory footprint
 */
object LanguageRecognizerFactory {

    fun createRecognizer(languageCode: String): TextRecognizer {
        return when (languageCode.lowercase()) {
            "ja", "japanese" -> {
                val options = JapaneseTextRecognizerOptions.Builder().build()
                TextRecognition.getClient(options)
            }
            "ko", "korean" -> {
                val options = KoreanTextRecognizerOptions.Builder().build()
                TextRecognition.getClient(options)
            }
            "zh", "chinese" -> {
                val options = ChineseTextRecognizerOptions.Builder().build()
                TextRecognition.getClient(options)
            }
            else -> {
                // Default to Latin recognizer (English, Turkish, Spanish, etc.)
                TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            }
        }
    }
}
