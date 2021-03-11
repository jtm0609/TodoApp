package com.jtmcompany.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jtmcompany.todoapp.fragment.CalendarFragment
import com.jtmcompany.todoapp.fragment.MemoFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("tak","onCreate!")
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.replace_layout, MemoFragment())
            .commit()

        bottom_nv.setOnNavigationItemSelectedListener(this)
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.todo-> supportFragmentManager
                .beginTransaction()
                .replace(R.id.replace_layout, MemoFragment())
                .commit()

            R.id.calendar->supportFragmentManager
                .beginTransaction()
                .replace(R.id.replace_layout, CalendarFragment())
                .commit()

        }
        return true
    }


    override fun onPause() {
        super.onPause()
        Log.d("tak","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("tak","onStop")
    }
}