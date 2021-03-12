package com.jtmcompany.todoapp.itemtouch_helper

import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class ItemTouchHelperCallback(val listener: ItemTouchHelperListener?) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val move_flag=ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipe_flag=ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(move_flag,swipe_flag)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        if (listener != null) {
            return listener.onMove(viewHolder.adapterPosition,target.adapterPosition)
        }
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (listener != null) {
            return listener.onSwipe(viewHolder.adapterPosition)
        }
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    //사용자가 아이템 move나 swipe를 마칠때 호출
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        try {
            recyclerView.adapter?.notifyDataSetChanged()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}