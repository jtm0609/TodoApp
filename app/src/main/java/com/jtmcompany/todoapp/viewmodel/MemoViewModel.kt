package com.jtmcompany.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.jtmcompany.todoapp.database.MemoDatabase
import com.jtmcompany.todoapp.model.MemoTodo

class MemoViewModel(application: Application) : AndroidViewModel(application) {
    val db= Room.databaseBuilder(application,MemoDatabase::class.java,"memoDB").allowMainThreadQueries().fallbackToDestructiveMigration().build()
    val memoDao=db.getMemoDao()
    val memoList=memoDao.getAll()

    fun insert(memoTodo: MemoTodo){
        memoDao.insert(memoTodo)
    }


    fun update(memoTodo:MemoTodo){
        memoDao.update(memoTodo)
    }
    fun delete(memoTodo:MemoTodo){
        memoDao.delete(memoTodo)
    }


}