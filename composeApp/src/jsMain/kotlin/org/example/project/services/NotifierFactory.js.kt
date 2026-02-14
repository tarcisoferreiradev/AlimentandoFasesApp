package org.example.project.services

import androidx.compose.runtime.Composable

/**
 * JS-specific implementation of the [rememberNotifier] factory.
 * Returns a no-op implementation as notifications are not implemented for this platform.
 */
@Composable
actual fun rememberNotifier(): Notifier {
    return object : Notifier {
        override fun showNotification(title: String, message: String) {
            println("Notification: '$title' - '$message' (no-op on JS)")
        }
    }
}
