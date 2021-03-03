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
    lateinit var selectedlList:List<CalendarTodo>


    fun insert(calendarTodo: CalendarTodo){
        calendarTodoDao.insert(calendarTodo)
    }

    fun update(calendarTodo: CalendarTodo){
        calendarTodoDao.update(calendarTodo)
    }


    //fun update(id:Int, newContent:String){

     //   calendarTodoDao.updateQuery(id,newContent)
    //}

    fun select(year:String, month:String, day:String){
        selectedlList=calendarTodoDao.getContent(year,month,day)

    }

    fun delete(cal: CalendarTodo){
        calendarTodoDao.delete(cal)
    }




    }

