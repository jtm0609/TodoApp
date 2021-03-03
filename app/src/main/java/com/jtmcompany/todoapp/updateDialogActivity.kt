package com.jtmcompany.todoapp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_input_dialog.*
import kotlinx.android.synthetic.main.activity_input_dialog.negative_bt
import kotlinx.android.synthetic.main.activity_input_dialog.positive_bt
import kotlinx.android.synthetic.main.activity_update_dialog.*
import android.util.Log
import com.jtmcompany.todoapp.model.CalendarTodo
import com.jtmcompany.todoapp.model.MemoTodo

class updateDialogActivity : AppCompatActivity(), View.OnClickListener {
    var calendarTodo : CalendarTodo?=null
    var memoTodo:MemoTodo?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_dialog)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        if(intent.getSerializableExtra("updateCalendar")!=null){
            calendarTodo=intent.getSerializableExtra("updateCalendar") as CalendarTodo
            upcontent_et.setText(calendarTodo!!.content)
        }
        else if(intent.getSerializableExtra("updateMemo")!=null){
            memoTodo=intent.getSerializableExtra("updateMemo") as MemoTodo
            Log.d("tak","memo: "+memoTodo!!.content )
            upcontent_et.setText(memoTodo!!.content)
        }



        positive_bt.setOnClickListener(this)
        negative_bt.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.positive_bt->{
                if(calendarTodo!=null) {
                    calendarTodo!!.content = upcontent_et.text.toString()
                    intent.putExtra("updateCalendar_OK", calendarTodo)
                    Log.d("tak", calendarTodo!!.content)
                }
                else if(memoTodo!=null){
                    memoTodo!!.content = upcontent_et.text.toString()
                    intent.putExtra("updateMemo_OK", memoTodo)
                    Log.d("tak", memoTodo!!.content)
                }
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
            else->{
                val intent= Intent()
                setResult(Activity.RESULT_CANCELED,intent)
                finish()
            }
        }
    }


}