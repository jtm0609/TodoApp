package com.jtmcompany.todoapp.model

import android.widget.CheckBox
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class CalendarTodo  (

    //@PrimaryKey(autoGenerate = true) var id:Int,
    var year:String,
    var month:String,
    var day:String,
    var content:String,
    var check:Boolean=false,
    var isAlarm:Boolean=false,
    var hour:Int,
    var minute:Int

): Serializable
{
    @PrimaryKey(autoGenerate=true)
    var id=0

}