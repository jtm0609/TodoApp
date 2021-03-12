package com.jtmcompany.todoapp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.jtmcompany.todoapp.databinding.ActivityAddCalendarMemoBinding
import com.jtmcompany.todoapp.model.CalendarTodo
import java.util.*

class AddCalendarActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener,
    View.OnClickListener, TimePicker.OnTimeChangedListener {
    lateinit var year:String
    lateinit var month:String
    lateinit var day:String
    lateinit var newCalendarTodo: CalendarTodo
    lateinit var binding:ActivityAddCalendarMemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_add_calendar_memo)

        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.switchView.setOnCheckedChangeListener(this)
        binding.positiveBt.setOnClickListener(this)
        binding.negativeBt.setOnClickListener(this)
        binding.timepicker.setOnTimeChangedListener(this)

        year=intent.getStringExtra("year")
        month=intent.getStringExtra("month")
        day=intent.getStringExtra("day")


    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if(isChecked){
            binding.timepicker.visibility= View.VISIBLE
        }else{
            binding.timepicker.visibility= View.GONE
        }

    }

    override fun onClick(v: View?) {
        if(v==binding.positiveBt){
            var newContent=binding.calendarEt.text.toString()
            val isAlarm=binding.switchView.isChecked

            var curCalendar= Calendar.getInstance()

            var curTime=curCalendar.timeInMillis

            var selectCalendar=Calendar.getInstance()
            selectCalendar.set(Calendar.YEAR,year.toInt())
            selectCalendar.set(Calendar.MONTH,month.toInt())
            selectCalendar.set(Calendar.DATE,day.toInt())
            selectCalendar.set(Calendar.HOUR_OF_DAY,binding.timepicker.hour)
            selectCalendar.set(Calendar.MINUTE,binding.timepicker.minute)

            var selectTime=selectCalendar.timeInMillis



            if(isAlarm && selectTime<curTime)
                Toast.makeText(this, "현재보다 과거 시간으로 알람을 설정할 수 없습니다!", Toast.LENGTH_SHORT).show()


             else {
                    newCalendarTodo = CalendarTodo(
                        year,
                        month,
                        day,
                        newContent,
                        false,
                        isAlarm,
                        binding.timepicker.hour,
                        binding.timepicker.minute
                    )


                    var intent = Intent()
                    intent.putExtra("addNewCalendar", newCalendarTodo)
                    setResult(Activity.RESULT_OK, intent)
                    finish()

                }

        }else if(v==binding.negativeBt){
            finish()
        }
    }



    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
       // Log.d("tak", hourOfDay.toString())
       // Log.d("tak", minute.toString())
    }
}