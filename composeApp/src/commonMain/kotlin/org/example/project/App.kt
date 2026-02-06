package org.example.project

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.example.project.navigation.Screen
import org.example.project.screens.CreateAccountScreen
import org.example.project.screens.CreatePasswordScreen
import org.example.project.screens.LoginScreen
import org.example.project.screens.MainScreen
import org.example.project.screens.RegistrationBirthdayScreen
import org.example.project.screens.RegistrationNameScreen
import org.example.project.screens.RegistrationProfilePhotoScreen
import org.example.project.screens.SplashScreen
import org.example.project.screens.VerifyEmailScreen
import org.example.project.theme.AlimentandoFasesTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(uiReady: Boolean = false) {
    AlimentandoFasesTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            var currentScreen by remember { mutableStateOf<Screen>(Screen.Splash) }

            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = {
                    fadeIn(animationSpec = tween(200, easing = FastOutSlowInEasing)) +
                            scaleIn(initialScale = 0.98f, animationSpec = tween(200, easing = FastOutSlowInEasing)) togetherWith
                            fadeOut(animationSpec = tween(200, easing = FastOutSlowInEasing)) +
                            scaleOut(targetScale = 0.98f, animationSpec = tween(200, easing = FastOutSlowInEasing))
                }
            ) { screen ->
                when (screen) {
                    is Screen.Splash -> SplashScreen(
                        uiReady = uiReady,
                        onFinish = { currentScreen = Screen.Login }
                    )
                    is Screen.Login -> LoginScreen(
                        onLoginSuccess = { currentScreen = Screen.Main },
                        onRegisterClicked = { currentScreen = Screen.CreateAccount }
                    )
                    is Screen.CreateAccount -> CreateAccountScreen(
                        onContinueClicked = { email -> currentScreen = Screen.VerifyEmail(email) },
                        onBackClicked = { currentScreen = Screen.Login }
                    )
                    is Screen.VerifyEmail -> VerifyEmailScreen(
                        email = screen.email,
                        onVerifyClicked = { currentScreen = Screen.CreatePassword },
                        onBackClicked = { currentScreen = Screen.CreateAccount }
                    )
                    is Screen.CreatePassword -> CreatePasswordScreen(
                        onContinueClicked = { currentScreen = Screen.Main },
                        onBackClicked = { currentScreen = Screen.VerifyEmail("") }
                    )
                    is Screen.RegistrationName -> RegistrationNameScreen(
                        onContinueClicked = { currentScreen = Screen.RegistrationBirthday }
                    )
                    is Screen.RegistrationBirthday -> RegistrationBirthdayScreen(
                        onContinueClicked = { currentScreen = Screen.RegistrationProfilePhoto },
                        onBackClicked = { currentScreen = Screen.RegistrationName }
                    )
                    is Screen.RegistrationProfilePhoto -> RegistrationProfilePhotoScreen(
                        onFinishClicked = { currentScreen = Screen.Main },
                        onSkipClicked = { currentScreen = Screen.Main },
                        onBackClicked = { currentScreen = Screen.RegistrationBirthday }
                    )
                    is Screen.Main -> MainScreen()
                    is Screen.Home, is Screen.Content, is Screen.Community, is Screen.Profile -> MainScreen()
                }
            }
        }
    }
}
