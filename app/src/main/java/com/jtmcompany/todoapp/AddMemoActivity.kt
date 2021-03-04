package com.jtmcompany.todoapp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.jtmcompany.todoapp.model.MemoTodo
import kotlinx.android.synthetic.main.activity_input_dialog.*
import kotlinx.android.synthetic.main.activity_update_dialog.*

class AddMemoActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_dialog)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        insertBt.setOnClickListener(this)
        insetBackBt.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.insertBt->{
                val newTitle=titleEt.text.toString()
                val newContent=contentEt.text.toString()
                val intent= Intent()
                var newMemo=MemoTodo(newTitle,newContent)
                intent.putExtra("add_OK",newMemo)
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