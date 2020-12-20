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

class updateDialogActivity : AppCompatActivity(), View.OnClickListener {
    var id=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_dialog)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        id= intent.getIntExtra("id",0)
        val content=intent.getStringExtra("content")
        Log.d("tak","받은값: "+content)
        Log.d("tak","받은값: "+id)
        upcontent_et.setText(content)


        positive_bt.setOnClickListener(this)
        negative_bt.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.positive_bt->{
                val content=upcontent_et.text.toString()
                val intent= Intent()
                intent.putExtra("id",id)
                intent.putExtra("content",content)
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