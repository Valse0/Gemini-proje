package com.luma.translate.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luma.translate.ui.theme.ElectricBlue
import com.luma.translate.ui.theme.EmeraldGreen
import com.luma.translate.ui.theme.Slate800
import com.luma.translate.ui.theme.Slate900

enum class SessionState {
    IDLE,
    REQUESTING_PERMISSION,
    ACTIVE_OVERLAY,
    TRANSLATING
}

@Composable
fun HomeScreen(
    isOverlayPermissionGranted: Boolean,
    onRequestOverlayPermission: () -> Unit,
    onStartCapture: () -> Unit,
    onStopCapture: () -> Unit,
    onOpenPrivacyInfo: () -> Unit,
    modifier: Modifier = Modifier
) {
    var sourceLanguage by remember { mutableStateOf("Japanese (ja)") }
    var targetLanguage by remember { mutableStateOf("English (en)") }
    var sessionState by remember { mutableStateOf(SessionState.IDLE) }
    var selectedContext by remember { mutableStateOf("General") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header Section
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Luma Translate",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Your Screen · Your Language · Your Privacy",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
                IconButton(onClick = onOpenPrivacyInfo) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("🛡️", fontSize = 16.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Status Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(if (sessionState == SessionState.ACTIVE_OVERLAY) EmeraldGreen else Color.Gray)
                    )
                    Column {
                        Text(
                            text = if (sessionState == SessionState.ACTIVE_OVERLAY) "Overlay Session Active" else "Ready to Translate",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = if (sessionState == SessionState.ACTIVE_OVERLAY) "Tap floating button on any app to translate" else "Start session to enable screen translation",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Language Selector Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Language Pair",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.background,
                            modifier = Modifier.weight(1f).padding(end = 6.dp)
                        ) {
                            Text(
                                text = sourceLanguage,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                        Text("➔", color = Color.Gray)
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.background,
                            modifier = Modifier.weight(1f).padding(start = 6.dp)
                        ) {
                            Text(
                                text = targetLanguage,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }
            }
        }

        // Action Section (Bottom)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    if (!isOverlayPermissionGranted) {
                        onRequestOverlayPermission()
                    } else {
                        if (sessionState == SessionState.ACTIVE_OVERLAY) {
                            onStopCapture()
                            sessionState = SessionState.IDLE
                        } else {
                            onStartCapture()
                            sessionState = SessionState.ACTIVE_OVERLAY
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (sessionState == SessionState.ACTIVE_OVERLAY) Slate800 else ElectricBlue
                )
            ) {
                Text(
                    text = when {
                        !isOverlayPermissionGranted -> "Grant Overlay Permission"
                        sessionState == SessionState.ACTIVE_OVERLAY -> "Stop Overlay Session"
                        else -> "Start Floating Overlay Session"
                    },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            TextButton(
                onClick = onOpenPrivacyInfo,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "🔒 Privacy Commitment: Images never leave this device",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }
        }
    }
}
