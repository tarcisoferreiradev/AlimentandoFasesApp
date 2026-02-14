package org.example.project

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

/**
 * Worker responsável por exibir a notificação de hidratação.
 * Sua única responsabilidade é invocar o NotificationService. A lógica
 * de repetição é agora gerenciada externamente por um PeriodicWorkRequest.
 */
class HydrationReminderWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        // A única responsabilidade: exibir a notificação, respeitando o contrato da interface Notifier.
        val notificationService = NotificationService(applicationContext)
        notificationService.showNotification(
            title = "Hora de se hidratar!",
            message = "Um copo de água agora pode fazer toda a diferença no seu dia."
        )
        return Result.success()
    }
}
