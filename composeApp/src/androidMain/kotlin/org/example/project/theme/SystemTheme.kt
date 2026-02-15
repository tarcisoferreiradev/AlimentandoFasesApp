package org.example.project.theme

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * [ARQUITETURA KMP] Implementação `actual` para a plataforma Android.
 *
 * Fornece a lógica concreta para controlar as barras de sistema no Android,
 * cumprindo o contrato definido pela declaração `expect` em `commonMain`.
 */
@Composable
internal actual fun SystemTheme(isDarkIcons: Boolean) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        LaunchedEffect(isDarkIcons) {
            val window = (view.context as? Activity)?.window ?: return@LaunchedEffect
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = isDarkIcons
            insetsController.isAppearanceLightNavigationBars = isDarkIcons
        }
    }
}
