package com.jtmcompany.todoapp.itemtouch_helper

interface ItemTouchHelperListener {
    fun onMove(fromPosition:Int, toPosition:Int):Boolean
    fun onSwipe(position: Int)

}