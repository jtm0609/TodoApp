package com.jtmcompany.todoapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jtmcompany.todoapp.model.MemoTodo

@Dao
interface MemoDao{
    @Insert
    fun insert(memoTodo: MemoTodo)


    @Update
    fun update(memoTodo:MemoTodo)

    @Delete
    fun delete(memoTodo: MemoTodo)

    @Query("select * from MemoTodo")
    fun getAll(): LiveData<List<MemoTodo>>
}