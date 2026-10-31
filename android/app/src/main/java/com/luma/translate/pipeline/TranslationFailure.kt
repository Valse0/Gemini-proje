package com.luma.translate.pipeline

import java.io.IOException

/**
 * Failure classes for on-device translation, with short user-facing text.
 *
 * History: earlier versions translated through a remote backend and showed raw
 * exception text such as "failed to connect to /10.0.2.2 (port 3000) ..." on a
 * real phone (reference/bubble-translate/the-fotos/Hata1/2/4.jpeg). Translation
 * is now fully on-device, so the network failure classes are gone; what remains
 * is "the language model isn't installed yet" and local failures.
 *
 * Technical detail is logged as a class name only - never message text, which
 * could contain recognized screen content.
 */
enum class FailureKind(val userMessage: String) {
    MODEL_MISSING("The translation language pack isn't installed. Open Luma > Settings to download it."),
    UNSUPPORTED_PAIR("This language combination isn't supported."),
    TRANSLATION_FAILED("Translation failed on this device. Please try again."),
    CANCELLED("Translation was cancelled."),
    UNKNOWN("Translation failed unexpectedly.");
}

/** The required on-device language model(s) are not installed. */
class ModelMissingException : IOException("Translation model not installed")

/** Source/target code is outside [com.luma.translate.translation.SupportedLanguages]. */
class UnsupportedLanguagePairException : IllegalArgumentException("Unsupported language pair")

object TranslationFailureClassifier {

    fun classify(t: Throwable): FailureKind = when (t) {
        is ModelMissingException -> FailureKind.MODEL_MISSING
        is UnsupportedLanguagePairException -> FailureKind.UNSUPPORTED_PAIR
        is kotlinx.coroutines.CancellationException -> FailureKind.CANCELLED
        is IOException -> FailureKind.TRANSLATION_FAILED
        is RuntimeException -> FailureKind.TRANSLATION_FAILED
        else -> FailureKind.UNKNOWN
    }
}
