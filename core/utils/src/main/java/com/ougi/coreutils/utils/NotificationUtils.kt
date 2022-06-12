package com.ougi.coreutils.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ougi.coreutils.R

class NotificationUtils(private val context: Context) {

    private val notificationManager = NotificationManagerCompat.from(context)

    fun notify(title: String? = null, content: String? = null) {

        createNotificationChannel()
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(androidx.core.R.drawable.notification_action_background)
            .setContentTitle(title ?: context.getString(R.string.app_name))
            .setContentText(content ?: context.getString(R.string.something_new))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        notificationManager.notify(0, builder.build())
    }

    private fun createNotificationChannel() {


        if (notificationManager.notificationChannels.none { it.id == CHANNEL_ID }
            && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_ID
            val descriptionText = CHANNEL_ID
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "SecretChatChannel"
    }
}