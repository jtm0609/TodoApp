package com.jtmcompany.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Memo (
    var content: String

){
    @PrimaryKey(autoGenerate=true)
    var id=0


}