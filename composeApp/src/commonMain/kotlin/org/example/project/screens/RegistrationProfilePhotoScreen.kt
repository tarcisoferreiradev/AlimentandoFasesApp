package org.example.project.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.theme.AppSizing
import org.example.project.theme.AppSpacing
import org.example.project.theme.grainyGradientBrush

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationProfilePhotoScreen(onFinishClicked: () -> Unit, onSkipClicked: () -> Unit, onBackClicked: () -> Unit) {
    val premiumShadow = Modifier.shadow(elevation = 10.dp, ambientColor = Color.Black.copy(alpha = 0.06f), spotColor = Color.Black.copy(alpha = 0.06f), shape = RoundedCornerShape(AppSpacing.medium))
    val primaryGreen = Color(0xFF5d8c4a)

    val backgroundBrush = grainyGradientBrush(
        startColor = Color(0xFFF0F4E8),
        endColor = Color(0xFFC7DAA3),
        grainIntensity = 0.02f
    )

    // --- ANIMAÇÃO DE CLIQUE ---
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = tween(durationMillis = 100)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush)
    ) {
        Scaffold(
            topBar = {
                RegistrationAppBar(step = 3, totalSteps = 3, onBackClicked = onBackClicked)
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(Modifier.weight(1f))
                Text(
                    text = "Adicione uma foto de perfil",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = primaryGreen
                )
                Spacer(Modifier.height(AppSpacing.medium))
                Text(
                    text = "Isso ajuda a personalizar sua experiência e a conectar-se com a comunidade.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = AppSpacing.medium)
                )
                Spacer(Modifier.height(AppSpacing.extraLarge))

                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .border(2.dp, primaryGreen, CircleShape)
                        .clickable { /* TODO: Adicionar lógica para escolher foto */ },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Adicionar foto", modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Spacer(Modifier.height(AppSpacing.extraLarge))

                Button(
                    onClick = onFinishClicked,
                    modifier = Modifier
                        .scale(scale)
                        .width(320.dp)
                        .height(AppSizing.buttonHeight),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryGreen, 
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(50),
                    interactionSource = interactionSource
                ) {
                    Text("CONCLUIR")
                }
                Spacer(Modifier.height(AppSpacing.medium))
                TextButton(onClick = onSkipClicked) {
                    Text("PULAR POR AGORA", color = Color.Black)
                }
                Spacer(Modifier.weight(2f))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegistrationAppBar(
    step: Int,
    totalSteps: Int,
    onBackClicked: () -> Unit
) {
    Column {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    "Passo $step de $totalSteps",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClicked) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color(0xFF5d8c4a))
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 1..totalSteps) {
                val color = if (i <= step) Color(0xFF5d8c4a) else MaterialTheme.colorScheme.surfaceVariant
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .background(color, shape = RoundedCornerShape(2.dp))
                )
            }
        }
    }
}
