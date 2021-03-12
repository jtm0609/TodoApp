package com.jtmcompany.todoapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jtmcompany.todoapp.R
import com.jtmcompany.todoapp.databinding.ItemCalendarContentBinding
import com.jtmcompany.todoapp.itemtouch_helper.ItemTouchHelperListener
import com.jtmcompany.todoapp.model.CalendarTodo


class CalendarAdapter(val list: List<CalendarTodo>) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>(),
    ItemTouchHelperListener {

    class CalendarViewHolder(binding: ItemCalendarContentBinding) : RecyclerView.ViewHolder(binding.root){
        val item_tv=binding.itemText
        val item_ck=binding.itemCheck
        var item=binding.calendarItem
        var item_alram=binding.alramIv
    }

    interface CheckClickListener{
        fun checkOnClick(calendarTodo: CalendarTodo)
        fun onUpdate(calendarTodo: CalendarTodo)

    }

    interface CalendarStatusListner{
        fun itemOnSwipe(calendarTodo:CalendarTodo)
        fun itemOnMoved(fromCalendar: CalendarTodo,toCalendar:CalendarTodo)
    }
    lateinit var statusListener: CalendarAdapter.CalendarStatusListner

    var mList=list
    lateinit var listener: CheckClickListener

    fun setClickListenr(listener: CheckClickListener){
        this.listener=listener
    }

    fun setCalendarStatusListener(listener: CalendarStatusListner){
        this.statusListener=listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        var binding= ItemCalendarContentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CalendarViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val cal: CalendarTodo =mList.get(position)
        if(cal.isAlarm)
            holder.item_alram.visibility=View.VISIBLE
        else if(!cal.isAlarm){
            holder.item_alram.visibility=View.GONE
        }

        holder.item_tv.setText(cal.content)
        holder.item_ck.isChecked=cal.check
        holder.item_ck.setOnClickListener {

            if(holder.item_ck.isChecked) {
                cal.check = true
                listener.checkOnClick(cal)
            }
            else {
                cal.check=false
                listener.checkOnClick(cal)
            }
        }
        holder.item.setOnClickListener {
            Log.d("tak","클릭")
            listener.onUpdate(cal)
        }


    }

    fun notify(list:List<CalendarTodo>){
        mList=list
        notifyDataSetChanged()
    }



    override fun onMove(fromPosition: Int, toPosition: Int): Boolean {
        return true
    }



    override fun onSwipe(position: Int) {
        Log.d("tak","test:"+position)
        if(mList.get(position)==null)
            Log.d("tak","null!")
        statusListener.itemOnSwipe(mList.get(position))
        notifyDataSetChanged()
        Log.d("tak","swipe")
    }
}