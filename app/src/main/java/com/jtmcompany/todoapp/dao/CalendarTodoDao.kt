package com.jtmcompany.todoapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jtmcompany.todoapp.model.CalendarTodo

@Dao
interface CalendarTodoDao {
    @Insert
    fun insert(calendarTodo: CalendarTodo)

    @Update
    fun update(calendarTodo: CalendarTodo)


    @Delete
    fun delete(calendarTodo: CalendarTodo)


    @Query("SELECT * FROM CalendarTodo WHERE year=:mYear AND month=:mMonth AND day=:mDay" )
    fun getSelectedList(mYear: String, mMonth:String, mDay:String): List<CalendarTodo>

    @Query("SELECT * FROM CalendarTodo WHERE year=:mYear AND month=:mMonth AND day=:mDay AND hour=:mHour AND minute=:mMinute" )
    fun getSelectedObject(mYear: String, mMonth:String, mDay:String, mHour: Int, mMinute:Int): CalendarTodo

    @Query("SELECT * FROM CalendarTodo")
    fun getAll():LiveData<List<CalendarTodo>>

}