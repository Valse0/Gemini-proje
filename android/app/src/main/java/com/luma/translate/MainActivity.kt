package com.luma.translate

import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.luma.translate.capture.CaptureService
import com.luma.translate.data.PreferencesRepository
import com.luma.translate.translation.SupportedLanguages
import com.luma.translate.ui.home.HomeScreen
import com.luma.translate.ui.privacy.PrivacySection
import com.luma.translate.ui.theme.LumaTheme

class MainActivity : ComponentActivity() {

    private lateinit var preferences: PreferencesRepository

    // Held as Activity-level Compose state (not inside setContent's
    // `remember`) so both the Composable tree and the ActivityResult
    // launcher callbacks below - which must be registered before
    // setContent runs - can read and update the same state.
    private var hasOverlayPermission by mutableStateOf(false)
    private var sourceLanguageCode by mutableStateOf(SupportedLanguages.DEFAULT_SOURCE_CODE)
    private var targetLanguageCode by mutableStateOf(SupportedLanguages.DEFAULT_TARGET_CODE)
    private var backendUrl by mutableStateOf("")
    private var isCapturing by mutableStateOf(false)
    private var captureDeniedMessage by mutableStateOf<String?>(null)

    // Bug 3 fix: true only while we are specifically waiting on the result
    // of an overlay-permission request *we* launched. Used to decide
    // whether returning to the app should auto-continue to the next setup
    // step (screen-capture consent) versus an ordinary app resume (e.g. the
    // user just switching back from another app), which must NOT silently
    // trigger a capture-consent prompt.
    private var awaitingOverlayPermissionResult = false

    private val overlayPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        val wasAwaiting = awaitingOverlayPermissionResult
        awaitingOverlayPermissionResult = false
        hasOverlayPermission = checkOverlayPermission()
        // Continue the setup flow automatically only when the user actually
        // granted the permission we just asked for. A denial or cancel
        // leaves them on the home screen, where the button reverts to
        // "Grant Overlay Permission" - no loop, no repeated prompt.
        if (wasAwaiting && hasOverlayPermission) {
            requestScreenCapture()
        }
    }

    private val mediaProjectionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            captureDeniedMessage = null
            val intent = Intent(this, CaptureService::class.java).apply {
                action = CaptureService.ACTION_START_CAPTURE
                putExtra(CaptureService.EXTRA_RESULT_CODE, result.resultCode)
                putExtra(CaptureService.EXTRA_RESULT_DATA, result.data)
                putExtra(CaptureService.EXTRA_SOURCE_LANGUAGE, sourceLanguageCode)
                putExtra(CaptureService.EXTRA_TARGET_LANGUAGE, targetLanguageCode)
                // Defaulting dimensions, can be refined based on device metrics
                putExtra(CaptureService.EXTRA_WIDTH, 1080)
                putExtra(CaptureService.EXTRA_HEIGHT, 1920)
                putExtra(CaptureService.EXTRA_DPI, 400)
            }
            startForegroundService(intent)
            isCapturing = true
        } else {
            // Previously `isCapturing` was set to true optimistically before
            // this callback ever ran, so denying the system's screen-capture
            // consent dialog left the UI stuck showing an active session
            // with nothing actually running. Now the UI only reflects a
            // capture session once the system has confirmed consent.
            isCapturing = false
            captureDeniedMessage = "Screen capture permission was not granted, so the session didn't start."
        }
    }

    private fun checkOverlayPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(this)
        } else {
            true
        }
    }

    private fun requestOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            awaitingOverlayPermissionResult = true
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            overlayPermissionLauncher.launch(intent)
        }
    }

    // Android 13+: the session notification (with its Stop action) is only
    // shown if POST_NOTIFICATIONS is granted. Asked once, in context (right
    // after the user taps Start), never at first launch. Denial is harmless:
    // the capture session still starts, so we continue either way and never
    // loop. Permanently denied -> the system simply doesn't show the dialog
    // and the callback fires with false; the session still works.
    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { launchProjectionConsent() }

    private fun requestScreenCapture() {
        captureDeniedMessage = null
        if (Build.VERSION.SDK_INT >= 33 &&
            checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) !=
            android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            launchProjectionConsent()
        }
    }

    private fun launchProjectionConsent() {
        val projectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        mediaProjectionLauncher.launch(projectionManager.createScreenCaptureIntent())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = PreferencesRepository(this)
        hasOverlayPermission = checkOverlayPermission()
        sourceLanguageCode = preferences.sourceLanguageCode
        targetLanguageCode = preferences.targetLanguageCode
        backendUrl = preferences.backendUrl
        enableEdgeToEdge()
        setContent {
            LumaTheme {
                var showPrivacy by remember { mutableStateOf(false) }

                // General resume refresh: catches the case where the user
                // revoked overlay permission from system settings while the
                // app was backgrounded (not covered by the launcher callback
                // above, which only fires for requests we initiated). This
                // intentionally does not auto-continue the setup flow -
                // only the explicit launcher path above does that.
                DisposableEffect(Unit) {
                    val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
                        if (event == androidx.lifecycle.Lifecycle.Event.ON_RESUME) {
                            hasOverlayPermission = checkOverlayPermission()
                        }
                    }
                    lifecycle.addObserver(observer)
                    onDispose { lifecycle.removeObserver(observer) }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (showPrivacy) {
                        PrivacySection(
                            onDismiss = { showPrivacy = false },
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {
                        HomeScreen(
                            isOverlayPermissionGranted = hasOverlayPermission,
                            sourceLanguageCode = sourceLanguageCode,
                            targetLanguageCode = targetLanguageCode,
                            onSourceLanguageSelected = { code ->
                                sourceLanguageCode = code
                                preferences.sourceLanguageCode = code
                            },
                            onTargetLanguageSelected = { code ->
                                targetLanguageCode = code
                                preferences.targetLanguageCode = code
                            },
                            isCapturing = isCapturing,
                            captureDeniedMessage = captureDeniedMessage,
                            onRequestOverlayPermission = { requestOverlayPermission() },
                            onStartCapture = { requestScreenCapture() },
                            onStopCapture = {
                                val intent = Intent(this@MainActivity, CaptureService::class.java)
                                intent.action = CaptureService.ACTION_STOP_CAPTURE
                                startService(intent)
                                isCapturing = false
                            },
                            onOpenPrivacyInfo = { showPrivacy = true },
                            backendUrl = backendUrl,
                            onBackendUrlSaved = { input ->
                                val normalized = PreferencesRepository.normalizeBackendUrl(
                                    input, allowCleartext = com.luma.translate.BuildConfig.DEBUG
                                )
                                if (normalized == null) false else {
                                    preferences.backendUrl = normalized
                                    backendUrl = normalized
                                    true
                                }
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
