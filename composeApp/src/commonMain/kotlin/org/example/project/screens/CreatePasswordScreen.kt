package org.example.project.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import alimentandofasesapp.composeapp.generated.resources.Res
import alimentandofasesapp.composeapp.generated.resources.mascotesenha
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.components.PremiumLoadingButton
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun CreatePasswordScreen(onContinueClicked: (String) -> Unit, onBackClicked: () -> Unit) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var hasAttemptedContinue by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val confirmPasswordFocusRequester = remember { FocusRequester() }

    val has8Chars = password.length >= 8
    val hasNumber = password.any { it.isDigit() }
    val hasSymbol = password.any { !it.isLetterOrDigit() }
    val passwordsMatch = password.isNotEmpty() && password == confirmPassword
    val isPasswordValid = has8Chars && hasNumber && hasSymbol && passwordsMatch
    val passwordsDoNotMatch = password.isNotEmpty() && confirmPassword.isNotEmpty() && !passwordsMatch

    val backgroundBrush = grainyGradientBrush(
        startColor = Color(0xFFF0F4E8),
        endColor = Color(0xFFC7DAA3),
        grainIntensity = 0.02f
    )

    fun handleContinue() {
        hasAttemptedContinue = true
        if (isPasswordValid) {
            isLoading = true
            scope.launch {
                delay(2000) // Simulate network call
                isLoading = false
                onContinueClicked(password)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                RegistrationAppBar(step = 3, totalSteps = 3, onBackClicked = onBackClicked)
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 40.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.mascotesenha),
                    contentDescription = "Mascote com uma chave na mão",
                    modifier = Modifier.size(150.dp)
                )
                Spacer(Modifier.height(AppSpacing.large))
                Text(
                    text = "Crie sua senha",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5d8c4a)
                )
                Spacer(Modifier.height(AppSpacing.extraLarge))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Senha") },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Campo de senha") },
                    trailingIcon = {
                        val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = if (passwordVisible) "Esconder senha" else "Mostrar senha")
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                        unfocusedTextColor = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = { confirmPasswordFocusRequester.requestFocus() }),
                    singleLine = true
                )
                Spacer(Modifier.height(AppSpacing.medium))
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirmar Senha") },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Campo de senha") },
                    trailingIcon = {
                        val image = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(imageVector = image, contentDescription = if (confirmPasswordVisible) "Esconder senha" else "Mostrar senha")
                        }
                    },
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth().premiumShadow().focusRequester(confirmPasswordFocusRequester),
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
                    isError = passwordsDoNotMatch,
                    supportingText = {
                        if (passwordsDoNotMatch) {
                            Text("As senhas não coincidem")
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { handleContinue() }),
                    singleLine = true
                )

                Spacer(Modifier.height(AppSpacing.large))

                PasswordStrengthIndicator(password = password)

                Spacer(Modifier.height(AppSpacing.large))

                Column(modifier = Modifier.align(Alignment.Start)) {
                    PasswordRequirement(isValid = has8Chars, text = "Mínimo de 8 caracteres", hasAttemptedContinue = hasAttemptedContinue)
                    PasswordRequirement(isValid = hasNumber, text = "Um número", hasAttemptedContinue = hasAttemptedContinue)
                    PasswordRequirement(isValid = hasSymbol, text = "Um símbolo", hasAttemptedContinue = hasAttemptedContinue)
                }

                Spacer(Modifier.height(AppSpacing.extraLarge))

                PremiumLoadingButton(
                    onClick = { handleContinue() },
                    text = "Continuar",
                    isLoading = isLoading,
                    active = isPasswordValid
                )

                Spacer(Modifier.height(AppSpacing.large))
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
private fun PasswordStrengthIndicator(password: String) {
    val strength = remember(password) {
        listOf(
            password.length >= 8,
            password.any { it.isDigit() },
            password.any { !it.isLetterOrDigit() }
        ).count { it }
    }

    val progress by animateFloatAsState(
        targetValue = strength / 3f,
        animationSpec = tween(durationMillis = 300),
        label = "StrengthProgress"
    )

    val color by animateColorAsState(
        targetValue = when (strength) {
            0 -> Color.Transparent
            1 -> Color(0xFFD32F2F) // Vermelho mais escuro
            2 -> Color(0xFFFBC02D) // Amarelo mais escuro
            3 -> Color(0xFF5d8c4a)
            else -> Color.Transparent
        },
        animationSpec = tween(durationMillis = 300),
        label = "StrengthColor"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .background(Color.Gray.copy(alpha = 0.3f), shape = RoundedCornerShape(2.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .height(4.dp)
                .background(color, shape = RoundedCornerShape(2.dp))
        )
    }
}

@Composable
private fun PasswordRequirement(isValid: Boolean, text: String, hasAttemptedContinue: Boolean) {
    val color by animateColorAsState(
        targetValue = if (isValid) Color(0xFF5d8c4a) else if (hasAttemptedContinue) Color.Red else Color.Black,
        animationSpec = tween(durationMillis = 300)
    )

    val icon = if (isValid) Icons.Filled.CheckCircle else Icons.Filled.RadioButtonUnchecked

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(AppSpacing.medium))
        Text(text, color = color, style = MaterialTheme.typography.bodySmall)
    }
}
