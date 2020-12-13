package com.jtmcompany.todoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_memo.view.*
import android.util.Log
import com.jtmcompany.todoapp.R
import com.jtmcompany.todoapp.model.Memo

class MemoAdapter(var list: List<Memo>) : RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {

    interface MemoClickListener{
        fun itemOnClick(memo:Memo)
    }
    lateinit var listener: MemoClickListener

    fun setOnMemoClickListener(listener: MemoClickListener){
        this.listener=listener
    }

    inner class MemoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val memo=itemView.memo_tv
        val memoContainer=itemView.memo_container
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        Log.d("tak","memoAdapter")
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_memo,parent,false)
        return MemoViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("tak","사이즈: "+list.size)
        return list.size
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        val memo=list.get(position)
        holder.memo.setText(memo.content)
        holder.memoContainer.setOnClickListener { listener.itemOnClick(memo) }

    }

    fun notify(updateList: List<Memo>){
        list=updateList
        notifyDataSetChanged()
    }

}