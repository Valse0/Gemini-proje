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
import com.luma.translate.ui.home.HomeScreen
import com.luma.translate.ui.privacy.PrivacySection
import com.luma.translate.ui.theme.LumaTheme

class MainActivity : ComponentActivity() {

    private val overlayPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { }

    private val mediaProjectionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val intent = Intent(this, CaptureService::class.java).apply {
                action = CaptureService.ACTION_START_CAPTURE
                putExtra(CaptureService.EXTRA_RESULT_CODE, result.resultCode)
                putExtra(CaptureService.EXTRA_RESULT_DATA, result.data)
                // Defaulting dimensions, can be refined based on device metrics
                putExtra(CaptureService.EXTRA_WIDTH, 1080)
                putExtra(CaptureService.EXTRA_HEIGHT, 1920)
                putExtra(CaptureService.EXTRA_DPI, 400)
            }
            startForegroundService(intent)
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
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            overlayPermissionLauncher.launch(intent)
        }
    }

    private fun requestScreenCapture() {
        val projectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        mediaProjectionLauncher.launch(projectionManager.createScreenCaptureIntent())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LumaTheme {
                var showPrivacy by remember { mutableStateOf(false) }
                var hasOverlayPermission by remember { mutableStateOf(checkOverlayPermission()) }
                var isCapturing by remember { mutableStateOf(false) }

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
                            onRequestOverlayPermission = { requestOverlayPermission() },
                            onStartCapture = { requestScreenCapture(); isCapturing = true },
                            onStopCapture = {
                                val intent = Intent(this@MainActivity, CaptureService::class.java)
                                intent.action = CaptureService.ACTION_STOP_CAPTURE
                                startService(intent)
                                isCapturing = false
                            },
                            onOpenPrivacyInfo = { showPrivacy = true },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
