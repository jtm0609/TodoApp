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
import com.jtmcompany.todoapp.*
import com.jtmcompany.todoapp.adapter.MemoAdapter
import com.jtmcompany.todoapp.itemtouch_helper.ItemTouchHelperCallback
import com.jtmcompany.todoapp.model.Memo
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
                memoAdapter = MemoAdapter(it as ArrayList<Memo>)
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
            memoAdapter?.notify(it as ArrayList<Memo>)
            moved=false


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
                viewModel.updateQuery(content,id)
            }


        }
    }


    override fun onClick(p0: View?) {
        val intent = Intent(context,InputDialogActivity::class.java)
        startActivityForResult(intent,REQUEST_CODE)
    }

    override fun itemOnClick(memo: Memo) {
        Log.d("tak","Id: "+memo.id)
        Log.d("tak","content: "+memo.content)
        val intent=Intent(context,updateDialogActivity::class.java)
        intent.putExtra("id",memo.id)
        intent.putExtra("content",memo.content)
        startActivityForResult(intent,REQUEST_UPDATE_CODE)
    }


    override fun itemOnMoved(fromMemo: Memo, toMemo: Memo) {
        val temp=fromMemo.content
        fromMemo.content=toMemo.content
        toMemo.content=temp
        Log.d("tak","swap "+fromMemo.id +" "+toMemo.id)
        moved=true
        viewModel.update(fromMemo)
        viewModel.update(toMemo)
        Log.d("tak","swap2 "+fromMemo.id +" "+toMemo.id)


    }

    override fun itemOnSwipe(memo: Memo) {
        viewModel.delete(memo)
    }





}