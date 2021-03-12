package com.jtmcompany.todoapp.fragment

import android.app.Activity
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper

import androidx.recyclerview.widget.LinearLayoutManager
import com.jtmcompany.todoapp.Receiver.AlaramReceiver
import com.jtmcompany.todoapp.*
import com.jtmcompany.todoapp.R
import com.jtmcompany.todoapp.adapter.CalendarAdapter
import com.jtmcompany.todoapp.calendar_decorator.EventDecorator
import com.jtmcompany.todoapp.calendar_decorator.ToDayDecorator
import com.jtmcompany.todoapp.databinding.FragmentCalendarBinding
import com.jtmcompany.todoapp.itemtouch_helper.ItemTouchHelperCallback
import com.jtmcompany.todoapp.model.CalendarTodo
import com.jtmcompany.todoapp.model.MemoTodo
import com.jtmcompany.todoapp.viewmodel.CalendarViewModel

import com.prolificinteractive.materialcalendarview.*
import java.util.*

class CalendarFragment : Fragment(), OnDateSelectedListener, View.OnClickListener,

    OnMonthChangedListener,
    CalendarAdapter.CheckClickListener, CalendarAdapter.CalendarStatusListner {

    private lateinit var alarmManager: AlarmManager
    private val INSERT_REQUEST_CODE: Int = 100
    private val UPDATE_REQUEST_CODE: Int= 101
    private var curDate: String = ""

    private lateinit var viewModel: CalendarViewModel
    private var year:String=Calendar.getInstance().get(Calendar.YEAR).toString()
    private var month:String=Calendar.getInstance().get(Calendar.MONTH).toString()
    private var day:String=Calendar.getInstance().get(Calendar.DATE).toString()
    private var adapter: CalendarAdapter? = null
    private var curId:Int=0
    lateinit var binding:FragmentCalendarBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_calendar, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CalendarViewModel::class.java)
        alarmManager=context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
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

            //데이터값이 변경되면 리싸이클러뷰 업데이트
            adapter?.notify(viewModel.selectedlList)

            //조회한 날짜가 모두 비었다면 빨간점 삭제
            if(viewModel.selectedlList.isEmpty()) {
                binding.calendarV.removeDecorators()
            }

            //값이 저장된 날짜에 빨간점 표시
            for(saveData in it)
                binding.calendarV.addDecorators(EventDecorator(saveData.year,saveData.month,saveData.day))
        })

        //오늘 날짜 조회
        viewModel.select(year, month, day)
        val list=viewModel.selectedlList

        adapter= CalendarAdapter(list)
        adapter?.setClickListenr(this)
        adapter?.setCalendarStatusListener(this)
        binding.calendarRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.calendarRv.adapter=adapter

        val itemTouchHelper= ItemTouchHelper(
            ItemTouchHelperCallback(
                adapter
            )
        )
        itemTouchHelper.attachToRecyclerView(binding.calendarRv)
    }




    fun viewSetting(){
        binding.calendarV.addDecorators(ToDayDecorator())
        binding.calendarV.setSelectedDate(Calendar.getInstance())

        binding.calendarV.setOnDateChangedListener(this)
        binding.calendarV.setOnMonthChangedListener(this)
        binding.todoAddBt.setOnClickListener(this)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //다이얼로그에서  추가하기 버튼을 눌렀을 때
        if (requestCode==INSERT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            var newCalendarTodo = data?.getSerializableExtra("addNewCalendar") as CalendarTodo
            viewModel.insert(newCalendarTodo)
            viewModel.selectObject(newCalendarTodo.year,newCalendarTodo.month,newCalendarTodo.day,newCalendarTodo.hour,newCalendarTodo.minute)
            if(newCalendarTodo.isAlarm)
                setAlaram(viewModel.selectedlObject)

        }

        //다이얼로그에서  수정하기 버튼을 눌렀을 때
        else if(requestCode==UPDATE_REQUEST_CODE && resultCode==Activity.RESULT_OK){
            val newCalendarTodo = data?.getSerializableExtra("updateCalendar_OK") as CalendarTodo
            updateAlarm(newCalendarTodo)

            viewModel.update(newCalendarTodo)
        }
    }



    //날짜를 클릭했을때 콜백
    override fun onDateSelected(
        widget: MaterialCalendarView,
        date: CalendarDay,
        selected: Boolean
    ) {

        var curDate=viewModel.parsingDate(date.toString())
        year=curDate[0]; month=curDate[1]; day=curDate[2]

        //선택한 날짜 조회
        viewModel.select(year, month, day)
        val list=viewModel.selectedlList

        if(list.isEmpty()) { Log.d("tak", "no") }
        //조회회한 리스트를 여줌
        adapter?.notify(list)
    }






    fun setAlaram(newCalendarTodo: CalendarTodo){

        var receiverIntent= Intent(context, AlaramReceiver::class.java)
        receiverIntent.putExtra("alarmContent",newCalendarTodo.content)
        receiverIntent.putExtra("alarmId",newCalendarTodo.id)

        //알람 식별자로 db의 id를 넣음
        Log.d("tak","alarmId: "+newCalendarTodo.id)

        var pendingIntent = PendingIntent.getBroadcast(context,newCalendarTodo.id,receiverIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)
        var calendar=Calendar.getInstance()
        //calendar.time=dateTime
        calendar.set(Calendar.YEAR,newCalendarTodo.year.toInt())
        calendar.set(Calendar.MONTH,newCalendarTodo.month.toInt())
        calendar.set(Calendar.DATE,newCalendarTodo.day.toInt())
        calendar.set(Calendar.HOUR_OF_DAY,newCalendarTodo.hour)
        calendar.set(Calendar.MINUTE,newCalendarTodo.minute)
        calendar.set(Calendar.SECOND,0)
        //Log.d("tak","year: "+year)
        //Log.d("tak","month: "+month)
        //Log.d("tak","day: "+day)
        Log.d("tak", calendar.time.toString())

        //알람등록
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

    }



    fun updateAlarm(newCalendarTodo: CalendarTodo){
        var intent=Intent(context,AlaramReceiver::class.java)
        intent.putExtra("alarmContent",newCalendarTodo.content)
        intent.putExtra("alarmId",newCalendarTodo.id)

        Log.d("tak","alarmId: "+ newCalendarTodo.id)
        var sender=PendingIntent.getBroadcast(context, newCalendarTodo.id,intent,
            PendingIntent.FLAG_NO_CREATE) //이미 설절된 알람이 없다면 NULL 반환


        //설정된 알람이 없는경우
        if(sender==null){
            //Log.d("tak","설정된 알람이 없음")
            //알람이 켜져있다면 새로 설정
            if(newCalendarTodo.isAlarm){
                setAlaram(newCalendarTodo)
            }

        }
        //설정된 알람이 있는경우
        else {
            //Log.d("tak","설정된 알람이 있음")
            //알람을 꺼져있다면 - 취소
            if(!newCalendarTodo.isAlarm){
                alarmManager.cancel(sender)
                sender.cancel()
            }
            //알람이 켜져있다면 - 업데이트
            else{
                setAlaram(newCalendarTodo)
            }
        }
    }




    fun deleteAlarm(newCalendarTodo: CalendarTodo){
        var intent=Intent(context,AlaramReceiver::class.java)
        //Log.d("tak","alarmId: "+ newCalendarTodo.id)
        var sender=PendingIntent.getBroadcast(context, newCalendarTodo.id,intent,
            PendingIntent.FLAG_NO_CREATE) //이미 설절된 알람이 없다면 NULL 반환
        if(sender!=null) {
            alarmManager.cancel(sender)
            sender.cancel()
        }

    }




    //일정추가
    override fun onClick(p0: View?) {
            val intent = Intent(context, AddCalendarActivity::class.java)
            intent.putExtra("year",year)
            intent.putExtra("month",month)
            intent.putExtra("day",day)
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
        var aintent=Intent(context,AlaramReceiver::class.java)
        Log.d("tak","alarmId: "+ cal.id)
        var sender=PendingIntent.getBroadcast(context, cal.id,aintent,
            PendingIntent.FLAG_NO_CREATE) //이미 설절된 알람이 없다면 NULL 반환


        //설정된 알람이 없는경우
        if(sender==null){
            Log.d("tak","설정된 알람 X")
            Toast.makeText(context, "설정된 알람 X", Toast.LENGTH_SHORT).show()
        }else if(sender!=null){
            Log.d("tak","설정된 알람 O")
            Toast.makeText(context, "설정된 알람 O", Toast.LENGTH_SHORT).show()
        }


        val intent=Intent(activity,UpdateCalendarActivity::class.java)
        //intent.putExtra("content",cal.content)
        intent.putExtra("updateCalendar",cal)
        startActivityForResult(intent,UPDATE_REQUEST_CODE)
        curId=cal.id
    }



    override fun itemOnMoved(fromMemoTodo: CalendarTodo, toMemoTodo: CalendarTodo) {


    }



    override fun itemOnSwipe(calendarTodo: CalendarTodo) {
        var dialogBuilder= AlertDialog.Builder(context,android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
        dialogBuilder.setMessage("메모를 삭제 하시겠습니까?")
            .setCancelable(true)
            .setPositiveButton("삭제",object: DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    viewModel.delete(calendarTodo)
                    deleteAlarm(calendarTodo)
                }
            })
            .setNegativeButton("취소",object:DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                }
            })
        var dialog=dialogBuilder.create()
        dialog.show()
    }


}