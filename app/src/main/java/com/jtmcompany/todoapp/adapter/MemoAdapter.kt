package com.jtmcompany.todoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_memo.view.*
import android.util.Log
import com.jtmcompany.todoapp.itemtouch_helper.ItemTouchHelperListener
import com.jtmcompany.todoapp.R
import com.jtmcompany.todoapp.model.Memo

class MemoAdapter(var list: ArrayList<Memo>) : RecyclerView.Adapter<MemoAdapter.MemoViewHolder>(),
    ItemTouchHelperListener {

    interface MemoClickListener{
        fun itemOnClick(memo:Memo)
    }

    interface MemoStatusListener{
        fun itemOnMoved(fromMemo:Memo,toMemo:Memo)
        fun itemOnSwipe(memo:Memo)
    }
    lateinit var clickListener: MemoClickListener
    lateinit var statusListener:MemoStatusListener

    fun setOnMemoClickListener(listener: MemoClickListener){
        this.clickListener=listener
    }

    fun setOnMemoStatusListener(listener: MemoStatusListener){
        this.statusListener=listener
    }

    inner class MemoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val memo=itemView.memo_tv
        val memoContainer=itemView.memo_container
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
       // Log.d("tak","memoAdapter")
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_memo,parent,false)
        return MemoViewHolder(view)
    }

    override fun getItemCount(): Int {
        //Log.d("tak","사이즈: "+list.size)
        return list.size
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        val memo=list.get(position)
        holder.memo.setText(memo.content)
        holder.memoContainer.setOnClickListener {
            Log.d("tak","adapterPostion: "+position)
            clickListener.itemOnClick(memo)
        }

        holder.memoContainer.setOnLongClickListener(object :View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                Log.d("tak","long")

                return true
            }
        })

    }

    fun notify(updateList: ArrayList<Memo>){
        list=updateList
        notifyDataSetChanged()
    }

    override fun onMove(fromPosition: Int, toPosition: Int): Boolean {
        //이동할 아이템
        val memo=list.get(fromPosition)

        val fromMemo=list.get(fromPosition)
        val toMemo=list.get(toPosition)


        notifyItemMoved(fromPosition,toPosition)

        Log.d("tak",""+fromPosition+" -> "+toPosition)
        Log.d("tak","adapter_swap: "+fromMemo.id+" -> "+toMemo.id)
        statusListener.itemOnMoved(fromMemo,toMemo)

        return true
    }

    override fun onSwipe(position: Int) {

        statusListener.itemOnSwipe(list.get(position))
        Log.d("tak","swipe")

    }

}