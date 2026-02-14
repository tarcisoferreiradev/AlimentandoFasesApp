package org.example.project.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.UIKit.UIDevice

@Composable
actual fun isLandscape(): Boolean {
    return remember { UIDevice.currentDevice.orientation.isLandscape }
}

@Composable
actual fun platformTestNotificationHandler(): () -> Unit {
    return { println("Test notification triggered (no-op on iOS).") }
}