package com.luma.translate.translation

import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import com.luma.translate.pipeline.ModelMissingException
import com.luma.translate.pipeline.UnsupportedLanguagePairException
import com.luma.translate.util.await

/** Translates a text string. Implementations must not log or transmit the text. */
interface TextTranslator {
    suspend fun translate(text: String, sourceLanguage: String, targetLanguage: String): String
    fun close()
}

/**
 * Luma language codes -> ML Kit [TranslateLanguage] codes. Returns null for
 * languages Luma doesn't offer.
 */
object MlKitLanguages {
    private val map = mapOf(
        "ja" to TranslateLanguage.JAPANESE,
        "ko" to TranslateLanguage.KOREAN,
        "zh" to TranslateLanguage.CHINESE,
        "en" to TranslateLanguage.ENGLISH,
        "es" to TranslateLanguage.SPANISH,
        "tr" to TranslateLanguage.TURKISH,
    )

    fun toMlKit(code: String): String? = map[code]

    fun fromMlKit(mlKitCode: String): String? = map.entries.firstOrNull { it.value == mlKitCode }?.key
}

/**
 * On-device translation using Google ML Kit Translation. Text is processed
 * locally and is never sent to a server. This class never starts a model
 * download: downloads require explicit user consent and are performed only by
 * [TranslationModelManager]. If the language pack isn't ready it throws
 * [ModelMissingException] instead.
 */
class MlKitTextTranslator(
    private val models: TranslationModelManager
) : TextTranslator {

    private val clients = HashMap<String, Translator>()

    @Synchronized
    private fun clientFor(source: String, target: String): Translator =
        clients.getOrPut("$source>$target") {
            Translation.getClient(
                TranslatorOptions.Builder()
                    .setSourceLanguage(source)
                    .setTargetLanguage(target)
                    .build()
            )
        }

    override suspend fun translate(text: String, sourceLanguage: String, targetLanguage: String): String {
        val source = MlKitLanguages.toMlKit(sourceLanguage) ?: throw UnsupportedLanguagePairException()
        val target = MlKitLanguages.toMlKit(targetLanguage) ?: throw UnsupportedLanguagePairException()
        if (source == target) return text // already in the target language
        if (!models.isPairReady(sourceLanguage, targetLanguage)) throw ModelMissingException()
        return clientFor(source, target).translate(text).await()
    }

    @Synchronized
    override fun close() {
        clients.values.forEach { it.close() }
        clients.clear()
    }
}
