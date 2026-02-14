package org.example.project.services

/**
 * Interface que define o contrato para um serviço de notificação,
 * abstraindo a implementação específica da plataforma.
 */
interface Notifier {
    /**
     * Exibe uma notificação no dispositivo.
     *
     * @param title O título da notificação.
     * @param message A mensagem de corpo da notificação.
     */
    fun showNotification(title: String, message: String)
}