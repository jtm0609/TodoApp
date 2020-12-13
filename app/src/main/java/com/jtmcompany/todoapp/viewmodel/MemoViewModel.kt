package com.jtmcompany.todoapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.jtmcompany.todoapp.database.MemoDatabase
import com.jtmcompany.todoapp.model.Memo

class MemoViewModel(application: Application) : AndroidViewModel(application) {
    val db= Room.databaseBuilder(application,MemoDatabase::class.java,"memoDB").allowMainThreadQueries().fallbackToDestructiveMigration().build()
    val memoDao=db.getMemoDao()
    val memoList=memoDao.getAll()

    fun insert(memo: Memo){
        memoDao.insert(memo)
    }
    fun update(newContent:String, myId:Int){

        memoDao.update(newContent,myId)
    }
    fun delete(memo:Memo){
        memoDao.delete(memo)
    }


}