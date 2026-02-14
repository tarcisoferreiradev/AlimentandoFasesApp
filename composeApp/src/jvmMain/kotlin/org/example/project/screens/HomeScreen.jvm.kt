package org.example.project.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.rememberWindowState

@Composable
actual fun isLandscape(): Boolean {
    val windowSize = rememberWindowState().size
    return windowSize.width > windowSize.height
}

@Composable
actual fun platformTestNotificationHandler(): () -> Unit {
    return { println("Test notification triggered (no-op on Desktop).") }
}