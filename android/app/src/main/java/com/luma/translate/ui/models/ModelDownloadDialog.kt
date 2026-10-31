package com.luma.translate.ui.models

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.luma.translate.translation.TranslationModelManager.OpState

/**
 * Consent dialog for the optional on-device language packs. Nothing is
 * downloaded until the user presses "Download".
 *
 * Size: ML Kit's SDK doesn't report a pack's size, so no number is computed
 * here. The text below only repeats what Google's ML Kit Translation guide
 * states ("around 30MB" per language model) and says plainly that the exact
 * size isn't known in advance.
 */
@Composable
fun ModelDownloadDialog(
    sourceName: String,
    targetName: String,
    wifiOnly: Boolean,
    onWifiOnlyChange: (Boolean) -> Unit,
    state: OpState,
    onDownload: () -> Unit,
    onNotNow: () -> Unit
) {
    val busy = state is OpState.Working
    AlertDialog(
        onDismissRequest = { if (!busy) onNotNow() },
        title = { Text("Download offline translation?") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    "To translate $sourceName to $targetName, Luma needs language packs from Google ML Kit. " +
                        "Once installed, translation runs entirely on your phone and works without internet.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    "This download is optional. It only fetches the language packs - text from your screen is " +
                        "never sent anywhere. Google's documentation describes each language pack as around 30 MB; " +
                        "the exact size isn't reported before the download starts. You can also do this later in Settings.",
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
                when (state) {
                    is OpState.Working -> {
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                        Text(state.message, style = MaterialTheme.typography.bodySmall)
                    }
                    is OpState.Failed -> Text(
                        state.message,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                    OpState.Idle -> Unit
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDownload, enabled = !busy) {
                Text(if (state is OpState.Failed) "Try Again" else "Download")
            }
        },
        dismissButton = {
            TextButton(onClick = onNotNow, enabled = !busy) { Text("Not Now") }
        }
    )
}
