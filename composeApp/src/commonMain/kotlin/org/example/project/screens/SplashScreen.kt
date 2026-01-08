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
import androidx.compose.foundation.layout.offset
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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

private object SplashScreenDefaults {
    // Colors
    val BackgroundColor = Color(0xFFFBF9F3)
    val TextColor = Color(0xFF38761d)

    // Durations
    const val EntranceDuration = 800
    const val ScaleEntranceDuration = 1000
    const val ExitDuration = 400
    const val TextEntranceDelay = 400L
    const val TotalScreenTime = 2800L
    const val ExitStaggerDelay = 100L

    // Animation Values
    const val InitialOffsetY = 15f
    const val InitialScale = 0.95f
    const val ExitLogoOffsetY = -30f
    const val ExitTextOffsetY = 30f
    const val OffsetStaggerDelay = 50L
    const val ScaleStaggerDelay = 80L
    const val FinalLetterSpacing = 0.02f

    // Easings
    val LogoScaleEasing = CubicBezierEasing(0.22f, 0.61f, 0.36f, 1f)
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SplashScreen(onFinish: () -> Unit) {

    val logoAlpha = remember { Animatable(0f) }
    val logoOffsetY = remember { Animatable(SplashScreenDefaults.InitialOffsetY) }
    val logoScale = remember { Animatable(SplashScreenDefaults.InitialScale) }
    val parallaxLogoOffsetY = remember { Animatable(0f) }
    val parallaxTextOffsetY = remember { Animatable(0f) }

    val fullText = "ALIMENTANDO FASES"
    val textAlpha = remember { Animatable(0f) }
    val textScale = remember { Animatable(0.98f) }
    val textOffsetY = remember { Animatable(0f) }


    LaunchedEffect(key1 = Unit) {
        // --- ENTRANCE ANIMATIONS ---
        launch {
            // Opacity First, Motion Later
            launch {
                logoAlpha.animateTo(1f, tween(180))
                delay(40)
                logoOffsetY.animateTo(0f, tween(260, easing = FastOutSlowInEasing))
            }
            launch {
                delay(SplashScreenDefaults.ScaleStaggerDelay)
                logoScale.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = SplashScreenDefaults.ScaleEntranceDuration,
                        easing = SplashScreenDefaults.LogoScaleEasing
                    )
                )
                // Breathing animation
                logoScale.animateTo(
                    targetValue = 1.015f,
                    animationSpec = tween(durationMillis = 650, easing = FastOutSlowInEasing)
                )
                logoScale.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 650, easing = FastOutSlowInEasing)
                )
            }
        }

        // Fade + Blur Mental Text Animation
        launch {
            delay(SplashScreenDefaults.TextEntranceDelay)
            launch {
                textAlpha.animateTo(
                    1f,
                    tween(320, easing = CubicBezierEasing(0.2f, 0.8f, 0.2f, 1f))
                )
            }
            launch {
                textScale.animateTo(
                    1f,
                    tween(420, easing = FastOutSlowInEasing)
                )
            }
        }

        // Gentle Parallax Drift
        launch {
            delay(SplashScreenDefaults.TextEntranceDelay + 500)
            launch {
                parallaxLogoOffsetY.animateTo(
                    targetValue = 2f,
                    animationSpec = tween(durationMillis = 1800, easing = FastOutSlowInEasing)
                )
            }
            launch {
                parallaxTextOffsetY.animateTo(
                    targetValue = -2f,
                    animationSpec = tween(durationMillis = 1800, easing = FastOutSlowInEasing)
                )
            }
        }

        // --- PAUSE & PREPARE FOR EXIT ---
        val exitAnimationTotalDuration = SplashScreenDefaults.ExitDuration + SplashScreenDefaults.ExitStaggerDelay
        val timeBeforeExit = SplashScreenDefaults.TotalScreenTime - exitAnimationTotalDuration
        delay(timeBeforeExit)

        // Micro pause
        delay(120)

        // --- ASYMMETRIC EXIT ANIMATIONS ---
        val exitJobs = mutableListOf<Job>()

        // Text fades out first with a drift
        exitJobs += launch {
            textAlpha.animateTo(0f, tween(200, easing = FastOutSlowInEasing))
        }
        exitJobs += launch {
            textOffsetY.animateTo(SplashScreenDefaults.ExitTextOffsetY, tween(200, easing = FastOutSlowInEasing))
        }


        // Logo exits with a slight delay
        exitJobs += launch {
            delay(SplashScreenDefaults.ExitStaggerDelay)
            logoAlpha.animateTo(0f, tween(SplashScreenDefaults.ExitDuration, easing = FastOutSlowInEasing))
        }
        exitJobs += launch {
            delay(SplashScreenDefaults.ExitStaggerDelay)
            logoOffsetY.animateTo(SplashScreenDefaults.ExitLogoOffsetY, tween(SplashScreenDefaults.ExitDuration, easing = FastOutSlowInEasing))
        }

        joinAll(*exitJobs.toTypedArray())

        // --- FINISH ---
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SplashScreenDefaults.BackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.offset(y = (-24).dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.logo_app1),
                contentDescription = "Logo Alimentando Fases",
                modifier = Modifier
                    .size(220.dp)
                    .graphicsLayer {
                        alpha = logoAlpha.value
                        translationY = logoOffsetY.value + parallaxLogoOffsetY.value
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
                    translationY = textOffsetY.value + parallaxTextOffsetY.value
                }
            )
        }
    }
}
