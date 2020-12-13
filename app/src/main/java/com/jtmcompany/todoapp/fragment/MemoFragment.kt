package com.jtmcompany.todoapp.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jtmcompany.todoapp.*
import com.jtmcompany.todoapp.adapter.MemoAdapter
import com.jtmcompany.todoapp.model.Memo
import com.jtmcompany.todoapp.viewmodel.MemoViewModel
import kotlinx.android.synthetic.main.fragment_memo.*


class MemoFragment : Fragment(), View.OnClickListener, MemoAdapter.MemoClickListener {
    private val REQUEST_UPDATE_CODE: Int=101
    private val REQUEST_CODE: Int=100
    var memoAdapter: MemoAdapter?=null
    lateinit var viewModel: MemoViewModel
    var list=ArrayList<String>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_memo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MemoViewModel::class.java)
        memo_add_bt.setOnClickListener(this)
        memo_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        itemTouchHelper.attachToRecyclerView(memo_rv)

        viewModel.memoList.observe(viewLifecycleOwner, Observer {
            Log.d("tak", "memoObserve")
            if (memoAdapter == null) {
                memoAdapter = MemoAdapter(it)
                memoAdapter?.setOnMemoClickListener(this)
                memo_rv.adapter = memoAdapter

            }
            memoAdapter?.notify(it)
        })


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==REQUEST_CODE && resultCode==Activity.RESULT_OK){
            val content=data?.getStringExtra("content").toString()
            Log.d("tak",content)
            viewModel.insert(Memo(content))
        }else if(requestCode==REQUEST_UPDATE_CODE && resultCode==Activity.RESULT_OK){
            val id=data?.getIntExtra("id",0)
            var content=data?.getStringExtra("content").toString()
            if (id != null) {
                viewModel.update(content,id)
            }


        }
    }


    override fun onClick(p0: View?) {
        val intent = Intent(context,InputDialog::class.java)
        startActivityForResult(intent,REQUEST_CODE)
    }

    override fun itemOnClick(memo: Memo) {
        Log.d("tak",""+memo.id)
        Log.d("tak",""+memo.content)
        val intent=Intent(context,updateDialog::class.java)
        intent.putExtra("id",memo.id)
        intent.putExtra("content",memo.content)
        startActivityForResult(intent,REQUEST_UPDATE_CODE)
    }

    //<<구현해야할부분>>
    //삭제기능, 순서바꾸기기능 구현해야함
    //https://everyshare.tistory.com/27 보고
    //1. 헬퍼 따로 클래스로 분리하고
    //2. 스와이프나 move했을때 어댑터에게 알려주고 인터페이스를 구현한다음
    //3. 프래그먼트에서 인터페이스를 구현객체로 만들어서 콜백한다.
    val itemTouchHelper=ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            val drag_flags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipe_flags = ItemTouchHelper.START or ItemTouchHelper.END
            return ItemTouchHelper.Callback.makeMovementFlags(drag_flags,swipe_flags)
        }

        //순서이동
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            Log.d("tak","move")
            return true
        }

        //스와이프됬을때 (아이템이 사라졋을때)
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            Log.d("tak","swiped")
            Log.d("tak",""+viewHolder.adapterPosition)

        }

        override fun isLongPressDragEnabled(): Boolean {
            return true
        }

    })



}