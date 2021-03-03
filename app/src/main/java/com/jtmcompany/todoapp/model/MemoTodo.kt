package com.jtmcompany.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class MemoTodo (
    var content: String

):Serializable{
    @PrimaryKey(autoGenerate=true)
    var id=0


}