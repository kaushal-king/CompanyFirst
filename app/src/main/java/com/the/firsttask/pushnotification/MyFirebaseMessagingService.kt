package com.the.firsttask.pushnotification


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.the.firsttask.DrawerActivity
import com.the.firsttask.R
import com.the.firsttask.utils.ConstantHelper


class MyFirebaseMessagingService : FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived ( remoteMessage )
        Log.e("notification", "notification 1 ", )
        if(remoteMessage.notification != null){
            generateNotification(remoteMessage.notification!!.title.toString(),remoteMessage.notification!!.body.toString())
            Log.e("notification", "notification received ", )
        }
    }


    fun generateNotification(title:String,message : String){

        val intent = Intent(this, DrawerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)

        var builder: NotificationCompat.Builder=NotificationCompat.Builder(applicationContext,ConstantHelper.PUSHNOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.smile)
            .setAutoCancel(true)
            .setContentTitle(title)
            .setContentText(message)
            .setVibrate(longArrayOf(0, 1000, 500, 1000))
            .setContentIntent(pendingIntent)
            .setDefaults(Notification.DEFAULT_ALL)
            .setStyle( NotificationCompat.BigTextStyle().bigText(message))



        var notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var channel = NotificationChannel(
                ConstantHelper.PUSHNOTIFICATION_CHANNEL_ID, ConstantHelper.PUSHNOTIFICATION_CHANNEL_Name,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel!!.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            channel!!.enableVibration(true)

            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0,builder.build())
    }



    override fun onNewToken(s: String) {
        super.onNewToken(s)
//        Log.e("TOKEN new", s)
    }

}