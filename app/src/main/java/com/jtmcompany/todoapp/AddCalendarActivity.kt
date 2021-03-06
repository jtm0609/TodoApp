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
import com.jtmcompany.todoapp.model.CalendarTodo
import kotlinx.android.synthetic.main.activity_add_calendar_memo.*
import java.util.*

class AddCalendarActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener,
    View.OnClickListener, TimePicker.OnTimeChangedListener {
    lateinit var year:String
    lateinit var month:String
    lateinit var day:String
    lateinit var newCalendarTodo: CalendarTodo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_calendar_memo)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        switchView.setOnCheckedChangeListener(this)
        positive_bt.setOnClickListener(this)
        negative_bt.setOnClickListener(this)
        timepicker.setOnTimeChangedListener(this)

        year=intent.getStringExtra("year")
        month=intent.getStringExtra("month")
        day=intent.getStringExtra("day")





    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if(isChecked){
            timepicker.visibility= View.VISIBLE
        }else{
            timepicker.visibility= View.GONE
        }

    }

    override fun onClick(v: View?) {
        if(v==positive_bt){
            var newContent=calendarEt.text.toString()
            val isAlarm=switchView.isChecked

            newCalendarTodo=CalendarTodo(year, month, day,newContent,false,isAlarm,timepicker.hour,timepicker.minute)


            var intent= Intent()
            intent.putExtra("addNewCalendar",newCalendarTodo)
            setResult(Activity.RESULT_OK,intent)
            finish()



        }else if(v==negative_bt){

        }
    }



    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
       // Log.d("tak", hourOfDay.toString())
       // Log.d("tak", minute.toString())
    }
}