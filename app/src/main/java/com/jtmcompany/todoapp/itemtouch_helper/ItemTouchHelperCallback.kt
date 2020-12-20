package com.jtmcompany.todoapp.itemtouch_helper

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

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

}