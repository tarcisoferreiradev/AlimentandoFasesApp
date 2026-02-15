package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import org.example.project.screens.SplashViewModel

class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var uiReady by mutableStateOf(false)

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { !splashViewModel.isReady.value }

        splashScreen.setOnExitAnimationListener { splashScreenView -> splashScreenView.remove() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                splashViewModel.isReady.collect { isReady ->
                    if (isReady) {
                        uiReady = true
                    }
                }
            }
        }

        NotificationService(this)

        // [DIRETRIZ DE ARQUITETURA] Desacopla a Window da Activity do conteúdo.
        // Esta é a única configuração de janela necessária na Activity.
        // Toda a estilização (cores, ícones) é delegada à camada Compose.
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            // O Composable 'App' agora é totalmente responsável pela UI.
            App(uiReady = uiReady)
        }
    }
}
