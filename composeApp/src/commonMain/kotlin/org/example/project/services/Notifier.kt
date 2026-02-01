package org.example.project.services

import androidx.compose.runtime.Composable

/**
 * Define um contrato para um serviço de notificação que pode ser implementado
 * em cada plataforma (Android, iOS, etc.).
 *
 * Esta abstração é crucial para desacoplar a lógica de negócio e a UI comum
 * das implementações de notificação específicas da plataforma.
 */
interface Notifier {
    /**
     * Exibe uma notificação no sistema.
     *
     * @param title O título da notificação.
     * @param message O corpo da mensagem da notificação.
     */
    fun showNotification(title: String, message: String)
}

/**
 * Declaração `expect` que permite que cada plataforma forneça sua própria
 * implementação de um `Notifier` dentro de um contexto Composable.
 *
 * Em `androidMain`, a implementação `actual` retornará uma instância
 * do `NotificationService`.
 */
@Composable
expect fun rememberNotifier(): Notifier
