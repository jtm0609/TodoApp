package com.jtmcompany.todoapp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_input_dialog.*

import kotlinx.android.synthetic.main.activity_update_dialog.*
import android.util.Log
import com.jtmcompany.todoapp.model.CalendarTodo
import com.jtmcompany.todoapp.model.MemoTodo

class UpdateMemoActivity : AppCompatActivity(), View.OnClickListener {
    var newMemoTodo:MemoTodo?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_dialog)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))



        if(intent.getSerializableExtra("updateMemo")!=null){
            newMemoTodo=intent.getSerializableExtra("updateMemo") as MemoTodo
            Log.d("tak","memo: "+newMemoTodo!!.title )
            updateTitleEt.setText(newMemoTodo!!.title)
            updateContentEt.setText(newMemoTodo!!.content)
        }





        updateMemoBt.setOnClickListener(this)
        updateMemoBackBt.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.updateMemoBt->{

                if(newMemoTodo!=null){
                    //받은 객체에 수정한 데이터 갱신
                    newMemoTodo!!.title = updateTitleEt.text.toString()
                    newMemoTodo!!.content=updateContentEt.text.toString()

                    intent.putExtra("updateMemo_OK", newMemoTodo)
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