package com.jtmcompany.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jtmcompany.todoapp.fragment.CalendarFragment
import com.jtmcompany.todoapp.fragment.TodolistFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        supportFragmentManager.beginTransaction().replace(R.id.replace_layout,
            TodolistFragment()
        ).commit()
        bottom_nv.setOnNavigationItemSelectedListener(object:BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId){
                    R.id.todo-> supportFragmentManager.beginTransaction().replace(R.id.replace_layout,
                        TodolistFragment()
                    ).commit()
                    R.id.calendar->supportFragmentManager.beginTransaction().replace(R.id.replace_layout,
                        CalendarFragment()
                    ).commit()

                }
        return true
        }
        })


    }
}