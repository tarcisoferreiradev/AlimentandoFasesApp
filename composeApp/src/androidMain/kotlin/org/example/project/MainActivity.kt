package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // A função é chamada sem parâmetros para habilitar o modo edge-to-edge
        enableEdgeToEdge()
        
        WindowCompat.getInsetsController(window, window.decorView).apply {
            // Define os ícones da barra de status (superior) como claros
            isAppearanceLightStatusBars = false
            // Define os ícones da barra de navegação (inferior) como claros
            isAppearanceLightNavigationBars = false
        }

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
