package org.example.project

import androidx.compose.runtime.Composable
import org.example.project.services.Notifier

/**
 * A Composable factory function that provides a platform-specific
 * implementation of the [Notifier] interface.
 */
@Composable
expect fun rememberNotifier(): Notifier
