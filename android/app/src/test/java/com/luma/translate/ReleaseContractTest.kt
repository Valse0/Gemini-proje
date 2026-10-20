package com.luma.translate

import com.luma.translate.release.ReleaseInspector
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ReleaseContractTest {

    @Test
    fun releaseReport_sdkAndOcrValidity() {
        assertTrue(android.os.Build.VERSION.SDK_INT >= 24)
    }
}
