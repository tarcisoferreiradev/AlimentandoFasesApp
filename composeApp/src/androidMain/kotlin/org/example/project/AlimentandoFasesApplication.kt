package org.example.project

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import org.example.project.workers.DailyReminderWorker
import java.util.Calendar
import java.util.concurrent.TimeUnit

class AlimentandoFasesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        scheduleDailyReminder()
    }

    private fun scheduleDailyReminder() {
        // Decisão Arquitetural: Agendar um trabalho periódico único.
        // A política `KEEP` garante que, se o trabalho já estiver agendado,
        // nenhuma nova instância será criada. Isso previne agendamentos duplicados
        // toda vez que o app é iniciado.
        val reminderRequest = PeriodicWorkRequestBuilder<DailyReminderWorker>(
            repeatInterval = 24, // Repetir a cada 24 horas
            repeatIntervalTimeUnit = TimeUnit.HOURS
        )
        // Decisão Arquitetural de Compatibilidade: Revertendo para a API legada de setInitialDelay
        // que aceita um Long e uma TimeUnit. Esta abordagem é necessária para garantir
        // a compatibilidade com a minSdk do projeto (API 24), evitando crashes
        // em dispositivos com Android 7 (Nougat).
        .setInitialDelay(calculateDelayUntil8AM(), TimeUnit.MILLISECONDS) // Define o atraso para a primeira execução
        .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "daily_hydration_reminder", // Um nome único para este trabalho
            ExistingPeriodicWorkPolicy.KEEP, // Política para não duplicar
            reminderRequest
        )
    }

    /**
     * Calcula o tempo em milissegundos desde agora até as 8:00 da manhã.
     * Esta é a regra de negócio crítica para garantir que a primeira notificação
     * ocorra no horário correto.
     *
     * @return O atraso em milissegundos.
     */
    private fun calculateDelayUntil8AM(): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)

            // Se o horário de 8:00 de hoje já passou, agenda para amanhã.
            if (before(Calendar.getInstance())) {
                add(Calendar.DATE, 1)
            }
        }
        return calendar.timeInMillis - System.currentTimeMillis()
    }
}
