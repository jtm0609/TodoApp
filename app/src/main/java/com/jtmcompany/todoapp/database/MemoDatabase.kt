package com.jtmcompany.todoapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jtmcompany.todoapp.dao.MemoDao
import com.jtmcompany.todoapp.model.MemoTodo

@Database(entities = [MemoTodo::class],version = 5)
abstract class MemoDatabase: RoomDatabase() {
    abstract fun getMemoDao() :MemoDao

}