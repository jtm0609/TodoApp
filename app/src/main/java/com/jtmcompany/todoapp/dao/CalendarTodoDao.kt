package com.jtmcompany.todoapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jtmcompany.todoapp.model.CalendarTodo

@Dao
interface CalendarTodoDao {
    @Insert
    fun insert(calendarTodo: CalendarTodo)

    @Update
    fun updateCheck(calendarTodo: CalendarTodo)

    @Query("UPDATE CalendarTodo SET content=:newContent WHERE year=:mYear AND month=:mMonth AND day=:mDay AND content=:mContent")
    fun update(mYear: String, mMonth:String, mDay:String, mContent:String, newContent:String)


    @Query("DELETE FROM CalendarTodo WHERE year=:mYear AND month=:mMonth AND day=:mDay And content=:mContent")
    fun delete(mYear: String, mMonth:String, mDay:String, mContent:String)

   // @Query("SELECT * FROM CalendarTodo WHERE year = mYear AND month=mMonth AND day=mDay")
   // fun select(mYear:String,mMonth:String,mDay:String):LiveData<CalendarTodo>


    //@Query("SELECT content FROM CalendarTodo WHERE year=:mYear AND month=:mMonth AND day=:mDay" )
    @Query("SELECT * FROM CalendarTodo WHERE year=:mYear AND month=:mMonth AND day=:mDay" )
    fun getContent(mYear: String, mMonth:String, mDay:String): List<CalendarTodo>

    @Query("SELECT * FROM CalendarTodo")
    fun getAll():LiveData<List<CalendarTodo>>



}