package com.jtmcompany.todoapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore

import androidx.recyclerview.widget.LinearLayoutManager
import com.jtmcompany.todoapp.calendar_decorator.EventDecorator
import com.jtmcompany.todoapp.calendar_decorator.OneDayDecorator
import com.jtmcompany.todoapp.room.CalendarTodo

import com.prolificinteractive.materialcalendarview.*
import kotlinx.android.synthetic.main.fragment_calendar.*

import kotlin.collections.ArrayList

class CalendarFragment : Fragment(), OnDateSelectedListener, View.OnClickListener,
    OnMonthChangedListener {
    private val REQUEST_CODE: Int=100
    private val calendartodoList= ArrayList<String>()
    private var curDate:String=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView= inflater.inflate(R.layout.fragment_calendar, container, false)


        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        calendar_v.addDecorators(OneDayDecorator())
        calendar_v.addDecorators(EventDecorator())
        calendar_v.setOnDateChangedListener(this)
        calendar_v.setOnMonthChangedListener(this)
        todo_add_bt.setOnClickListener(this)



        calendar_rv.adapter=CalendarAdapter(calendartodoList)
        calendar_rv.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("tak","호출")
        if(resultCode== Activity.RESULT_OK){
            Log.d("tak",curDate)
            Log.d("tak",data?.getStringExtra("content"))
        }else{
            Log.d("tak","취소")
        }
    }

    //날짜를 클릭했을때 콜백
    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
            curDate=date.toString()

    }

    //일정추가 버튼
    override fun onClick(p0: View?) {
        if(curDate=="") Toast.makeText(context,"날짜를 선택해주세요!",Toast.LENGTH_SHORT).show()
        else{
            val intent=Intent(context,InputDialog::class.java)
            startActivityForResult(intent,REQUEST_CODE)
            Log.d("tak","go")
        }
    }

    //다음달, 이전달로 넘어갔을때 콜백
    override fun onMonthChanged(widget: MaterialCalendarView?, date: CalendarDay?) {
        curDate=""
    }

}