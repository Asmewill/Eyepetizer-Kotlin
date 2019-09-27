package com.tt.lvruheng.eyepetizer.ui.fragment

import android.content.Intent
import android.text.TextUtils
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.adapter.FindAdapter
import com.tt.lvruheng.eyepetizer.adapter.SearchAdapter
import com.tt.lvruheng.eyepetizer.base.BaseFragment
import com.tt.lvruheng.eyepetizer.mvp.bean.FindBean
import com.tt.lvruheng.eyepetizer.mvp.contract.FindContract
import com.tt.lvruheng.eyepetizer.mvp.presenter.FindPresenter
import com.tt.lvruheng.eyepetizer.ui.ResultActivity
import kotlinx.android.synthetic.main.find_fragment.*

/**
 * Created by Owen on 2019/8/13
 */
class FindFragment:BaseFragment(),FindContract.View {

    private var mAdapter: FindAdapter?=null
    private var mPresenter: FindPresenter?=null
    var mList:MutableList<FindBean>?=ArrayList()

    override fun getLayoutResources(): Int {
        return R.layout.find_fragment
    }

    override fun initView() {
        mPresenter=FindPresenter(context,this)
        mPresenter?.start()
        mAdapter=FindAdapter(context,mList!!)
        gv_find.adapter=mAdapter
        gv_find.setOnItemClickListener{
            parent, view, position, id ->
            if(!TextUtils.isEmpty(mList?.get(position)?.name)){
                var keyWord=mList?.get(position)?.name!!
                var intent=Intent(context,ResultActivity::class.java)
                intent.putExtra(SearchAdapter.KEYWORD,keyWord)
                startActivity(intent)
            }
        }
    }
    override fun setData(beans: MutableList<FindBean>) {
        mAdapter?.mList=beans
        mList=beans
        mAdapter?.notifyDataSetChanged()
    }
}