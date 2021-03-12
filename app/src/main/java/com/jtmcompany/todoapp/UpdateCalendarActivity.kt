package com.jtmcompany.todoapp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.jtmcompany.todoapp.databinding.ActivityUpdateCalendarBinding
import com.jtmcompany.todoapp.model.CalendarTodo

import java.util.*

class UpdateCalendarActivity : AppCompatActivity(), View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {

    var newCalendarTodo : CalendarTodo?=null
    lateinit var binding:ActivityUpdateCalendarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_update_calendar)

        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        if(intent.getSerializableExtra("updateCalendar")!=null){
            newCalendarTodo=intent.getSerializableExtra("updateCalendar") as CalendarTodo
            binding.updateCalendarEt.setText(newCalendarTodo!!.content)

            Log.d("tak", newCalendarTodo!!.isAlarm.toString())
            if(!newCalendarTodo!!.isAlarm && binding.updateSwitchView.isChecked){
                binding.updateSwitchView.isChecked=false
            }

            //알람이 설정된 메모라면 알람 정보불러오기
            if(newCalendarTodo!!.isAlarm){
                binding.updateSwitchView.isChecked=true
                if(binding.updateSwitchView.isChecked) {
                    binding.timepicker.visibility=View.VISIBLE
                    binding.timepicker.hour = newCalendarTodo!!.hour
                    binding.timepicker.minute = newCalendarTodo!!.minute
                }
            }


        }


        binding.updateSwitchView.setOnCheckedChangeListener(this)
        binding.updateCalendarBt.setOnClickListener(this)
        binding.updateCalendarCancelBt.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v==binding.updateCalendarBt){
            if(newCalendarTodo!=null) {
                val isAlarm=binding.updateSwitchView.isChecked
                var curCalendar= Calendar.getInstance()

                var curTime=curCalendar.timeInMillis

                var selectCalendar=Calendar.getInstance()
                selectCalendar.set(Calendar.YEAR, newCalendarTodo!!.year.toInt())
                selectCalendar.set(Calendar.MONTH, newCalendarTodo!!.month.toInt())
                selectCalendar.set(Calendar.DATE, newCalendarTodo!!.day.toInt())
                selectCalendar.set(Calendar.HOUR_OF_DAY,binding.timepicker.hour)
                selectCalendar.set(Calendar.MINUTE,binding.timepicker.minute)

                var selectTime=selectCalendar.timeInMillis



                if(isAlarm && selectTime<curTime)
                    Toast.makeText(this, "현재보다 과거 시간으로 알람을 설정할 수 없습니다!", Toast.LENGTH_SHORT).show()


                else {
                    //받은 객체에 수정한 데이터 갱신
                    newCalendarTodo!!.content = binding.updateCalendarEt.text.toString()
                    newCalendarTodo!!.isAlarm = binding.updateSwitchView.isChecked
                    newCalendarTodo!!.hour = binding.timepicker.hour
                    newCalendarTodo!!.minute = binding.timepicker.minute


                    intent.putExtra("updateCalendar_OK", newCalendarTodo)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
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
            binding.timepicker.visibility=View.VISIBLE
        }else {
            binding.timepicker.visibility=View.GONE
        }
    }

}