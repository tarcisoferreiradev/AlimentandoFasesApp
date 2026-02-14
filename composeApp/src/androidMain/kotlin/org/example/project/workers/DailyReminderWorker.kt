package org.example.project.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.example.project.NotificationService

/**
 * Um Worker que executa a tarefa de exibir a notificação de lembrete diário.
 * Sua única responsabilidade é instanciar e invocar o NotificationService com o
 * conteúdo correto para o lembrete matinal.
 */
class DailyReminderWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val notificationService = NotificationService(applicationContext)

        // Decisão Arquitetural: O conteúdo da notificação (título e mensagem)
        // é definido pelo seu contexto de chamada. Este Worker é responsável
        // pelo lembrete matinal, portanto, ele fornece a mensagem apropriada.
        notificationService.showNotification(
            title = "Lembrete de Hidratação 💧",
            message = "Bom dia! Comece o dia bem, lembre-se de se hidratar."
        )

        // Retorna 'success' para informar ao WorkManager que a tarefa foi concluída.
        return Result.success()
    }
}