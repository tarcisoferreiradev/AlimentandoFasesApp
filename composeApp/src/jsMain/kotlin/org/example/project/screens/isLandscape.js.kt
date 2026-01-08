package org.example.project.screens

import androidx.compose.runtime.Composable

@Composable
actual fun isLandscape(): Boolean {
    // Web implementation could check window.innerWidth > window.innerHeight
    // For now, we default to portrait to resolve the build error.
    return false
}