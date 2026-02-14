package org.example.project

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory // Import necessário para carregar imagens PNG/JPG
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import org.example.project.services.Notifier

/**
 * Serviço que cria a notificação usando uma arquitetura de ícones híbrida:
 *
 * - Small Icon: Um `Vector Drawable` monocromático para a barra de status (`ic_copo_icon`).
 * - Large Icon: O `PNG` de alta resolução do logo (`alimentandofasesicon`) para o corpo da notificação.
 */
class NotificationService(private val context: Context) : Notifier {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        const val CHANNEL_ID = "default_channel_id"
        const val CHANNEL_NAME = "Default"
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Canal de notificações padrão"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun showNotification(title: String, message: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Decisão Arquitetural: O 'Small Icon' permanece um vetor monocromático para
        // compatibilidade com a barra de status do sistema, garantindo visibilidade.
        val smallIconResource = R.drawable.ic_copo_icon

        // Decisão Arquitetural: O 'Large Icon' agora carrega o recurso PNG de alta resolução.
        // O BitmapFactory decodifica o arquivo de imagem em um objeto Bitmap, que é o
        // formato exigido pela API de notificação. O recurso é carregado de 'res/drawable-xxhdpi'.
        val largeIconBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.alimentandofasesicon)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(smallIconResource)
            .setContentTitle(title)
            .setContentText(message)
            .setLargeIcon(largeIconBitmap) // Utiliza o logo em formato PNG
            .setColor(ContextCompat.getColor(context, R.color.brand_primary))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}