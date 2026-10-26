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

    companion object {
        private const val PREFS_NAME = "luma_preferences"
        private const val KEY_SOURCE_LANGUAGE = "source_language_code"
        private const val KEY_TARGET_LANGUAGE = "target_language_code"
    }
}
