package org.example.project.services

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import org.example.project.NotificationService

/**
 * Implementação `actual` para a plataforma Android.
 *
 * Esta função utiliza o `LocalContext` para obter o contexto do Android e o utiliza
 * para instanciar e fornecer o `NotificationService` sempre que a função `expect`
 * `rememberNotifier()` for chamada a partir do código comum no contexto do Android.
 */
@Composable
actual fun rememberNotifier(): Notifier {
    val context = LocalContext.current
    return remember { NotificationService(context) }
}
