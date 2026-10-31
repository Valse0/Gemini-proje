package com.luma.translate.ui.privacy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PrivacySection(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
    ) {
        // Scrollable so this doesn't clip on small screens or with larger
        // system font-scale settings (more privacy points may be added
        // here later too) - previously a fixed, non-scrolling Column could
        // push the "Understood & Agreed" button off-screen in either case.
        Column(
            modifier = Modifier
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Privacy & Data Architecture",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Luma Translate is designed from first principles with privacy by design:",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                PrivacyPoint(
                    title = "1. On-Device OCR",
                    description = "Screen text is extracted locally using on-device ML Kit models. Screen images are never uploaded anywhere."
                )
                PrivacyPoint(
                    title = "2. On-Device Translation",
                    description = "Recognized text is translated on your phone with Google ML Kit language packs. Your screen text is not sent to any translation server, and there is no account or API key."
                )
                PrivacyPoint(
                    title = "3. Optional Language-Pack Downloads",
                    description = "The only network use is downloading language packs, and only after you tap Download. The download fetches the packs; it does not upload your text. Google's ML Kit may also exchange non-content diagnostic/usage metrics as described in its privacy documentation."
                )
                PrivacyPoint(
                    title = "4. No Tracking",
                    description = "Luma adds no analytics and does not log recognized or translated text. Support and Feedback links simply open your web browser."
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Understood & Agreed")
            }
        }
    }
}

@Composable
private fun PrivacyPoint(title: String, description: String) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = description,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
    }
}
