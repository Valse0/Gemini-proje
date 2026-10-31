package com.luma.translate.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.luma.translate.translation.SupportedLanguages
import com.luma.translate.translation.TranslationModelManager.OpState
import com.luma.translate.ui.theme.ElectricBlue

/**
 * Settings: manage on-device language packs, then support / feedback / about.
 * Every control here performs the action it advertises.
 */
@Composable
fun SettingsScreen(
    installedLanguages: Set<String>,
    opState: OpState,
    wifiOnly: Boolean,
    versionName: String,
    onWifiOnlyChange: (Boolean) -> Unit,
    onDownloadLanguage: (String) -> Unit,
    onDeleteLanguage: (String) -> Unit,
    onDismissFailure: () -> Unit,
    onSupportDeveloper: () -> Unit,
    onSendFeedback: () -> Unit,
    onOpenPrivacy: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var pendingDelete by remember { mutableStateOf<SupportedLanguages.Language?>(null) }
    val busy = opState is OpState.Working
    val languages = (SupportedLanguages.SOURCE_LANGUAGES + SupportedLanguages.TARGET_LANGUAGES).distinctBy { it.code }

    pendingDelete?.let { language ->
        AlertDialog(
            onDismissRequest = { pendingDelete = null },
            title = { Text("Remove ${language.displayName} pack?") },
            text = {
                Text(
                    "Translations that use ${language.displayName} will stop working offline until you download it again. " +
                        "Nothing else on your device is affected."
                )
            },
            confirmButton = {
                TextButton(onClick = { onDeleteLanguage(language.code); pendingDelete = null }) { Text("Remove") }
            },
            dismissButton = { TextButton(onClick = { pendingDelete = null }) { Text("Cancel") } }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = onBack) { Text("< Back") }
            Spacer(Modifier.width(8.dp))
            Text("Settings", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
        }

        SectionCard(title = "Offline language packs") {
            Text(
                "Translation runs on your phone. Language packs are optional downloads from Google ML Kit; " +
                    "downloading never uploads your screen text. The SDK doesn't report pack sizes " +
                    "(Google's guide says around 30 MB each).",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Download on Wi-Fi only", style = MaterialTheme.typography.bodyMedium)
                Switch(checked = wifiOnly, onCheckedChange = onWifiOnlyChange, enabled = !busy)
            }
            languages.forEach { language ->
                val installed = language.code in installedLanguages
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(language.displayName, style = MaterialTheme.typography.bodyMedium)
                        Text(
                            if (installed) "Installed" else "Not installed",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (installed) ElectricBlue else Color.Gray
                        )
                    }
                    if (installed) {
                        TextButton(onClick = { pendingDelete = language }, enabled = !busy) { Text("Remove") }
                    } else {
                        TextButton(onClick = { onDownloadLanguage(language.code) }, enabled = !busy) { Text("Download") }
                    }
                }
            }
            when (opState) {
                is OpState.Working -> {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    Text(opState.message, style = MaterialTheme.typography.bodySmall)
                }
                is OpState.Failed -> {
                    Text(opState.message, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
                    TextButton(onClick = onDismissFailure) { Text("Dismiss") }
                }
                OpState.Idle -> Unit
            }
        }

        SectionCard(title = "Support & feedback") {
            Button(
                onClick = onSupportDeveloper,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Support the Developer") }
            OutlinedButton(
                onClick = onSendFeedback,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Send Feedback") }
            Text(
                "Both open in your web browser.",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
        }

        SectionCard(title = "About") {
            Text("Luma Translate $versionName", style = MaterialTheme.typography.bodyMedium)
            Text(
                "OCR and translation use Google ML Kit, running on-device. See the privacy notes for details.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            TextButton(onClick = onOpenPrivacy) { Text("Privacy details") }
        }
    }
}

@Composable
private fun SectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            content()
        }
    }
}
