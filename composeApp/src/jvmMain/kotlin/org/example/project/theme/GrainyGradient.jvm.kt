package org.example.project.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
actual fun grainyGradientBrush(
    startColor: Color,
    endColor: Color,
    grainIntensity: Float
): Brush {
    return remember {
        Brush.linearGradient(
            colors = listOf(
                startColor,
                endColor
            )
        )
    }
}
