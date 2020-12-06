package com.jtmcompany.todoapp

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.jtmcompany.todoapp.room.CalendarTodo
import com.jtmcompany.todoapp.room.CalendarTodoDatabase

class CalendarViewModel(application: Application) : AndroidViewModel(application) {


    val db= Room.databaseBuilder(application,CalendarTodoDatabase::class.java,"db_name").allowMainThreadQueries().fallbackToDestructiveMigration().build()
    val calendarTodoDao=db.getCalendarTodoDao()
    var calendarTodoList=calendarTodoDao.getAll()
    lateinit var calendarTodoDetailList:List<CalendarTodo>

    //라이브 데이터를 db에 연결

    fun insert(calendarTodo:CalendarTodo){
        calendarTodoDao.insert(calendarTodo)
    }

    fun updateCheck(calendarTodo: CalendarTodo){
        calendarTodoDao.updateCheck(calendarTodo)
    }


    fun update(cal: CalendarTodo, newContent:String){

        calendarTodoDao.update(cal.year,cal.month,cal.day,cal.content,newContent)
    }

    fun setLiveDataList(year:String, month:String, day:String){
        calendarTodoDetailList=calendarTodoDao.getContent(year,month,day)

    }

    fun delete(cal: CalendarTodo){
        calendarTodoDao.delete(cal.year,cal.month,cal.day,cal.content)
    }




    }

