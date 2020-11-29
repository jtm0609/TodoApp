package com.jtmcompany.todoapp.room

import androidx.room.Database
import com.jtmcompany.todoapp.room.CalendarTodo
import com.jtmcompany.todoapp.room.CalendarTodoDao

@Database(entities = [ CalendarTodo::class],version=1)
public abstract class CalendarTodoDatabase() {
    abstract fun getCalendarTodoDao():CalendarTodoDao

}