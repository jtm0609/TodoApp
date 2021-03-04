package com.jtmcompany.todoapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jtmcompany.todoapp.R
import com.jtmcompany.todoapp.itemtouch_helper.ItemTouchHelperListener
import com.jtmcompany.todoapp.model.CalendarTodo
import kotlinx.android.synthetic.main.item_calendar_content.view.*

class CalendarAdapter(val list: List<CalendarTodo>) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>(),
    ItemTouchHelperListener {

    class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val item_tv=itemView.item_text
        val item_ck=itemView.item_check
        var item=itemView.calendarItem
        var item_alram=itemView.alramIv
    }

    interface CheckClickListener{
        fun checkOnClick(calendarTodo: CalendarTodo)
        fun onUpdate(calendarTodo: CalendarTodo)

    }

    interface CalendarStatusListner{
        fun itemOnSwipe(calendarTodo:CalendarTodo)
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
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_content,parent,false)
        return CalendarViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val cal: CalendarTodo =mList.get(position)
        if(cal.isAlarm)
            holder.item_alram.visibility=View.VISIBLE

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

    fun update(list:List<CalendarTodo>){
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