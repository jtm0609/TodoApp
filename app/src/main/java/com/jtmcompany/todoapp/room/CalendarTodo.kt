package com.jtmcompany.todoapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CalendarTodo (
    var year:String,
    var month:String,
    var day:String,
    var content:String

){
    @PrimaryKey(autoGenerate = true) var id=0
}