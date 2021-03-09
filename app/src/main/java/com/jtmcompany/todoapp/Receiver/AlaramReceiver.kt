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
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.jtmcompany.todoapp.MainActivity
import com.jtmcompany.todoapp.R
import com.jtmcompany.todoapp.database.CalendarTodoDatabase
import com.jtmcompany.todoapp.model.CalendarTodo
import com.jtmcompany.todoapp.viewmodel.CalendarViewModel

class AlaramReceiver : BroadcastReceiver() {
    val CHANNEL_ID="jtm"
    val CHANNEL_NAME="jtm"
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("tak","receive!")
        //var am=context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if(intent?.action.equals("android.intent.action.BOOT_COMPLETED")){
            Log.d("tak","부팅!!")
            val db= Room.databaseBuilder(
                context!!,
                CalendarTodoDatabase::class.java,"db_name").allowMainThreadQueries().fallbackToDestructiveMigration().build()
            val calendarTodoDao=db.getCalendarTodoDao()
            var alramList=calendarTodoDao.getAlarm()
            if(alramList.isNotEmpty()){
                for(alram in alramList)
                Log.d("tak",alram.content)
            }else if(alramList.isEmpty())
                Log.d("tak","비어있네?")


        }
        else {
            var notiContent: String? = ""
            if (intent?.getStringExtra("alarmContent") != null) {
                notiContent = intent.getStringExtra("alarmContent")
            }

            var builder: NotificationCompat.Builder? = null
            var notimanager =
                context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notimanager.createNotificationChannel(
                    NotificationChannel(
                        CHANNEL_ID,
                        CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                )
                builder = NotificationCompat.Builder(context!!, CHANNEL_ID)

            } else {

                builder = NotificationCompat.Builder(context)
            }

            var intent = Intent(context, MainActivity::class.java)
            var pendingIntent =
                PendingIntent.getActivity(context, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT)


            builder.setContentTitle("알람")
            builder.setContentText(notiContent)
            builder.setSmallIcon(R.drawable.ic_launcher_background)
            builder.setAutoCancel(true)
            builder.setContentIntent(pendingIntent)
            var notification = builder.build()
            notimanager.notify(1, notification)
        }
    }
}