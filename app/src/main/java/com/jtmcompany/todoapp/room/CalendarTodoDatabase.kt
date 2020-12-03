package com.jtmcompany.todoapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jtmcompany.todoapp.room.CalendarTodo
import com.jtmcompany.todoapp.room.CalendarTodoDao

@Database(entities = [ CalendarTodo::class],version=3,exportSchema = false)
 abstract class CalendarTodoDatabase() : RoomDatabase() {
    abstract fun getCalendarTodoDao():CalendarTodoDao

}