package com.jtmcompany.todoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.jtmcompany.todoapp.itemtouch_helper.ItemTouchHelperListener
import com.jtmcompany.todoapp.R
import com.jtmcompany.todoapp.databinding.ItemMemoBinding
import com.jtmcompany.todoapp.model.MemoTodo

class MemoAdapter(var list: ArrayList<MemoTodo>) : RecyclerView.Adapter<MemoAdapter.MemoViewHolder>(),
    ItemTouchHelperListener {

    interface MemoClickListener{
        fun itemOnClick(memoTodo:MemoTodo)
    }

    interface MemoStatusListener{
        fun itemOnMoved(fromMemoTodo:MemoTodo, toMemoTodo:MemoTodo)
        fun itemOnSwipe(memoTodo:MemoTodo)
    }
    lateinit var clickListener: MemoClickListener
    lateinit var statusListener:MemoStatusListener

    fun setOnMemoClickListener(listener: MemoClickListener){
        this.clickListener=listener
    }

    fun setOnMemoStatusListener(listener: MemoStatusListener){
        this.statusListener=listener
    }

    inner class MemoViewHolder(binding: ItemMemoBinding) : RecyclerView.ViewHolder(binding.root) {
        val titleTv=binding.memoTitle
        val contentTv=binding.memoContent
        val memoContainer=binding.memoContainer
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
       // Log.d("tak","memoAdapter")
        var binding=ItemMemoBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return MemoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        //Log.d("tak","사이즈: "+list.size)
        return list.size
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        val memo=list.get(position)
        holder.titleTv.setText(memo.title)
        holder.contentTv.setText(memo.content)
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

    fun notify(updateList: ArrayList<MemoTodo>){
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
        notifyDataSetChanged()
        Log.d("tak","swipe")

    }

}