package com.jtmcompany.todoapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jtmcompany.todoapp.dao.CalendarTodoDao
import com.jtmcompany.todoapp.model.CalendarTodo

@Database(entities = [ CalendarTodo::class],version=3,exportSchema = false)
 abstract class CalendarTodoDatabase() : RoomDatabase() {
    abstract fun getCalendarTodoDao(): CalendarTodoDao

}