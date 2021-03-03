package com.jtmcompany.todoapp.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import com.jtmcompany.todoapp.*
import com.jtmcompany.todoapp.R
import com.jtmcompany.todoapp.adapter.CalendarAdapter
import com.jtmcompany.todoapp.calendar_decorator.EventDecorator
import com.jtmcompany.todoapp.calendar_decorator.ToDayDecorator
import com.jtmcompany.todoapp.model.CalendarTodo
import com.jtmcompany.todoapp.viewmodel.CalendarViewModel

import com.prolificinteractive.materialcalendarview.*
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.util.*

class CalendarFragment : Fragment(), OnDateSelectedListener, View.OnClickListener,

    OnMonthChangedListener,
    CalendarAdapter.CheckClickListener {

    private val INSERT_REQUEST_CODE: Int = 100
    private val UPDATE_REQUEST_CODE: Int= 101
    private var curDate: String = ""

    private lateinit var viewModel: CalendarViewModel
    private var year:String=Calendar.getInstance().get(Calendar.YEAR).toString()
    private var month:String=Calendar.getInstance().get(Calendar.MONTH).toString()
    private var day:String=Calendar.getInstance().get(Calendar.DATE).toString()
    private  var content:String="0"
    private var adapter: CalendarAdapter? = null
    private var curId:Int=0



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
        viewSetting()

        /** 캘린더 DB의 data가 변경되면 호출된다. **/
        viewModel.calendarTodoList.observe(viewLifecycleOwner,Observer<List<CalendarTodo>>
        {
            Log.d("tak","calendarObserve")

            //변경된 날짜 조회
            viewModel.select(year, month, day)

            //값이 저장된 날짜에 빨간점 표시
            adapter?.update(viewModel.selectedlList)
            for(saveData in it)
                calendar_v.addDecorators(EventDecorator(saveData.year,saveData.month,saveData.day))
        })

        //오늘 날짜 조회
        viewModel.select(year, month, day)
        val list=viewModel.selectedlList

        adapter= CalendarAdapter(list)
        adapter?.setClickListenr(this)
        calendar_rv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        calendar_rv.adapter=adapter
    }



    fun viewSetting(){
        calendar_v.addDecorators(ToDayDecorator())
        calendar_v.setSelectedDate(Calendar.getInstance())

        calendar_v.setOnDateChangedListener(this)
        calendar_v.setOnMonthChangedListener(this)
        todo_add_bt.setOnClickListener(this)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //다이얼로그에서  추가하기 버튼을 눌렀을 때
        if (requestCode==INSERT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            content = data?.getStringExtra("add_OK").toString()
            viewModel.insert(
                CalendarTodo(year, month, day, content)
            )
        }

        //다이얼로그에서  수정하기 버튼을 눌렀을 때
        else if(requestCode==UPDATE_REQUEST_CODE && resultCode==Activity.RESULT_OK){
            val newCalendarTodo = data?.getSerializableExtra("updateCalendar_OK") as CalendarTodo

            viewModel.update(newCalendarTodo)
        }
    }



    //날짜를 클릭했을때 콜백
    override fun onDateSelected(
        widget: MaterialCalendarView,
        date: CalendarDay,
        selected: Boolean
    ) {
        //문자열 파싱
        //curDate = date.toString()
        //val idx=curDate.indexOf("{")
        //curDate=curDate?.substring(idx+1,curDate.length-1)
        //val date=curDate.split("-")
        //year=date[0];month=date[1];day=date[2]

        var curDate=parsingDate(date.toString())
        year=curDate[0]
        month=curDate[1]
        day=curDate[2]

        Log.d("tak",year)
        Log.d("tak",month)
        Log.d("tak",day)
        //선택한 날짜 조회
        viewModel.select(year, month, day)
        val list=viewModel.selectedlList

        if(list.isEmpty()) { Log.d("tak", "no") }
        //조회회한 리스트를 여줌
        adapter?.update(list)
    }

    fun parsingDate(date:String): List<String> {
        var curDate=date
        val idx=curDate.indexOf("{")
        curDate=curDate?.substring(idx+1,curDate.length-1)
        val curDates=curDate.split("-")
        return curDates
    }

    //일정추가 버튼
    override fun onClick(p0: View?) {
            val intent = Intent(context, InputDialogActivity::class.java)
            startActivityForResult(intent, INSERT_REQUEST_CODE)
    }


    //다음달, 이전달로 넘어갔을때 콜백
    override fun onMonthChanged(widget: MaterialCalendarView?, date: CalendarDay?) {
        curDate = ""
    }


    //DB에 체크박스를 업데이트한다.
    override fun checkOnClick(calendarTodo: CalendarTodo) {
        viewModel.update(calendarTodo)
    }


    //일정 수정
    override fun onUpdate(cal: CalendarTodo) {
        val intent=Intent(activity,updateDialogActivity::class.java)
        //intent.putExtra("content",cal.content)
        intent.putExtra("updateCalendar",cal)
        startActivityForResult(intent,UPDATE_REQUEST_CODE)
        curId=cal.id
    }


    override fun onDelete(calendarTodo: CalendarTodo) {
        var dialogBuilder= AlertDialog.Builder(context,android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
        dialogBuilder.setMessage("메모를 삭제 하시겠습니까?")
            .setCancelable(true)
            .setPositiveButton("취소",object: DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {}
            })
            .setNegativeButton("삭제",object:DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    viewModel.delete(calendarTodo)
                }
            })
        var dialog=dialogBuilder.create()
        dialog.show()


    }

}