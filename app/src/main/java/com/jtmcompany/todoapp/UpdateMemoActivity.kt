package com.jtmcompany.todoapp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.jtmcompany.todoapp.databinding.ActivityUpdateDialogBinding
import com.jtmcompany.todoapp.model.CalendarTodo
import com.jtmcompany.todoapp.model.MemoTodo

class UpdateMemoActivity : AppCompatActivity(), View.OnClickListener {
    var newMemoTodo:MemoTodo?=null
    lateinit var binding:ActivityUpdateDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_update_dialog)

        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))



        if(intent.getSerializableExtra("updateMemo")!=null){
            newMemoTodo=intent.getSerializableExtra("updateMemo") as MemoTodo
            Log.d("tak","memo: "+newMemoTodo!!.title )

            //데이터 바인딩으로 xml에서 setText해준다.
            binding.memoTodo=newMemoTodo
            //binding.updateTitleEt.setText(newMemoTodo!!.title)
            //binding.updateContentEt.setText(newMemoTodo!!.content)
        }





        binding.updateMemoBt.setOnClickListener(this)
        binding.updateMemoBackBt.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.updateMemoBt->{

                if(newMemoTodo!=null){
                    //받은 객체에 수정한 데이터 갱신
                    newMemoTodo!!.title = binding.updateTitleEt.text.toString()
                    newMemoTodo!!.content=binding.updateContentEt.text.toString()

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