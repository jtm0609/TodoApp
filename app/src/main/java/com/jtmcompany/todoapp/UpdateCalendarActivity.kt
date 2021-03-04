package com.jtmcompany.todoapp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.TimePicker
import com.jtmcompany.todoapp.model.CalendarTodo
import kotlinx.android.synthetic.main.activity_update_calendar.*
import kotlinx.android.synthetic.main.activity_update_dialog.*

class UpdateCalendarActivity : AppCompatActivity(), View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {

    var newCalendarTodo : CalendarTodo?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_calendar)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if(intent.getSerializableExtra("updateCalendar")!=null){
            newCalendarTodo=intent.getSerializableExtra("updateCalendar") as CalendarTodo
            updateCalendarEt.setText(newCalendarTodo!!.content)

            //알람이 설정된 메모라면 알람 정보불러오기
            if(newCalendarTodo!!.isAlarm){
                updateSwitchView.isChecked=true
                if(updateSwitchView.isChecked) {
                    timepicker.visibility=View.VISIBLE
                    timepicker.hour = newCalendarTodo!!.hour
                    timepicker.minute = newCalendarTodo!!.minute
                }
            }
        }

        updateSwitchView.setOnCheckedChangeListener(this)
        updateCalendar_bt.setOnClickListener(this)
        updateCalendarCancelBt.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v==updateCalendar_bt){
            if(newCalendarTodo!=null) {
                //받은 객체에 수정한 데이터 갱신
                newCalendarTodo!!.content = updateCalendarEt.text.toString()
                newCalendarTodo!!.isAlarm=updateSwitchView.isChecked
                newCalendarTodo!!.hour=timepicker.hour
                newCalendarTodo!!.minute=timepicker.minute

                intent.putExtra("updateCalendar_OK", newCalendarTodo)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
        }else {
            Log.d("tak","취소?")
            val intent= Intent()
            setResult(Activity.RESULT_CANCELED,intent)
            finish()
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if(isChecked){
            timepicker.visibility=View.VISIBLE
        }else {
            timepicker.visibility=View.GONE
        }
    }

}