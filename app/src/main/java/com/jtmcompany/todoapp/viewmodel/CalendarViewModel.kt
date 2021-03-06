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
    lateinit var selectedlObject:CalendarTodo


    fun insert(calendarTodo: CalendarTodo){
        calendarTodoDao.insert(calendarTodo)

    }

    fun update(calendarTodo: CalendarTodo){
        calendarTodoDao.update(calendarTodo)
    }

    fun select(year:String, month:String, day:String){
        selectedlList=calendarTodoDao.getSelectedList(year,month,day)

    }

    fun selectObject(year:String, month:String, day:String, hour: Int, minute: Int){
        selectedlObject=calendarTodoDao.getSelectedObject(year,month,day,hour,minute)
    }



    fun delete(cal: CalendarTodo){
        calendarTodoDao.delete(cal)
    }


    fun parsingDate(date:String): List<String> {
        var curDate=date
        val idx=curDate.indexOf("{")
        curDate=curDate?.substring(idx+1,curDate.length-1)
        val curDates=curDate.split("-")
        return curDates
    }





}

