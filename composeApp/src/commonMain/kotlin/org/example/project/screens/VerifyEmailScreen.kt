package org.example.project.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import alimentandofasesapp.composeapp.generated.resources.Res
import alimentandofasesapp.composeapp.generated.resources.mascoteseuemail
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.components.PremiumLoadingButton
import org.example.project.theme.AppSpacing
import org.example.project.theme.grainyGradientBrush
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyEmailScreen(email: String, onVerifyClicked: () -> Unit, onBackClicked: () -> Unit) {
    var otpValue by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val backgroundBrush = grainyGradientBrush(
        startColor = Color(0xFFF0F4E8),
        endColor = Color(0xFFC7DAA3),
        grainIntensity = 0.02f
    )

    fun handleVerify(){
        isLoading = true
        scope.launch {
            delay(2000) // Simulate network call
            isLoading = false
            onVerifyClicked()
        }
    }

    val isOtpComplete = otpValue.length == 6

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                RegistrationAppBar(
                    step = 2,
                    totalSteps = 3,
                    onBackClicked = onBackClicked
                )
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
                    painter = painterResource(Res.drawable.mascoteseuemail),
                    contentDescription = "Mascote com um celular na mão",
                    modifier = Modifier.height(300.dp)
                )

                Spacer(Modifier.height(AppSpacing.large))

                Text(
                    text = "Verifique seu e-mail",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5d8c4a)
                )
                Spacer(Modifier.height(AppSpacing.medium))
                Text(
                    text = "Enviamos um código de 6 dígitos para $email. Insira-o abaixo.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    textAlign = TextAlign.Center

                )
                Spacer(Modifier.height(AppSpacing.extraLarge))

                OtpTextField(otpText = otpValue, onOtpTextChange = { value -> otpValue = value })

                Spacer(Modifier.height(AppSpacing.large))
                PremiumLoadingButton(
                    isLoading = isLoading,
                    text = "Verificar e-mail",
                    onClick = { handleVerify() },
                    enabled = true, // O botão está sempre habilitado para clique
                    active = isOtpComplete // O estado visual depende do preenchimento do OTP
                )
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
fun OtpTextField(
    otpText: String,
    onOtpTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    val verde = Color(0xFF5d8c4a)

    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val cursorAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cursor alpha"
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    BasicTextField(
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= 6 && it.text.all { c -> c.isDigit() }) {
                onOtpTextChange(it.text)
            }
        },
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { focusState -> isFocused = focusState.isFocused },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(6) { index ->
                    val char = otpText.getOrNull(index)?.toString() ?: ""
                    val isCurrentBox = index == otpText.length

                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .padding(horizontal = 4.dp)
                            .border(
                                width = 1.dp,
                                color = if (char.isNotEmpty() || (isCurrentBox && isFocused)) verde else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(AppSpacing.medium)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isCurrentBox && isFocused) {
                            Box(
                                modifier = Modifier
                                    .width(2.dp)
                                    .height(20.dp)
                                    .background(verde.copy(alpha = cursorAlpha))
                            )
                        }
                        Text(
                            text = char,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = verde
                        )
                    }
                }
            }
        }
    )
}
