package com.jtmcompany.todoapp.fragment

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

import androidx.recyclerview.widget.LinearLayoutManager
import com.jtmcompany.todoapp.*
import com.jtmcompany.todoapp.R
import com.jtmcompany.todoapp.calendar_decorator.EventDecorator
import com.jtmcompany.todoapp.calendar_decorator.ToDayDecorator
import com.jtmcompany.todoapp.room.CalendarTodo

import com.prolificinteractive.materialcalendarview.*
import kotlinx.android.synthetic.main.fragment_calendar.*

class CalendarFragment : Fragment(), OnDateSelectedListener, View.OnClickListener,

    OnMonthChangedListener,
    CalendarAdapter.CheckClickListener {

    private val INSERT_REQUEST_CODE: Int = 100
    private val UPDATE_REQUEST_CODE: Int= 101
    private var curDate: String = ""

    private lateinit var viewModel: CalendarViewModel
    private var year:String="0"
    private var month:String="0"
    private var day:String="0"
    private  var content:String="0"
    private var adapter: CalendarAdapter? = null
    private var flag:Boolean=false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CalendarViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        calendar_v.addDecorators(ToDayDecorator())

        calendar_v.setOnDateChangedListener(this)
        calendar_v.setOnMonthChangedListener(this)
        todo_add_bt.setOnClickListener(this)


        calendar_rv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        //값이 저장된 날짜에 빨간점 표시
        viewModel.calendarTodoList.observe(viewLifecycleOwner,Observer<List<CalendarTodo>>
        {
            Log.d("tak","test")
            //select문 실행
            viewModel.setLiveDataList(year, month, day)
            //리싸이클러뷰 아이템 notify
            adapter?.update(viewModel.calendarTodoDetailList)
            for(saveData in it)
                calendar_v.addDecorators(EventDecorator(saveData.year,saveData.month,saveData.day))


        })
        viewModel.setLiveDataList(year, month, day)
        val list=viewModel.calendarTodoDetailList
        adapter= CalendarAdapter(list)
        adapter?.setClickListenr(this)
        calendar_rv.adapter=adapter




    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Log.d("tak", "호출")

        if (requestCode==INSERT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            content = data?.getStringExtra("content").toString()
            viewModel.insert(CalendarTodo(year, month, day, content))
        }
        else if(requestCode==UPDATE_REQUEST_CODE && resultCode==Activity.RESULT_OK){
            val newContent = data?.getStringExtra("content").toString()

            viewModel.update(CalendarTodo(year, month, day, content),newContent)
        }
    }

    //날짜를 클릭했을때 콜백
    override fun onDateSelected(
        widget: MaterialCalendarView,
        date: CalendarDay,
        selected: Boolean
    ) {
        flag=false
        curDate = date.toString()
        year = curDate?.substring(12, 16)
        month = curDate?.substring(17, 19)
        day = curDate?.substring(20, 22)

        //select문 실행
        viewModel.setLiveDataList(year, month, day)
        val list=viewModel.calendarTodoDetailList
        adapter?.update(list)




    }

    //일정추가 버튼
    override fun onClick(p0: View?) {
        if (curDate == "") Toast.makeText(context, "날짜를 선택해주세요!", Toast.LENGTH_SHORT).show()
        else {
            val intent = Intent(context, InputDialog::class.java)
            startActivityForResult(intent, INSERT_REQUEST_CODE)

        }
    }

    //다음달, 이전달로 넘어갔을때 콜백
    override fun onMonthChanged(widget: MaterialCalendarView?, date: CalendarDay?) {
        curDate = ""
    }

    //아이템을 채크했을때, flag를 true로해줌
    //DB를 update를하면 observe에 업데이트한 부분 행들이 파라미터로 다 넘겨지기때문에, 데이터가 뒤죽박죽됨을 방지
    override fun checkOnClick(calendarTodo: CalendarTodo) {
        viewModel.updateCheck(calendarTodo)
        flag=true
    }

    override fun onUpdate(cal: CalendarTodo) {
        val intent=Intent(activity,updateDialog::class.java)
        intent.putExtra("content",cal.content)
        startActivityForResult(intent,UPDATE_REQUEST_CODE)
        year=cal.year; month=cal.month; day=cal.day; content=cal.content


    }

    override fun onDelete(calendarTodo: CalendarTodo) {
        viewModel.delete(calendarTodo)
    }

}