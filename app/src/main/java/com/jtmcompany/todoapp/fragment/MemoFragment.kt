package com.jtmcompany.todoapp.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.jtmcompany.todoapp.*
import com.jtmcompany.todoapp.adapter.MemoAdapter
import com.jtmcompany.todoapp.itemtouch_helper.ItemTouchHelperCallback
import com.jtmcompany.todoapp.model.MemoTodo
import com.jtmcompany.todoapp.viewmodel.MemoViewModel
import kotlinx.android.synthetic.main.fragment_memo.*


class MemoFragment : Fragment(), View.OnClickListener, MemoAdapter.MemoClickListener,
    MemoAdapter.MemoStatusListener {
    private val REQUEST_UPDATE_CODE: Int=101
    private val REQUEST_CODE: Int=100
    var memoAdapter: MemoAdapter?=null
    var moved: Boolean = false
    lateinit var viewModel: MemoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_memo, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MemoViewModel::class.java)
        memo_add_bt.setOnClickListener(this)
        memo_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        viewModel.memoList.observe(viewLifecycleOwner, Observer {
            Log.d("tak", "memoObserve")
            if (memoAdapter == null) {
                memoAdapter = MemoAdapter(it as ArrayList<MemoTodo>)
                memoAdapter?.setOnMemoClickListener(this)
                memoAdapter?.setOnMemoStatusListener(this)
                memo_rv.adapter = memoAdapter

                val itemTouchHelper =ItemTouchHelper(
                    ItemTouchHelperCallback(
                        memoAdapter
                    )
                )
                itemTouchHelper.attachToRecyclerView(memo_rv)
            }

            if(!moved)
            memoAdapter?.notify(it as ArrayList<MemoTodo>)
            moved=false

        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //다이얼로그에서  추가하기 버튼을 눌렀을 때
        if(requestCode==REQUEST_CODE && resultCode==Activity.RESULT_OK){
            val newMemoTodo=data?.getSerializableExtra("add_OK") as MemoTodo
            //Log.d("tak",newMemoTodo.title)
            viewModel.insert(newMemoTodo)
        }

        //다이얼로그에서 수정하기 버튼을 눌렀을 때
        else if(requestCode==REQUEST_UPDATE_CODE && resultCode==Activity.RESULT_OK){
            Log.d("tak","안넘어오나?")
            var newMemoTodo=data?.getSerializableExtra("updateMemo_OK") as MemoTodo
            Log.d("tak","수정한 제목: "+newMemoTodo.title)
            Log.d("tak","수정한 내용" +newMemoTodo.content)
            viewModel.update(newMemoTodo)

        }

    }


    //추가
    override fun onClick(p0: View?) {
        val intent = Intent(context,AddMemoActivity::class.java)
        startActivityForResult(intent,REQUEST_CODE)
    }


    //업데이트
    override fun itemOnClick(memoTodo: MemoTodo) {
        Log.d("tak","Id: "+memoTodo.id)
        Log.d("tak","title: "+memoTodo.title)
        Log.d("tak","content: "+memoTodo.content)
        val intent=Intent(context,UpdateMemoActivity::class.java)
        intent.putExtra("updateMemo",memoTodo)
        startActivityForResult(intent,REQUEST_UPDATE_CODE)
    }


    override fun itemOnMoved(fromMemoTodo: MemoTodo, toMemoTodo: MemoTodo) {
        var temp=fromMemoTodo.content
        fromMemoTodo.content=toMemoTodo.content
        toMemoTodo.content=temp

        temp=fromMemoTodo.title
        fromMemoTodo.title=toMemoTodo.title
        toMemoTodo.title=temp

        Log.d("tak","swap "+fromMemoTodo.id +" "+toMemoTodo.id)
        moved=true
        viewModel.update(fromMemoTodo)
        viewModel.update(toMemoTodo)
        Log.d("tak","swap2 "+fromMemoTodo.id +" "+toMemoTodo.id)

    }



    //스왑해서 삭제
    override fun itemOnSwipe(memoTodo: MemoTodo) {
        Log.d("tak","delete")
        var dialogBuilder=AlertDialog.Builder(context,android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
        dialogBuilder.setMessage("메모를 삭제 하시겠습니까?")
            .setCancelable(true)
            .setPositiveButton("삭제",object:DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    viewModel.delete(memoTodo)
                }
            })
            .setNegativeButton("취소",object:DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {

                }
            })
        var dialog=dialogBuilder.create()
        dialog.show()



    }





}