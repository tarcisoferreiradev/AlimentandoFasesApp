package org.example.project.ui.theme

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Implementação `actual` para a plataforma Android.
 *
 * Este Composable gerencia a aparência das barras de sistema e ativa o modo edge-to-edge.
 *
 * @param isLight Se `true`, os ícones da barra de status serão desenhados para um fundo claro (ícones escuros).
 *                Se `false`, os ícones serão desenhados para um fundo escuro (ícones claros).
 */
@Composable
actual fun SystemAppearance(isLight: Boolean) {
    val view = LocalView.current
    // SideEffect garante que este código será executado após cada recomposição bem-sucedida.
    SideEffect {
        val window = (view.context as Activity).window

        // Diretriz Arquitetural: Habilita o modo edge-to-edge, permitindo que o app
        // seja desenhado atrás das barras de sistema. Essencial para que os WindowInsets
        // funcionem corretamente nos componentes do Material 3, como a TopAppBar.
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Define a aparência (clara ou escura) dos ícones da barra de status.
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = isLight
    }
}
