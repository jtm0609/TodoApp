package com.jtmcompany.todoapp.Receiver

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.jtmcompany.todoapp.MainActivity
import com.jtmcompany.todoapp.R

class AlaramReceiver : BroadcastReceiver() {
    val CHANNEL_ID="jtm"
    val CHANNEL_NAME="jtm"
    override fun onReceive(context: Context?, p1: Intent?) {
        Log.d("tak","receive!")
        var am=context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        var builder:NotificationCompat.Builder?=null
        var notimanager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            notimanager.createNotificationChannel(NotificationChannel( CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT))
            builder=NotificationCompat.Builder(context,CHANNEL_ID)

        }else{
            builder=NotificationCompat.Builder(context)
        }

        var intent=Intent(context,MainActivity::class.java)
        var pendingIntent=PendingIntent.getActivity(context,101,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentTitle("알람")
        builder.setSmallIcon(R.drawable.ic_launcher_background)
        builder.setAutoCancel(true)
        builder.setContentIntent(pendingIntent)
        var notification=builder.build()
        notimanager.notify(1,notification)

    }
}