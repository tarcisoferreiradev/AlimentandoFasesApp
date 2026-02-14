package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import org.example.project.services.Notifier

/**
 * Android-specific implementation of the [rememberNotifier] factory.
 */
@Composable
actual fun rememberNotifier(): Notifier {
    val context = LocalContext.current
    return remember(context) {
        NotificationService(context)
    }
}
