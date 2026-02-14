package org.example.project.screens

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import org.example.project.NotificationService

@Composable
actual fun isLandscape(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}

@Composable
actual fun platformTestNotificationHandler(): () -> Unit {
    val context = LocalContext.current
    // Decisão Arquitetural: O handler de teste deve simular o resultado final
    // da notificação agendada para permitir uma validação visual precisa.
    val notificationService = NotificationService(context)

    return {
        notificationService.showNotification(
            title = "Lembrete de Hidratação 💧",
            message = "Bom dia! Comece o dia bem, lembre-se de se hidratar."
        )
    }
}
