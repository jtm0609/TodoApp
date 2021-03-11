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
import java.util.*

class AlaramReceiver : BroadcastReceiver() {
    val CHANNEL_ID="jtm"
    val CHANNEL_NAME="jtm"
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("tak","receive!")
        val db= Room.databaseBuilder(
            context!!,
            CalendarTodoDatabase::class.java,"db_name").allowMainThreadQueries().fallbackToDestructiveMigration().build()


        //부팅시 호출 -> 알람 재설정
        if(intent?.action.equals("android.intent.action.BOOT_COMPLETED")){
            Log.d("tak","부팅!!")

            val calendarTodoDao=db.getCalendarTodoDao()
            var alramList=calendarTodoDao.getAlarm()
            if(alramList.isNotEmpty()){
                for(calendarTodo in alramList)
                    setAlram(context,calendarTodo)

            }else if(alramList.isEmpty())
                Log.d("tak","비어있네?")
        }

        //일반 알람을 울렸을때 호출 및 알람 스위치 off
        else {
            var notiContent: String? = ""
            var notiId:Int
            if (intent?.getStringExtra("alarmContent") != null) {
                notiContent = intent.getStringExtra("alarmContent")
                notiId= intent.getIntExtra("alarmId",0)
                var calendarTodo=db.getCalendarTodoDao().getFromId(notiId)
                Log.d("tak",calendarTodo.content)
                Log.d("tak", calendarTodo.id.toString())
                Log.d("tak", calendarTodo.isAlarm.toString())
                calendarTodo.isAlarm=false
                db.getCalendarTodoDao().update(calendarTodo)
            }
            sendNoti(context,notiContent)


        }
    }




    fun sendNoti(context: Context?, notiContent:String?){
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
        }

        else
            builder = NotificationCompat.Builder(context)


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






    fun setAlram(context: Context?, newCalendarTodo:CalendarTodo){
        var alarmManager=context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var receiverIntent= Intent(context, AlaramReceiver::class.java)
        receiverIntent.putExtra("alarmContent",newCalendarTodo.content)

        //알람 식별자로 db의 id를 넣음
        Log.d("tak","alarmId: "+newCalendarTodo.id)

        var pendingIntent = PendingIntent.getBroadcast(context,newCalendarTodo.id,receiverIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)
        var calendar= Calendar.getInstance()
        //calendar.time=dateTime
        calendar.set(Calendar.YEAR,newCalendarTodo.year.toInt())
        calendar.set(Calendar.MONTH,newCalendarTodo.month.toInt())
        calendar.set(Calendar.DATE,newCalendarTodo.day.toInt())
        calendar.set(Calendar.HOUR_OF_DAY,newCalendarTodo.hour)
        calendar.set(Calendar.MINUTE,newCalendarTodo.minute)
        calendar.set(Calendar.SECOND,0)
        //Log.d("tak","year: "+year)
        //Log.d("tak","month: "+month)
        //Log.d("tak","day: "+day)
        Log.d("tak", calendar.time.toString())

        //알람등록
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}