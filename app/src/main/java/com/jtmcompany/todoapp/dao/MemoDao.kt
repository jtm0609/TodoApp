package com.jtmcompany.todoapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jtmcompany.todoapp.model.Memo
import java.io.Serializable

@Dao
interface MemoDao{
    @Insert
    fun insert(memo: Memo)

    @Query("UPDATE Memo SET content=:newContent WHERE id=:myId")
    fun update(newContent:String, myId:Int)

    @Delete
    fun delete(memo: Memo)

    @Query("select * from Memo")
    fun getAll(): LiveData<List<Memo>>
}