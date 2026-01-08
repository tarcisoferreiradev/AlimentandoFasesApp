package org.example.project.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import alimentandofasesapp.composeapp.generated.resources.Res
import alimentandofasesapp.composeapp.generated.resources.mascotequalseunome
import org.example.project.theme.AppSizing
import org.example.project.theme.AppSpacing
import org.example.project.theme.grainyGradientBrush
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RegistrationNameScreen(onContinueClicked: () -> Unit) {
    var name by remember { mutableStateOf("") }
    val primaryGreen = Color(0xFF5d8c4a) // Cor principal
    val premiumShadow = Modifier.shadow(elevation = 10.dp, ambientColor = Color.Black.copy(alpha = 0.06f), spotColor = Color.Black.copy(alpha = 0.06f), shape = RoundedCornerShape(AppSpacing.medium))
    val inputUnfocusedBorderColor = Color.Black.copy(alpha = 0.15f)

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
                CenterAlignedTopAppBar(
                    title = { Text("Seu Nome") },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(Res.drawable.mascotequalseunome),
                    contentDescription = "Mascote",
                    modifier = Modifier.size(300.dp)
                )
                Spacer(Modifier.height(AppSpacing.large))
                Text(
                    text = "Como podemos te chamar?",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(AppSpacing.extraLarge))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome") },
                    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(premiumShadow),
                    shape = RoundedCornerShape(AppSpacing.medium),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = primaryGreen,
                        unfocusedBorderColor = inputUnfocusedBorderColor,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    singleLine = true
                )
                Spacer(Modifier.height(AppSpacing.large))
                val enabledColor = primaryGreen
                val disabledColor = enabledColor.copy(alpha = 0.5f)

                Button(
                    onClick = onContinueClicked,
                    enabled = name.isNotBlank(),
                    modifier = Modifier
                        .scale(scale)
                        .width(320.dp)
                        .height(AppSizing.buttonHeight),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = enabledColor,
                        contentColor = Color.White,
                        disabledContainerColor = disabledColor,
                        disabledContentColor = Color.White.copy(alpha = 0.7f)
                    ),
                    shape = RoundedCornerShape(50),
                    interactionSource = interactionSource
                ) {
                    Text("CONTINUAR")
                }
                Spacer(Modifier.weight(2f))
            }
        }
    }
}
