package com.luma.translate.pipeline

import java.io.IOException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

/**
 * Failure classes for the translation request, with user-facing text.
 *
 * Evidence this exists (reference/bubble-translate/the-fotos/Hata1/2/4.jpeg):
 * a real phone showed the raw exception text
 * "Translation failed: failed to connect to /10.0.2.2 (port 3000) from
 * /192.168.1.3 (port 60628) after 5000ms". That message (a) leaked the
 * device's LAN address and internal ports into the UI, (b) gave the user no
 * hint what to do, and (c) hid the actual root cause: 10.0.2.2 is the
 * Android *emulator's* alias for the host PC and can never be reached from a
 * physical phone. Failures are now classified and shown as short, actionable
 * messages; technical detail goes to the log as a class name only (never
 * message text, which can contain hosts, ports or screen content).
 */
enum class FailureKind(val userMessage: String) {
    NOT_CONFIGURED("The translation server address isn't set. Open Luma, tap Server settings and enter your server's address."),
    OFFLINE("Can't reach the translation server. Check your internet connection and the server address."),
    TIMEOUT("The translation server took too long to answer. Try again in a moment."),
    TLS("A secure connection to the translation server couldn't be established."),
    RATE_LIMITED("Too many requests. Please wait a few seconds and try again."),
    BAD_REQUEST("The server rejected this text (it may be too long or use an unsupported language)."),
    SERVER_ERROR("The translation service had a problem. Try again later."),
    BAD_RESPONSE("The server sent a reply Luma couldn't read."),
    CANCELLED("Translation was cancelled."),
    UNKNOWN("Translation failed unexpectedly.");
}

/** HTTP failure from the backend (non-2xx). Carries only the status code. */
class BackendHttpException(val statusCode: Int) : IOException("HTTP $statusCode")

/** 2xx reply whose body is empty or not the documented JSON shape. */
class BadBackendResponseException(cause: Throwable? = null) : IOException("Unreadable backend response", cause)

/** Backend address is missing/unusable for this device. */
class BackendNotConfiguredException : IllegalStateException("Backend URL not configured")

object TranslationFailureClassifier {

    fun classify(t: Throwable): FailureKind = when (t) {
        is BackendNotConfiguredException -> FailureKind.NOT_CONFIGURED
        is kotlinx.coroutines.CancellationException -> FailureKind.CANCELLED
        is BackendHttpException -> classifyHttp(t.statusCode)
        is BadBackendResponseException -> FailureKind.BAD_RESPONSE
        is org.json.JSONException -> FailureKind.BAD_RESPONSE
        is SocketTimeoutException -> FailureKind.TIMEOUT
        is UnknownHostException, is ConnectException, is NoRouteToHostException -> FailureKind.OFFLINE
        is SSLException -> FailureKind.TLS
        is IOException -> FailureKind.OFFLINE
        else -> FailureKind.UNKNOWN
    }

    fun classifyHttp(code: Int): FailureKind = when {
        code == 429 -> FailureKind.RATE_LIMITED
        code in 400..499 -> FailureKind.BAD_REQUEST
        code >= 500 -> FailureKind.SERVER_ERROR
        else -> FailureKind.UNKNOWN
    }
}

/**
 * True when [baseUrl] points at the Android emulator's host alias but the
 * code is running on a physical device, where that address can never work.
 */
fun isEmulatorOnlyUrlOnRealDevice(baseUrl: String, isEmulator: Boolean): Boolean =
    !isEmulator && baseUrl.contains("://10.0.2.2")
