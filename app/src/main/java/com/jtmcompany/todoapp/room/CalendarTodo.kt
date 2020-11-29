package com.jtmcompany.todoapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CalendarTodo (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var year:String,
    var month:String,
    var day:String

)