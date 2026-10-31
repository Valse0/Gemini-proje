package com.luma.translate.app

import android.app.Application
import com.luma.translate.data.PreferencesRepository
import com.luma.translate.translation.TranslationModelManager

/**
 * Luma Translate application entry point. Holds the single on-device
 * translation-model manager so the UI and the capture service share state.
 * No analytics, no network work at startup.
 */
class LumaApplication : Application() {
    val modelManager: TranslationModelManager by lazy {
        TranslationModelManager(this, PreferencesRepository(this))
    }
}
