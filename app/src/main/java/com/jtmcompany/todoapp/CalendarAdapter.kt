package com.jtmcompany.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jtmcompany.todoapp.room.CalendarTodo
import kotlinx.android.synthetic.main.item_calendar_content.view.*
import java.time.Month
import java.time.Year

class CalendarAdapter(val list: List<CalendarTodo>) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val item_tv=itemView.item_text
        val item_ck=itemView.item_check
        val item_update=itemView.item_update
        val item_delete=itemView.item_delete
    }

    interface CheckClickListener{
        fun checkOnClick(calendarTodo: CalendarTodo)
        fun onUpdate(calendarTodo: CalendarTodo)
        fun onDelete(calendarTodo: CalendarTodo)
    }

    var mList=list
    lateinit var listener: CheckClickListener

    fun setClickListenr(listener: CheckClickListener){
        this.listener=listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_content,parent,false)
        return CalendarViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val cal:CalendarTodo=mList.get(position)

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
        holder.item_update.setOnClickListener {
            listener.onUpdate(cal)
        }

        holder.item_delete.setOnClickListener{
            listener.onDelete(cal)
        }
    }

    fun update(list:List<CalendarTodo>){
        mList=list
        notifyDataSetChanged()
    }
}