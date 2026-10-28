package com.luma.translate

import com.luma.translate.data.PreferencesRepository
import com.luma.translate.pipeline.BackendHttpException
import com.luma.translate.pipeline.BackendNotConfiguredException
import com.luma.translate.pipeline.BadBackendResponseException
import com.luma.translate.pipeline.FailureKind
import com.luma.translate.pipeline.TranslationFailureClassifier
import com.luma.translate.pipeline.isEmulatorOnlyUrlOnRealDevice
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

class TranslationFailureTest {

    private fun kind(t: Throwable) = TranslationFailureClassifier.classify(t)

    @Test fun connectFailure_isOffline_andMessageLeaksNothing() {
        // Exact text seen in the Hata1/2/4 screenshots.
        val e = ConnectException("failed to connect to /10.0.2.2 (port 3000) from /192.168.1.3 (port 60628) after 5000ms")
        assertEquals(FailureKind.OFFLINE, kind(e))
        val shown = kind(e).userMessage
        assertFalse(shown.contains("10.0.2.2"))
        assertFalse(shown.contains("192.168"))
        assertFalse(shown.contains("port"))
    }

    @Test fun timeout_dns_tls_classified() {
        assertEquals(FailureKind.TIMEOUT, kind(SocketTimeoutException()))
        assertEquals(FailureKind.OFFLINE, kind(UnknownHostException("x")))
        assertEquals(FailureKind.TLS, kind(SSLHandshakeException("x")))
        assertEquals(FailureKind.OFFLINE, kind(IOException("x")))
    }

    @Test fun httpStatuses_classified() {
        assertEquals(FailureKind.RATE_LIMITED, kind(BackendHttpException(429)))
        assertEquals(FailureKind.BAD_REQUEST, kind(BackendHttpException(400)))
        assertEquals(FailureKind.SERVER_ERROR, kind(BackendHttpException(503)))
        assertEquals(FailureKind.UNKNOWN, TranslationFailureClassifier.classifyHttp(302))
    }

    @Test fun badResponse_notConfigured_cancelled_unknown() {
        assertEquals(FailureKind.BAD_RESPONSE, kind(BadBackendResponseException()))
        assertEquals(FailureKind.NOT_CONFIGURED, kind(BackendNotConfiguredException()))
        assertEquals(FailureKind.CANCELLED, kind(kotlinx.coroutines.CancellationException("x")))
        assertEquals(FailureKind.UNKNOWN, kind(IllegalArgumentException("secret screen text")))
        assertFalse(kind(IllegalArgumentException("secret screen text")).userMessage.contains("secret"))
    }

    @Test fun emulatorAlias_onlyRejectedOnRealDevice() {
        assertTrue(isEmulatorOnlyUrlOnRealDevice("http://10.0.2.2:3000", isEmulator = false))
        assertFalse(isEmulatorOnlyUrlOnRealDevice("http://10.0.2.2:3000", isEmulator = true))
        assertFalse(isEmulatorOnlyUrlOnRealDevice("https://translate.example.com", isEmulator = false))
    }

    @Test fun backendUrlValidation() {
        assertEquals("https://a.example.com", PreferencesRepository.normalizeBackendUrl(" https://a.example.com/ ", false))
        assertEquals("", PreferencesRepository.normalizeBackendUrl("  ", false))
        assertNull(PreferencesRepository.normalizeBackendUrl("http://a.example.com", false))
        assertEquals("http://a.example.com", PreferencesRepository.normalizeBackendUrl("http://a.example.com", true))
        assertNull(PreferencesRepository.normalizeBackendUrl("https://", false))
        assertNull(PreferencesRepository.normalizeBackendUrl("ftp://a.example.com", true))
        assertNull(PreferencesRepository.normalizeBackendUrl("https://a b.com", false))
    }
}
