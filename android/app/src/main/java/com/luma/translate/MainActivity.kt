package com.luma.translate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.luma.translate.ui.home.HomeScreen
import com.luma.translate.ui.privacy.PrivacySection
import com.luma.translate.ui.theme.LumaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LumaTheme {
                var showPrivacy by remember { mutableStateOf(false) }
                var hasOverlayPermission by remember { mutableStateOf(false) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (showPrivacy) {
                        PrivacySection(
                            onDismiss = { showPrivacy = false },
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {
                        HomeScreen(
                            isOverlayPermissionGranted = hasOverlayPermission,
                            onRequestOverlayPermission = { hasOverlayPermission = true },
                            onOpenPrivacyInfo = { showPrivacy = true },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
