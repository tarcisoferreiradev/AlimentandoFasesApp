package org.example.project.screens

import alimentandofasesapp.composeapp.generated.resources.Res
import alimentandofasesapp.composeapp.generated.resources.logo_app1
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import org.example.project.ui.theme.SystemAppearance
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

private object SplashScreenDefaults {
    val BackgroundColor = Color(0xFFFBF9F3)
    val TextColor = Color(0xFF38761d)

    // Timings ajustados para uma experiência contínua pós-splash nativa
    const val TotalScreenTime = 1800L // Tempo total reduzido
    const val TextEntranceDelay = 100L  // Texto aparece mais cedo
    const val ExitDuration = 400
    const val ExitStaggerDelay = 100L

    // Valores de animação para saída
    const val ExitLogoOffsetY = -30f
    const val ExitTextOffsetY = 30f

    const val FinalLetterSpacing = 0.02f
}

/**
 * [ARQUITETURA] SplashScreen Refatorada para Transição Contínua
 *
 * A animação de "entrada" foi removida. O Composable agora inicializa com o logo
 * já visível, em um estado idêntico ao da splash screen nativa. A animação começa
 * com os efeitos sutis, dando a impressão de que a logo estática "ganhou vida".
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun SplashScreen(
    uiReady: Boolean,
    onFinish: () -> Unit
) {
    SystemAppearance(isLight = true)

    // Estado inicial "quente": O logo começa visível e centralizado.
    val logoAlpha = remember { Animatable(1f) }
    val logoOffsetY = remember { Animatable(0f) }
    val logoScale = remember { Animatable(1f) }

    val fullText = "ALIMENTANDO FASES"
    val textAlpha = remember { Animatable(0f) }
    val textScale = remember { Animatable(0.98f) }
    val textOffsetY = remember { Animatable(0f) }

    LaunchedEffect(uiReady) {
        if (uiReady) {
            // FASE 1: Animação de texto e "respiração" do logo
            launch {
                delay(SplashScreenDefaults.TextEntranceDelay)
                launch {
                    textAlpha.animateTo(1f, tween(320, easing = CubicBezierEasing(0.2f, 0.8f, 0.2f, 1f)))
                }
                launch {
                    textScale.animateTo(1f, tween(420, easing = FastOutSlowInEasing))
                }
            }
            launch {
                // Animação sutil de "respiração" para dar vida ao logo
                logoScale.animateTo(1.02f, tween(800, easing = FastOutSlowInEasing))
                logoScale.animateTo(1f, tween(800, easing = FastOutSlowInEasing))
            }

            // --- PAUSA & PREPARA PARA SAIR ---
            val exitAnimationTotalDuration = SplashScreenDefaults.ExitDuration + SplashScreenDefaults.ExitStaggerDelay
            val timeBeforeExit = SplashScreenDefaults.TotalScreenTime - exitAnimationTotalDuration
            delay(timeBeforeExit)

            delay(120)

            // --- FASE 2: Animações de Saída Assimétricas ---
            val exitJobs = mutableListOf<Job>()

            exitJobs += launch {
                textAlpha.animateTo(0f, tween(200, easing = FastOutSlowInEasing))
            }
            exitJobs += launch {
                textOffsetY.animateTo(SplashScreenDefaults.ExitTextOffsetY, tween(200, easing = FastOutSlowInEasing))
            }

            exitJobs += launch {
                delay(SplashScreenDefaults.ExitStaggerDelay)
                logoAlpha.animateTo(0f, tween(SplashScreenDefaults.ExitDuration, easing = FastOutSlowInEasing))
            }
            exitJobs += launch {
                delay(SplashScreenDefaults.ExitStaggerDelay)
                logoOffsetY.animateTo(SplashScreenDefaults.ExitLogoOffsetY, tween(SplashScreenDefaults.ExitDuration, easing = FastOutSlowInEasing))
            }

            joinAll(*exitJobs.toTypedArray())

            onFinish()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SplashScreenDefaults.BackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.logo_app1),
                contentDescription = "Logo Alimentando Fases",
                modifier = Modifier
                    .size(220.dp)
                    .graphicsLayer {
                        alpha = logoAlpha.value
                        translationY = logoOffsetY.value
                        scaleX = logoScale.value
                        scaleY = logoScale.value
                    }
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = fullText,
                color = SplashScreenDefaults.TextColor,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = SplashScreenDefaults.FinalLetterSpacing.em
                ),
                modifier = Modifier.graphicsLayer {
                    alpha = textAlpha.value
                    scaleX = textScale.value
                    scaleY = textScale.value
                    translationY = textOffsetY.value
                }
            )
        }
    }
}
