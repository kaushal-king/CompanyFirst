package com.the.firsttask.ui.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.the.firsttask.R
import com.the.firsttask.utils.ConstantHelper


class AlarmReceiver : BroadcastReceiver() {

    lateinit var  notification:Notification
    var notificationManager: NotificationManager? = null
    var channel: NotificationChannel? = null
    override fun onReceive(context: Context, intent: Intent) {

        val description= intent.extras?.getString(ConstantHelper.ALARM_DESCRIPTION)
        Log.e("TAG", "onReceive: "+description)
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val soundUri = Uri.parse("android.resource://" + context.packageName + "/" + R.raw.notification)
        notification =  NotificationCompat.Builder(context, ConstantHelper.NOTIFICATION_CHANNEL_ID )
                                .setContentTitle("Alarm Reminder")
                                .setContentText(description+"" )
                                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setOngoing(false)
                                .setWhen(System.currentTimeMillis())
                                .setVibrate(longArrayOf(0, 1000, 500, 1000))
                                .setSound(soundUri)
                                .setChannelId( ConstantHelper.NOTIFICATION_CHANNEL_ID )
                                .build()


        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            channel= NotificationChannel(ConstantHelper.NOTIFICATION_CHANNEL_ID,"Alaram Name",
                NotificationManager.IMPORTANCE_HIGH )
            channel!!.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            channel!!.enableVibration(true)
            channel!!.description=description
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            channel!!.setSound(soundUri,attributes)
            assert(notificationManager != null)
            notificationManager?.createNotificationChannel(channel!!)
        }
        assert (notificationManager != null)
        notificationManager!!.notify(0, notification)

//                                .setContentIntent(pendingIntent)

    }
}