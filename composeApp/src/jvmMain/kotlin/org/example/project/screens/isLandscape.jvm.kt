package org.example.project.screens

import androidx.compose.runtime.Composable

@Composable
actual fun isLandscape(): Boolean {
    // A more complex implementation could get the window size.
    // For now, we default to portrait to resolve the build error.
    return false
}