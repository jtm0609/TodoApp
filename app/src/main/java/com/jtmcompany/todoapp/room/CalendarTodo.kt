package com.jtmcompany.todoapp.room

import android.widget.CheckBox
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CalendarTodo (

        //@PrimaryKey(autoGenerate = true) var id:Int,
    var year:String,
    var month:String,
    var day:String,
    var content:String,
    var check:Boolean=false

){
    @PrimaryKey(autoGenerate=true)
    var id=0
}