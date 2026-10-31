package com.luma.translate.translation

/**
 * The single source of truth for which languages Luma actually supports,
 * on both sides of the pipeline.
 *
 * This exists because the previous implementation displayed two hardcoded
 * strings ("Japanese (ja)" / "English (en)") in the UI with no selection
 * behavior at all, while [com.luma.translate.ocr.LanguageRecognizerFactory]
 * already had real on-device recognizers for Japanese, Korean, Chinese and
 * Latin-script text, and the backend provider implementations under
 * `server/src/services/` already accepted a much larger
 * language list. The UI simply never exposed any of it. Keeping this list
 * here - rather than duplicating language codes across the UI, OCR, and
 * pipeline call sites - is what makes "selecting a language changes actual
 * processing behavior" (the Bug 2 acceptance criterion) true by
 * construction: every call site reads from here.
 *
 * Source languages are deliberately limited to languages
 * [com.luma.translate.ocr.LanguageRecognizerFactory] has a *real,
 * dedicated* on-device recognizer for. "Auto-detect source language" is
 * intentionally NOT offered here: ML Kit's on-device recognizers are
 * script-specific, not language-detecting, and Japanese/Chinese text can
 * both produce plausible-looking (but wrong) output from either
 * recognizer. Offering a fake "Auto" option would violate the explicit
 * requirement to make unsupported/unreliable combinations honest rather
 * than silently guessing.
 */
object SupportedLanguages {

    data class Language(val code: String, val displayName: String, val nativeName: String)

    /**
     * Languages Luma can recognize on-device via ML Kit. Each one maps
     * directly to a branch in [com.luma.translate.ocr.LanguageRecognizerFactory].
     */
    val SOURCE_LANGUAGES: List<Language> = listOf(
        Language(code = "ja", displayName = "Japanese", nativeName = "日本語"),
        Language(code = "ko", displayName = "Korean", nativeName = "한국어"),
        Language(code = "zh", displayName = "Chinese (Simplified)", nativeName = "中文"),
        Language(code = "en", displayName = "English", nativeName = "English"),
    )

    /**
     * Languages Luma translates into (all have on-device ML Kit language packs). Kept to the
     * practical set called for in the product brief (English, Turkish,
     * Spanish) rather than the full list
     * [com.luma.translate.translation] theoretically supports server-side -
     * every entry here should be a language that has actually been
     * exercised, not merely accepted by validation.
     */
    val TARGET_LANGUAGES: List<Language> = listOf(
        Language(code = "en", displayName = "English", nativeName = "English"),
        Language(code = "es", displayName = "Spanish", nativeName = "Español"),
        Language(code = "tr", displayName = "Turkish", nativeName = "Türkçe"),
    )

    val DEFAULT_SOURCE_CODE: String = "ja"
    val DEFAULT_TARGET_CODE: String = "en"

    fun sourceLanguageOrDefault(code: String): Language =
        SOURCE_LANGUAGES.find { it.code == code } ?: SOURCE_LANGUAGES.first { it.code == DEFAULT_SOURCE_CODE }

    fun targetLanguageOrDefault(code: String): Language =
        TARGET_LANGUAGES.find { it.code == code } ?: TARGET_LANGUAGES.first { it.code == DEFAULT_TARGET_CODE }

    /**
     * Whether translating from [sourceCode] into [targetCode] is a pairing
     * Luma actually supports end-to-end. Source and target happening to be
     * the same language is allowed (the text is passed through unchanged), but any
     * code outside the two lists above is not.
     */
    fun isSupportedPair(sourceCode: String, targetCode: String): Boolean {
        return SOURCE_LANGUAGES.any { it.code == sourceCode } &&
            TARGET_LANGUAGES.any { it.code == targetCode }
    }
}
