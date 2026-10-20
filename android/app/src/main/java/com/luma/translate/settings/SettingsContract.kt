package com.luma.translate.settings

/**
 * User preferences and configuration model for Luma Translate
 */
data class UserPreferences(
    val defaultSourceLanguage: String = "auto",
    val defaultTargetLanguage: String = "en",
    val activeContext: String = "gaming",
    val autoTranslateOnOverlayTap: Boolean = true,
    val showBoundingBoxes: Boolean = true,
    val floatingButtonOpacity: Float = 0.85f,
    val keepAliveService: Boolean = true
)

/**
 * Settings Manager Contract
 */
class SettingsManager {
    private var prefs = UserPreferences()

    fun getPreferences(): UserPreferences = prefs

    fun updatePreferences(update: (UserPreferences) -> UserPreferences): UserPreferences {
        prefs = update(prefs)
        return prefs
    }
}
