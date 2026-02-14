package org.example.project.theme

import androidx.compose.runtime.Composable

@Composable
internal actual fun SystemAppearance(darkTheme: Boolean) {
    // No-op on Wasm. A aparência do sistema é controlada pelo navegador e CSS.
}
