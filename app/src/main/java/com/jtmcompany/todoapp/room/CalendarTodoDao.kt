package com.jtmcompany.todoapp.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jtmcompany.todoapp.room.CalendarTodo

@Dao
interface CalendarTodoDao {
    @Insert
    fun insert(calendarTodo: CalendarTodo)

    @Update
    fun update(calendarTodo: CalendarTodo)

    @Delete
    fun delete(calendarTodo: CalendarTodo)

    @Query("SELECT * FROM CalendarTodo WHERE year = mYear AND month=mMonth AND day=mDay")
    fun select(mYear:String,mMonth:String,mDay:String):LiveData<CalendarTodo>

}