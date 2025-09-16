package com.fjvid.taskbox.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.ui.res.stringResource
import androidx.core.app.NotificationCompat
import com.fjvid.taskbox.MainActivity
import com.fjvid.taskbox.R

/**
 * Creación del canal
 */
fun createNotificationChannel(context: Context) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager // creamos el manager

        val channelId = "taskbox_channel"
        val channelName = "TaskBox Notificaciones"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, channelName, importance).apply { // creamos el canal y le añadimos la informacion que queramos
            description = context.getString(R.string.description_channel_notification)
            enableVibration(true)
            setShowBadge(true)
        }

    notificationManager.createNotificationChannel(channel) // con el manager creamos el canal y le pasamos el nuestro como parametro

}

/**
 *  Lanza la notificacion cuando se accede a la lista de tareas y estas contiene tareas urgentes
 */
fun createNotification(context: Context) {
    val channelId = "taskbox_channel"


    val intent = Intent(context, MainActivity::class.java).apply { // Intent para abrir MainActivity cuando pulsamos en la notificacion
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        putExtra("origin", "notification")
    }

    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(context, channelId) // creamos un NofificationCompact con el contexto y el id del canal y cambiamos su informacion con los set
        .setSmallIcon(R.drawable.reloj)
        .setContentTitle(context.getString(R.string.tasks_notification))
        .setContentText(context.getString(R.string.body_tasks_notification))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(1, builder.build()) // lanzamos la notificación, dandole un id de la notificacion y nuestro NotificationCompact con el metodo build()
}
