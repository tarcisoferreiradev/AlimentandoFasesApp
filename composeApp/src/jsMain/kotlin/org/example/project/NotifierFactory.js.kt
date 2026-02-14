package org.example.project

import androidx.compose.runtime.Composable
import org.example.project.services.Notifier

/**
 * JS-specific implementation of the [rememberNotifier] factory.
 */
@Composable
actual fun rememberNotifier(): Notifier {
    return object : Notifier {
        override fun showNotification(title: String, message: String) {
            println("Notification: '$title' - '$message' (no-op on JS)")
        }
    }
}
