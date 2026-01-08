package org.example.project.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
expect fun grainyGradientBrush(
    startColor: Color,
    endColor: Color,
    grainIntensity: Float
): Brush
