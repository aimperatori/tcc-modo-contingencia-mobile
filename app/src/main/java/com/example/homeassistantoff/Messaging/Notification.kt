package com.example.homeassistantoff.Messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PRIVATE
import com.example.homeassistantoff.MainActivity_old
import com.example.homeassistantoff.R


class Notification() {

    companion object {

        fun instanceOff(context : Context) {

            val title = ""
            val body = ""


            val intent = Intent(context, MainActivity_old::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(
                context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT
            )


            // !!! BOTAO DE AÇÃO !!!
            /*
        val snoozeIntent = Intent(this, MyBroadcastReceiver::class.java).apply {
            action = ACTION_SNOOZE
            putExtra(EXTRA_NOTIFICATION_ID, 0)
        }*/

            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:54984078154")
//        startActivity(callIntent)

            //val callPendingIntent: PendingIntent =
//            PendingIntent.getService(this, 0, callIntent, 0)

            //val channelId = getString(R.string.default_notification_channel_id)
            val channelId = "1"
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.logo)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
//                .setContentIntent(pendingIntent)
                .setVisibility(VISIBILITY_PRIVATE)
                .setOngoing(true)
                .setAutoCancel(false)
            //.addAction(R.drawable.ic_stat_ic_notification, "Ligar para a polícia",
            //  callPendingIntent)

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    "Important Notification",
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager.createNotificationChannel(channel)
            }

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
        }



        fun sendNotification(context : Context, title: String, body: String) {
            val intent = Intent(context, MainActivity_old::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(
                context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT
            )


            // !!! BOTAO DE AÇÃO !!!
            /*
        val snoozeIntent = Intent(this, MyBroadcastReceiver::class.java).apply {
            action = ACTION_SNOOZE
            putExtra(EXTRA_NOTIFICATION_ID, 0)
        }*/

            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:54984078154")
//        startActivity(callIntent)

            //val callPendingIntent: PendingIntent =
//            PendingIntent.getService(this, 0, callIntent, 0)

            //val channelId = getString(R.string.default_notification_channel_id)
            val channelId = "1"
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.logo)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setContentIntent(pendingIntent)
            //.addAction(R.drawable.ic_stat_ic_notification, "Ligar para a polícia",
            //  callPendingIntent)

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    "Important Notification",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            }

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
        }
    }
}