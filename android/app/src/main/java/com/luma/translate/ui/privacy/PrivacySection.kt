package com.luma.translate.ui.privacy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
        Column(
            modifier = Modifier.padding(24.dp),
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
                    title = "1. On-Device OCR Execution",
                    description = "Screen text is extracted locally using on-device ML Kit models. Screen images and bitmaps are never uploaded to any remote server."
                )
                PrivacyPoint(
                    title = "2. Minimal Text Transmission",
                    description = "Only the bounded extracted text string and language settings are sent to the translation backend."
                )
                PrivacyPoint(
                    title = "3. Zero Telemetry & Secret Safety",
                    description = "No translation text or personal data is logged. Provider credentials reside strictly on the server and are never included in the APK."
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
