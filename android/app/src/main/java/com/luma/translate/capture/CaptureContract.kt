package com.luma.translate.capture

import android.graphics.Bitmap

/**
 * Capture State Machine
 */
enum class CaptureState {
    IDLE,
    CONSENT_REQUESTED,
    CAPTURING,
    STOPPED,
    PERMISSION_DENIED,
    SECURE_SURFACE_BLOCKED,
    ERROR
}

/**
 * Representation of a locally captured screen frame
 */
data class ScreenFrame(
    val bitmap: Bitmap,
    val width: Int,
    val height: Int,
    val densityDpi: Int,
    val timestampMs: Long = System.currentTimeMillis()
)

/**
 * Screen capture error types
 */
sealed class CaptureError(val message: String) {
    object PermissionDenied : CaptureError("User declined screen capture consent")
    object SecureSurfaceBlocked : CaptureError("The screen is protected (FLAG_SECURE or DRM) and cannot be captured")
    data class InitializationFailed(val reason: String) : CaptureError("Failed to initialize capture service: $reason")
}
