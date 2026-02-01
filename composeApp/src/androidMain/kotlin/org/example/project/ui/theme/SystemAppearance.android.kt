package org.example.project.ui.theme

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Implementação `actual` para a plataforma Android.
 *
 * Este Composable obtém a `Window` da `Activity` atual e utiliza o `WindowCompat`
 * para instruir o sistema a desenhar os ícones da barra de status (superior) para um fundo claro ou escuro.
 * A barra de navegação (inferior) não é afetada.
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
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = isLight
    }
}
