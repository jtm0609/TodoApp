package com.jtmcompany.todoapp

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.jtmcompany.todoapp.room.CalendarTodo
import com.jtmcompany.todoapp.room.CalendarTodoDatabase

class CalendarViewModel(application: Application) : AndroidViewModel(application) {

    val db= Room.databaseBuilder(application,CalendarTodoDatabase::class.java,"db_name").allowMainThreadQueries().build()
    val calendarTodoDao=db.getCalendarTodoDao()
    val calendarTodoList=calendarTodoDao.getAA()
    //라이브 데이터를 db에 연결

    fun insert(calendarTodo:CalendarTodo){
        calendarTodoDao.insert(calendarTodo)
    }

    fun setLiveDataList(year:String, month:String, day:String){
        //calendarTodoList=calendarTodoDao.getAll()
    }




    }

