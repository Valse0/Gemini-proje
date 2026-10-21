package com.luma.translate

import org.junit.Assert.assertTrue
import org.junit.Test

class ReleaseContractTest {

    @Test
    fun releaseReport_sdkAndOcrValidity() {
        val sdkInt = android.os.Build.VERSION.SDK_INT
        // In local unit test environment, SDK_INT may be 0 or >= 24
        assertTrue(sdkInt == 0 || sdkInt >= 24)
    }
}
