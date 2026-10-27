package com.luma.translate.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.window.Dialog
import com.luma.translate.translation.SupportedLanguages
import com.luma.translate.ui.theme.ElectricBlue
import com.luma.translate.ui.theme.EmeraldGreen
import com.luma.translate.ui.theme.Slate800
import com.luma.translate.ui.theme.Slate900

private enum class LanguagePickerTarget { SOURCE, TARGET, NONE }

@Composable
fun HomeScreen(
    isOverlayPermissionGranted: Boolean,
    sourceLanguageCode: String,
    targetLanguageCode: String,
    onSourceLanguageSelected: (String) -> Unit,
    onTargetLanguageSelected: (String) -> Unit,
    isCapturing: Boolean,
    captureDeniedMessage: String?,
    onRequestOverlayPermission: () -> Unit,
    onStartCapture: () -> Unit,
    onStopCapture: () -> Unit,
    onOpenPrivacyInfo: () -> Unit,
    modifier: Modifier = Modifier
) {
    var pickerTarget by remember { mutableStateOf(LanguagePickerTarget.NONE) }

    val sourceLanguage = SupportedLanguages.sourceLanguageOrDefault(sourceLanguageCode)
    val targetLanguage = SupportedLanguages.targetLanguageOrDefault(targetLanguageCode)

    if (pickerTarget != LanguagePickerTarget.NONE) {
        LanguagePickerDialog(
            title = if (pickerTarget == LanguagePickerTarget.SOURCE) "Translate from" else "Translate to",
            options = if (pickerTarget == LanguagePickerTarget.SOURCE) {
                SupportedLanguages.SOURCE_LANGUAGES
            } else {
                SupportedLanguages.TARGET_LANGUAGES
            },
            selectedCode = if (pickerTarget == LanguagePickerTarget.SOURCE) sourceLanguageCode else targetLanguageCode,
            onSelect = { code ->
                if (pickerTarget == LanguagePickerTarget.SOURCE) {
                    onSourceLanguageSelected(code)
                } else {
                    onTargetLanguageSelected(code)
                }
                pickerTarget = LanguagePickerTarget.NONE
            },
            onDismiss = { pickerTarget = LanguagePickerTarget.NONE }
        )
    }

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
                            .background(if (isCapturing) EmeraldGreen else Color.Gray)
                    )
                    Column {
                        Text(
                            text = if (isCapturing) "Overlay Session Active" else "Ready to Translate",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = if (isCapturing) "Tap floating button on any app to translate" else "Start session to enable screen translation",
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
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 6.dp)
                                .clickable { pickerTarget = LanguagePickerTarget.SOURCE }
                        ) {
                            Text(
                                text = sourceLanguage.displayName,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                        Text("➔", color = Color.Gray)
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.background,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 6.dp)
                                .clickable { pickerTarget = LanguagePickerTarget.TARGET }
                        ) {
                            Text(
                                text = targetLanguage.displayName,
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
            // AGENTS.md "Screen capture and overlay": "Explain why overlay
            // permission is needed before sending the user to system
            // settings." This was previously missing - the button below
            // jumped straight to the system permission screen with no
            // explanation shown first. A short, specific reason (not just
            // "this app needs a permission") is shown only while the
            // permission is actually missing, so it doesn't clutter the
            // screen once setup is done.
            if (!isOverlayPermissionGranted) {
                Text(
                    text = "Luma needs permission to draw over other apps so its translate button can appear on top of whatever you're using. It only shows a small floating button - it does not read or record other apps.",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            // Bug 5/6 note: this button's only job is to get the user into an
            // active overlay session (permission -> capture consent -> the
            // floating bubble appears). The actual translation action lives
            // on the bubble itself once the session is active - this button
            // does not perform a fake translation and does not optimistically
            // flip state before the real permission/consent results return;
            // `isCapturing` is owned by MainActivity and only becomes true
            // once CaptureService has actually started (see Bug 6 fix notes
            // in docs/AGENT_PROGRESS.md).
            Button(
                onClick = {
                    if (!isOverlayPermissionGranted) {
                        onRequestOverlayPermission()
                    } else if (isCapturing) {
                        onStopCapture()
                    } else {
                        onStartCapture()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isCapturing) Slate800 else ElectricBlue
                )
            ) {
                Text(
                    text = when {
                        !isOverlayPermissionGranted -> "Grant Overlay Permission"
                        isCapturing -> "Stop Overlay Session"
                        else -> "Start Floating Overlay Session"
                    },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (captureDeniedMessage != null) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.errorContainer
                ) {
                    Text(
                        text = captureDeniedMessage,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(12.dp)
                    )
                }
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

/**
 * A small, native-feeling language picker. The list is short (4 source /
 * 3 target languages today), so a single scrollable list is "otherwise
 * easy-to-use" per the Bug 2 requirement without the overhead of a search
 * field that a list this size doesn't need.
 */
@Composable
private fun LanguagePickerDialog(
    title: String,
    options: List<SupportedLanguages.Language>,
    selectedCode: String,
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                )
                LazyColumn {
                    items(options) { language ->
                        val isSelected = language.code == selectedCode
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelect(language.code) }
                                .padding(horizontal = 20.dp, vertical = 14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = language.displayName,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                    color = if (isSelected) ElectricBlue else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = language.nativeName,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray
                                )
                            }
                            if (isSelected) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(ElectricBlue)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
