package org.example.project.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.example.project.theme.AppSizing

@Composable
fun AppPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(AppSizing.buttonHeight),
        enabled = !isLoading,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B8E23))
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Text(text)
        }
    }
}