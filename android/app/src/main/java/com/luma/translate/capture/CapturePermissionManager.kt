package com.luma.translate.capture

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjectionManager
import androidx.activity.result.ActivityResultLauncher

/**
 * Handles official Android MediaProjection consent request lifecycle
 */
class CapturePermissionManager(private val activity: Activity) {

    private val mediaProjectionManager: MediaProjectionManager =
        activity.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager

    /**
     * Start the official Android screen capture consent intent
     */
    fun requestCaptureConsent(launcher: ActivityResultLauncher<Intent>) {
        val captureIntent = mediaProjectionManager.createScreenCaptureIntent()
        launcher.launch(captureIntent)
    }

    /**
     * Start Foreground CaptureService with granted projection tokens
     */
    fun startCaptureService(resultCode: Int, data: Intent, width: Int, height: Int, dpi: Int) {
        val serviceIntent = Intent(activity, CaptureService::class.java).apply {
            action = CaptureService.ACTION_START_CAPTURE
            putExtra(CaptureService.EXTRA_RESULT_CODE, resultCode)
            putExtra(CaptureService.EXTRA_RESULT_DATA, data)
            putExtra(CaptureService.EXTRA_WIDTH, width)
            putExtra(CaptureService.EXTRA_HEIGHT, height)
            putExtra(CaptureService.EXTRA_DPI, dpi)
        }
        activity.startService(serviceIntent)
    }

    /**
     * Send explicit stop signal to CaptureService
     */
    fun stopCaptureService() {
        val stopIntent = Intent(activity, CaptureService::class.java).apply {
            action = CaptureService.ACTION_STOP_CAPTURE
        }
        activity.startService(stopIntent)
    }
}
