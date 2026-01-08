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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import alimentandofasesapp.composeapp.generated.resources.Res
import alimentandofasesapp.composeapp.generated.resources.facebook
import alimentandofasesapp.composeapp.generated.resources.google
import alimentandofasesapp.composeapp.generated.resources.logo_app1
import alimentandofasesapp.composeapp.generated.resources.logotipo_da_apple
import alimentandofasesapp.composeapp.generated.resources.microsoft
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.theme.AppSizing
import org.example.project.theme.AppSpacing
import org.example.project.theme.grainyGradientBrush
import org.example.project.theme.premiumShadow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onRegisterClicked: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var serverError by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = tween(durationMillis = 100)
    )

    val backgroundBrush = grainyGradientBrush(
        startColor = Color(0xFFF0F4E8),
        endColor = Color(0xFFC7DAA3),
        grainIntensity = 0.02f
    )

    fun validateFields(): Boolean {
        emailError = if (email.isBlank()) "O e-mail não pode estar em branco" else null
        passwordError = if (password.isBlank()) "A senha não pode estar em branco" else null
        return emailError == null && passwordError == null
    }

    fun handleLogin() {
        onLoginSuccess()
    }

    Box(
        modifier = Modifier.fillMaxSize().background(backgroundBrush)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 40.dp, vertical = AppSpacing.extraLarge),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(Res.drawable.logo_app1),
                contentDescription = "Logo Alimentando Fases",
                modifier = Modifier.size(AppSizing.logo * 1f)
            )
            Spacer(Modifier.height(AppSpacing.medium))
            Text(
                text = "ALIMENTANDO FASES",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF5d8c4a),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Nutrição para todo ciclo da vida",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                color = Color.Black
            )

            Spacer(Modifier.height(AppSpacing.extraLarge))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it; emailError = null; serverError = null },
                label = { Text("E-mail") },
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
                modifier = Modifier.fillMaxWidth().premiumShadow(),
                shape = RoundedCornerShape(AppSpacing.medium),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    errorSupportingTextColor = MaterialTheme.colorScheme.error
                ),
                isError = emailError != null || serverError != null,
                supportingText = { if (emailError != null) Text(emailError!!) },
                singleLine = true
            )

            Spacer(Modifier.height(AppSpacing.small))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it; passwordError = null; serverError = null },
                label = { Text("Senha") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = if (passwordVisible) "Hide password" else "Show password")
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth().premiumShadow(),
                shape = RoundedCornerShape(AppSpacing.medium),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    errorSupportingTextColor = MaterialTheme.colorScheme.error
                ),
                isError = passwordError != null || serverError != null,
                supportingText = { if (passwordError != null) Text(passwordError!!) },
                singleLine = true
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-8).dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.offset(x = (-12).dp)
                ) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFF5d8c4a),
                            uncheckedColor = Color.Black,
                            checkmarkColor = Color.White
                        )
                    )
                    Text("Lembrar de mim", style = MaterialTheme.typography.bodySmall, color = Color.Black)
                }
                ClickableText(
                    text = buildAnnotatedString { append("Esqueceu a senha?") },
                    onClick = { /* TODO */ },
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
                )
            }

            if (serverError != null) {
                Spacer(Modifier.height(AppSpacing.small))
                Text(
                    text = serverError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(Modifier.height(AppSpacing.medium))

            Button(
                onClick = { handleLogin() },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppSizing.buttonHeight)
                    .premiumShadow()
                    .scale(scale),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5d8c4a), contentColor = Color.White),
                shape = RoundedCornerShape(AppSpacing.medium),
                interactionSource = interactionSource
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                } else {
                    Text("ENTRAR")
                }
            }

            val annotatedString = buildAnnotatedString {
                append("Não tem uma conta? ")
                withStyle(style = SpanStyle(color = Color(0xFF5d8c4a), fontWeight = FontWeight.Bold)) {
                    pushStringAnnotation("action", "register")
                    append("Criar conta")
                    pop()
                }
            }

            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations("action", offset, offset).firstOrNull()?.let {
                        onRegisterClicked()
                    }
                },
                modifier = Modifier.padding(top = AppSpacing.large)
            )

            Spacer(Modifier.height(AppSpacing.medium))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outlineVariant)
                Text("ou", modifier = Modifier.padding(horizontal = AppSpacing.medium), color = Color.Black, style = MaterialTheme.typography.bodySmall)
                HorizontalDivider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outlineVariant)
            }
            Spacer(Modifier.height(AppSpacing.medium))

            // Social Login Buttons
            SocialLoginButton(text = "Entrar com Google", icon = painterResource(Res.drawable.google), onClick = {/*TODO*/})
            Spacer(Modifier.height(AppSpacing.medium))
            SocialLoginButton(text = "Entrar com Apple", icon = painterResource(Res.drawable.logotipo_da_apple), onClick = {/*TODO*/})
            Spacer(Modifier.height(AppSpacing.medium))
            SocialLoginButton(text = "Entrar com Microsoft", icon = painterResource(Res.drawable.microsoft), onClick = {/*TODO*/})
            Spacer(Modifier.height(AppSpacing.medium))
            SocialLoginButton(text = "Entrar com Facebook", icon = painterResource(Res.drawable.facebook), onClick = {/*TODO*/})

            Spacer(Modifier.weight(1f))

            val termsText = buildAnnotatedString {
                append("Ao criar uma conta ou fazer login, você concorda com nossos ")
                withStyle(style = SpanStyle(color = Color(0xFF5d8c4a), fontWeight = FontWeight.Bold)) {
                    pushStringAnnotation("action", "terms")
                    append("Termos de Uso")
                    pop()
                }
                append(" • ")
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
                style = MaterialTheme.typography.labelSmall.copy(textAlign = TextAlign.Center, color = Color.Black),
                modifier = Modifier.padding(horizontal = AppSpacing.medium, vertical = AppSpacing.large)
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun SocialLoginButton(text: String, icon: androidx.compose.ui.graphics.painter.Painter, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = tween(durationMillis = 100)
    )

    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(AppSizing.buttonHeight)
            .premiumShadow()
            .scale(scale),
        shape = RoundedCornerShape(AppSpacing.medium),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
        interactionSource = interactionSource
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Image(painter = icon, contentDescription = null, modifier = Modifier.size(22.dp), contentScale = ContentScale.Fit)
            Text(text, modifier = Modifier.padding(start = AppSpacing.medium))
        }
    }
}
