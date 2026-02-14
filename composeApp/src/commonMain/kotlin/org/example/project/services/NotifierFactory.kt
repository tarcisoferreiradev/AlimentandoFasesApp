package org.example.project.services

import androidx.compose.runtime.Composable

/**
 * A Composable factory function that provides a platform-specific
 * implementation of the [Notifier] interface.
 */
@Composable
expect fun rememberNotifier(): Notifier
