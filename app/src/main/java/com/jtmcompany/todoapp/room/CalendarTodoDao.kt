package com.jtmcompany.todoapp.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface CalendarTodoDao {
    @Insert
    fun insert(calendarTodo: CalendarTodo)

    @Update
    fun update(calendarTodo: CalendarTodo)

    @Delete
    fun delete(calendarTodo: CalendarTodo)

   // @Query("SELECT * FROM CalendarTodo WHERE year = mYear AND month=mMonth AND day=mDay")
   // fun select(mYear:String,mMonth:String,mDay:String):LiveData<CalendarTodo>

    //@Query("SELECT content FROM CalendarTodo WHERE year=:mYear AND month=:mMonth AND day=:mDay" )
    @Query("SELECT * FROM CalendarTodo WHERE year=:mYear AND month=:mMonth AND day=:mDay" )
    fun getContent(mYear: String, mMonth:String, mDay:String): LiveData<List<CalendarTodo>>

    @Query("SELECT * FROM CalendarTodo")
    fun getAll():LiveData<List<CalendarTodo>>


}