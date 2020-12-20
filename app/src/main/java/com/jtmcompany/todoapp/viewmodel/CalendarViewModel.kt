package com.jtmcompany.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.jtmcompany.todoapp.model.CalendarTodo
import com.jtmcompany.todoapp.database.CalendarTodoDatabase

class CalendarViewModel(application: Application) : AndroidViewModel(application) {


    val db= Room.databaseBuilder(application,
        CalendarTodoDatabase::class.java,"db_name").allowMainThreadQueries().fallbackToDestructiveMigration().build()
    val calendarTodoDao=db.getCalendarTodoDao()
    var calendarTodoList=calendarTodoDao.getAll()
    lateinit var calendarTodoDetailList:List<CalendarTodo>

    //라이브 데이터를 db에 연결

    fun insert(calendarTodo: CalendarTodo){
        calendarTodoDao.insert(calendarTodo)
    }

    fun updateCheck(calendarTodo: CalendarTodo){
        calendarTodoDao.update(calendarTodo)
    }


    fun update(id:Int, newContent:String){

        calendarTodoDao.updateQuery(id,newContent)
    }

    fun setLiveDataList(year:String, month:String, day:String){
        calendarTodoDetailList=calendarTodoDao.getContent(year,month,day)

    }

    fun delete(cal: CalendarTodo){
        calendarTodoDao.delete(cal)
    }




    }

