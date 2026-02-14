package org.example.project.theme

import androidx.compose.runtime.Composable

@Composable
internal actual fun SystemAppearance(darkTheme: Boolean) {
    // No-op on iOS. A aparência do sistema é gerenciada nativamente.
}
