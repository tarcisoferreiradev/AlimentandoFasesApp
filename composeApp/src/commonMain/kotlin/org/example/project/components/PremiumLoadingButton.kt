package org.example.project.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.theme.AppSizing
import org.example.project.theme.AppSpacing
import org.example.project.theme.premiumShadow

@Composable
fun PremiumLoadingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    text: String,
    enabled: Boolean = true,
    active: Boolean = true // Novo parâmetro para controlar o estado visual
) {
    val enabledColor = Color(0xFF5d8c4a)
    val disabledColor = enabledColor.copy(alpha = 0.5f)
    val inactiveColor = enabledColor.copy(alpha = 0.3f) // Verde semitransparente

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = tween(durationMillis = 100)
    )

    val containerColor = when {
        !enabled -> disabledColor
        !active -> inactiveColor
        else -> enabledColor
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .premiumShadow()
            .scale(scale)
            .fillMaxWidth()
            .height(AppSizing.buttonHeight),
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(AppSpacing.medium),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = Color.White,
            disabledContainerColor = disabledColor,
            disabledContentColor = Color.White.copy(alpha = 0.7f)
        ),
        interactionSource = interactionSource
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.White,
                strokeWidth = 2.5.dp
            )
        } else {
            Text(text)
        }
    }
}
