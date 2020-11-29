package com.jtmcompany.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_calendar_content.view.*

class CalendarAdapter(val list:ArrayList<String>) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {
    val mList=list

    class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val item_tv=itemView.item_text
        val item_ck=itemView.item_check
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_content,parent,false)
        return CalendarViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.item_tv.setText(mList.get(position))
    }
}