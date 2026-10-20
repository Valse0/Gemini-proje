package com.luma.translate

import com.luma.translate.capture.CaptureError
import com.luma.translate.capture.CaptureState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CaptureContractTest {

    @Test
    fun captureState_orderAndValues() {
        val states = CaptureState.values()
        assertTrue(states.contains(CaptureState.IDLE))
        assertTrue(states.contains(CaptureState.CONSENT_REQUESTED))
        assertTrue(states.contains(CaptureState.CAPTURING))
        assertTrue(states.contains(CaptureState.STOPPED))
        assertTrue(states.contains(CaptureState.PERMISSION_DENIED))
        assertTrue(states.contains(CaptureState.SECURE_SURFACE_BLOCKED))
    }

    @Test
    fun captureError_secureSurfaceMessage() {
        val error = CaptureError.SecureSurfaceBlocked
        assertEquals(
            "The screen is protected (FLAG_SECURE or DRM) and cannot be captured",
            error.message
        )
    }

    @Test
    fun captureError_permissionDeniedMessage() {
        val error = CaptureError.PermissionDenied
        assertEquals("User declined screen capture consent", error.message)
    }
}
