package com.jtmcompany.todoapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jtmcompany.todoapp.dao.MemoDao
import com.jtmcompany.todoapp.model.Memo

@Database(entities = [Memo::class],version = 4)
abstract class MemoDatabase: RoomDatabase() {
    abstract fun getMemoDao() :MemoDao

}