package com.luma.translate

import com.luma.translate.settings.SettingsManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SettingsContractTest {

    @Test
    fun defaultPreferences_valuesMatch() {
        val manager = SettingsManager()
        val prefs = manager.getPreferences()
        assertEquals("auto", prefs.defaultSourceLanguage)
        assertEquals("en", prefs.defaultTargetLanguage)
        assertEquals("gaming", prefs.activeContext)
        assertTrue(prefs.autoTranslateOnOverlayTap)
    }

    @Test
    fun updatePreferences_modifiesStateCorrectly() {
        val manager = SettingsManager()
        manager.updatePreferences { it.copy(defaultTargetLanguage = "tr", activeContext = "manga") }
        val prefs = manager.getPreferences()
        assertEquals("tr", prefs.defaultTargetLanguage)
        assertEquals("manga", prefs.activeContext)
    }
}
