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
        // [CORREÇÃO] Agendar um trabalho periódico para o lembrete de hidratação.
        val reminderRequest = PeriodicWorkRequestBuilder<DailyReminderWorker>(
            repeatInterval = 24, // Repetir a cada 24 horas
            repeatIntervalTimeUnit = TimeUnit.HOURS
        )
        // Define o atraso inicial para que a primeira execução ocorra exatamente às 8:00 AM.
        .setInitialDelay(calculateDelayUntil8AM(), TimeUnit.MILLISECONDS)
        .build()

        // [CORREÇÃO ARQUITETURAL] Alterado de KEEP para UPDATE.
        // A política `KEEP` impedia que o agendamento fosse corrigido se o Worker já estivesse 
        // registrado no sistema (provavelmente agendado para o horário em que o app foi 
        // aberto pela primeira vez). O `UPDATE` força a atualização do agendamento com 
        // o novo cálculo de horário (8:00 AM) toda vez que o app for atualizado ou reiniciado.
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "daily_hydration_reminder",
            ExistingPeriodicWorkPolicy.UPDATE, 
            reminderRequest
        )
    }

    /**
     * Calcula o tempo em milissegundos desde agora até as 8:00 da manhã.
     * Garante que a notificação ocorra no período matinal.
     *
     * @return O atraso em milissegundos.
     */
    private fun calculateDelayUntil8AM(): Long {
        val now = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            // Se o horário de 8:00 de hoje já passou, agenda para as 8:00 de amanhã.
            if (before(now)) {
                add(Calendar.DATE, 1)
            }
        }
        return target.timeInMillis - now.timeInMillis
    }
}
