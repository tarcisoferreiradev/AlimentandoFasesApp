package org.example.project.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import alimentandofasesapp.composeapp.generated.resources.Res
import alimentandofasesapp.composeapp.generated.resources.facebook
import alimentandofasesapp.composeapp.generated.resources.google
import alimentandofasesapp.composeapp.generated.resources.logotipo_da_apple
import alimentandofasesapp.composeapp.generated.resources.mascotequalseunome
import alimentandofasesapp.composeapp.generated.resources.microsoft
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.components.PremiumLoadingButton
import org.example.project.theme.AppSizing
import org.example.project.theme.AppSpacing
import org.example.project.theme.grainyGradientBrush
import org.example.project.theme.premiumShadow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

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

fun String.isValidEmail(): Boolean {
    val emailRegex = Regex("[a-zA-Z0-9._%+-]+@([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}")
    return this.isNotBlank() && emailRegex.matches(this)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun CreateAccountScreen(onContinueClicked: (String) -> Unit, onBackClicked: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val backgroundBrush = grainyGradientBrush(
        startColor = Color(0xFFF0F4E8),
        endColor = Color(0xFFC7DAA3),
        grainIntensity = 0.02f
    )

    fun handleContinue() {
        if (email.isValidEmail()) {
            isLoading = true
            scope.launch {
                delay(2000) // Simulate network call
                isLoading = false
                onContinueClicked(email)
            }
        }
    }

    val isEmailValid = email.isValidEmail()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                RegistrationAppBar(step = 1, totalSteps = 3, onBackClicked = onBackClicked)
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 40.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(Res.drawable.mascotequalseunome),
                    contentDescription = "Mascote",
                    modifier = Modifier.size(150.dp)
                )
                Spacer(Modifier.height(AppSpacing.large))
                Text(
                    text = "Crie uma nova conta",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start),
                    color = Color(0xFF5d8c4a)
                )
                Spacer(Modifier.height(AppSpacing.medium))
                Text(
                    text = "Comece criando sua conta gratuita. Isso ajuda a manter seu acesso mais fácil e seguro.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(Modifier.height(AppSpacing.extraLarge))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("E-mail") },
                    leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth().premiumShadow(),
                    shape = RoundedCornerShape(AppSpacing.medium),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true
                )
                Spacer(Modifier.height(AppSpacing.large))
                PremiumLoadingButton(
                    onClick = { handleContinue() },
                    text = "Continuar com E-mail",
                    isLoading = isLoading,
                    active = isEmailValid
                )
                Spacer(Modifier.height(AppSpacing.large))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Divider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outlineVariant)
                    Text("ou", modifier = Modifier.padding(horizontal = AppSpacing.medium), color = Color.Black, style = MaterialTheme.typography.bodySmall)
                    Divider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outlineVariant)
                }
                Spacer(Modifier.height(AppSpacing.large))
                Column(modifier = Modifier.fillMaxWidth()) {
                    SocialLoginButton(text = "Entrar com Google", icon = painterResource(Res.drawable.google), onClick = {/*TODO*/})
                    Spacer(Modifier.height(AppSpacing.medium))
                    SocialLoginButton(text = "Entrar com Apple", icon = painterResource(Res.drawable.logotipo_da_apple), onClick = {/*TODO*/})
                    Spacer(Modifier.height(AppSpacing.medium))
                    SocialLoginButton(text = "Entrar com Microsoft", icon = painterResource(Res.drawable.microsoft), onClick = {/*TODO*/})
                    Spacer(Modifier.height(AppSpacing.medium))
                    SocialLoginButton(text = "Entrar com Facebook", icon = painterResource(Res.drawable.facebook), onClick = {/*TODO*/})
                }
                Spacer(Modifier.weight(1f))

                val termsText = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append("Ao criar uma conta ou fazer login, você concorda com nossos ")
                    }
                    withStyle(style = SpanStyle(color = Color(0xFF5d8c4a), fontWeight = FontWeight.Bold)) {
                        pushStringAnnotation("action", "terms")
                        append("Termos de Uso")
                        pop()
                    }
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append(" • ")
                    }
                    withStyle(style = SpanStyle(color = Color(0xFF5d8c4a), fontWeight = FontWeight.Bold)) {
                        pushStringAnnotation("action", "privacy")
                        append("Política de Privacidade")
                        pop()
                    }
                }

                ClickableText(
                    text = termsText,
                    onClick = { offset ->
                        termsText.getStringAnnotations("action", offset, offset).firstOrNull()?.let { annotation ->
                            when (annotation.item) {
                                "terms" -> { /* TODO: Handle Terms of Use click */ }
                                "privacy" -> { /* TODO: Handle Privacy Policy click */ }
                            }
                        }
                    },
                    style = MaterialTheme.typography.labelSmall.copy(textAlign = TextAlign.Center),
                    modifier = Modifier.padding(horizontal = AppSpacing.medium, vertical = AppSpacing.large)
                )
            }
        }
    }
}


@Composable
private fun SocialLoginButton(text: String, icon: Painter, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(AppSizing.buttonHeight).premiumShadow(),
        shape = RoundedCornerShape(AppSpacing.medium),
        border = BorderStroke(1.dp, Color.Black),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Image(painter = icon, contentDescription = null, modifier = Modifier.size(22.dp), contentScale = ContentScale.Fit)
            Text(text, modifier = Modifier.padding(start = AppSpacing.medium))
        }
    }
}
