package com.jtmcompany.todoapp

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.jtmcompany.todoapp.room.CalendarTodo

class CalendarViewModel : AndroidViewModel() {
    lateinit var calendarTodoList : LiveData<List<CalendarTodo>>
    
}