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

        // [ARQUITETURA] Controle total da transição da splash screen.
        // DECISÃO: A animação de saída padrão da splash nativa é desabilitada.
        // Ao receber o sinal, a view da splash é removida imediatamente, sem animações
        // concorrentes. Isso entrega o controle total e instantâneo à UI do Compose,
        // garantindo uma transição imperceptível e eliminando o "flicker" final.
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

        // A instanciação do NotificationService é suficiente.
        // O canal de notificação agora é criado em seu bloco `init`.
        NotificationService(this)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            App(uiReady = uiReady)
        }
    }
}
