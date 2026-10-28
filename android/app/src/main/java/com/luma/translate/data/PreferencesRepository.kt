package com.luma.translate.data

import android.content.Context
import android.content.SharedPreferences
import com.luma.translate.translation.SupportedLanguages

/**
 * Small local preferences repository, as called for in docs/ARCHITECTURE.md
 * ("data/ - Small local preferences repository").
 *
 * This did not previously exist. `settings/SettingsContract.kt`'s
 * `SettingsManager` held preferences only in an in-memory `var`, which is
 * why language selection (and any other setting) reset to Luma's hardcoded
 * defaults every time the app process restarted - part of the Bug 2 report
 * ("persist user preferences across app restarts"). `SettingsManager` was
 * left as-is (it's a small, deliberately Context-free class with its own
 * passing JVM unit tests); this repository is the actual persistence layer,
 * backed by plain `SharedPreferences` rather than a new dependency such as
 * Jetpack DataStore, since a handful of string preferences doesn't justify
 * one (AGENTS.md "simplicity first" / "do not add a library without a
 * clear need").
 */
class PreferencesRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var sourceLanguageCode: String
        get() = prefs.getString(KEY_SOURCE_LANGUAGE, SupportedLanguages.DEFAULT_SOURCE_CODE)
            ?: SupportedLanguages.DEFAULT_SOURCE_CODE
        set(value) = prefs.edit().putString(KEY_SOURCE_LANGUAGE, value).apply()

    var targetLanguageCode: String
        get() = prefs.getString(KEY_TARGET_LANGUAGE, SupportedLanguages.DEFAULT_TARGET_CODE)
            ?: SupportedLanguages.DEFAULT_TARGET_CODE
        set(value) = prefs.edit().putString(KEY_TARGET_LANGUAGE, value).apply()

    /**
     * User-entered translation server address (e.g. https://translate.example.com).
     * Empty string means "not set": the build-time default
     * (BuildConfig.BACKEND_BASE_URL) is used instead. Needed because the
     * build default is the emulator-only alias 10.0.2.2, which a physical
     * phone can never reach (see docs/analysis/BUG_AUDIT.md, BUG-01).
     */
    var backendUrl: String
        get() = prefs.getString(KEY_BACKEND_URL, "") ?: ""
        set(value) = prefs.edit().putString(KEY_BACKEND_URL, value.trim()).apply()

    companion object {
        /** Returns the normalized URL if acceptable, otherwise null. */
        fun normalizeBackendUrl(input: String, allowCleartext: Boolean): String? {
            val v = input.trim().trimEnd('/')
            if (v.isEmpty()) return ""
            val lower = v.lowercase()
            val ok = lower.startsWith("https://") || (allowCleartext && lower.startsWith("http://"))
            if (!ok || v.length > 200 || v.any { it.isWhitespace() }) return null
            val host = v.substringAfter("://").substringBefore('/').substringBefore(':')
            return if (host.isEmpty()) null else v
        }

        private const val KEY_BACKEND_URL = "backend_url"
        private const val PREFS_NAME = "luma_preferences"
        private const val KEY_SOURCE_LANGUAGE = "source_language_code"
        private const val KEY_TARGET_LANGUAGE = "target_language_code"
    }
}
