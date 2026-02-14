package org.example.project.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.browser.window
import org.w3c.dom.events.Event

@Composable
actual fun isLandscape(): Boolean {
    var isLandscape by remember { mutableStateOf(window.innerWidth > window.innerHeight) }

    LaunchedEffect(Unit) {
        val onResize = { _: Event -> isLandscape = window.innerWidth > window.innerHeight }
        window.addEventListener("resize", onResize)
    }

    return isLandscape
}

@Composable
actual fun platformTestNotificationHandler(): () -> Unit {
    return { println("Test notification triggered (no-op on Wasm).") }
}