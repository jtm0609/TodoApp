package com.jtmcompany.todoapp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.jtmcompany.todoapp.databinding.ActivityInputDialogBinding
import com.jtmcompany.todoapp.model.MemoTodo


class AddMemoActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding:ActivityInputDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_input_dialog)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.insertBt.setOnClickListener(this)
        binding.insetBackBt.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.insertBt->{
                val newTitle=binding.titleEt.text.toString()
                val newContent=binding.contentEt.text.toString()
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