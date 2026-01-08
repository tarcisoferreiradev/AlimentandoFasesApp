
package org.example.project.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.premiumShadow() = this.shadow(
    elevation = 10.dp,
    ambientColor = Color.Black.copy(alpha = 0.06f),
    spotColor = Color.Black.copy(alpha = 0.06f),
    shape = RoundedCornerShape(AppSpacing.medium)
)
