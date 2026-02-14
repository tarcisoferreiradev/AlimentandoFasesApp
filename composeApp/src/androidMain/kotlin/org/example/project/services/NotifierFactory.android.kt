package org.example.project.services

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import org.example.project.NotificationService

/**
 * Android-specific implementation of the [rememberNotifier] factory.
 * This implementation uses [LocalContext] to provide a concrete
 * instance of [NotificationService], connecting the common UI code
 * to the platform-specific notification implementation.
 */
@Composable
actual fun rememberNotifier(): Notifier {
    val context = LocalContext.current
    // The NotificationService is remembered with the context as a key.
    // This ensures that the service is a singleton for the given context.
    return remember(context) {
        NotificationService(context)
    }
}
