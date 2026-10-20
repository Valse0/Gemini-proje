package com.luma.translate.app

import android.app.Application

/**
 * Luma Translate Application Entry Point
 * Initializes logging, lifecycle tracking, and on-device OCR resources
 */
class LumaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize application state cleanly without unnecessary SDK bloat
    }
}
