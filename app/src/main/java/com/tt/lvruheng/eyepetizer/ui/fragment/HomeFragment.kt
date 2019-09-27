package com.tt.lvruheng.eyepetizer.ui.fragment

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.adapter.HomeAdapter
import com.tt.lvruheng.eyepetizer.base.BaseFragment
import com.tt.lvruheng.eyepetizer.mvp.contract.HomeContract
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import com.tt.lvruheng.eyepetizer.mvp.presenter.HomePresenter
import kotlinx.android.synthetic.main.home_fragment.*
import java.util.regex.Pattern

/**
 * Created by Owen on 2019/8/13
 */
class HomeFragment:BaseFragment(),HomeContract.View ,SwipeRefreshLayout.OnRefreshListener{

    var mPresenter: HomePresenter?=null
    var mIsRefresh:Boolean =false
    var mAdapter:HomeAdapter?=null
    var mList=ArrayList<HomeBean.Issue.Item>()
    var date:String? =null

    override fun getLayoutResources(): Int {
        return R.layout.home_fragment
    }


    override fun initView() {
        mPresenter = HomePresenter(context, this)
        recyclerView.layoutManager=LinearLayoutManager(activity)
        mList=ArrayList<HomeBean.Issue.Item>()
        mAdapter=HomeAdapter(context,mList)
        recyclerView.adapter=mAdapter
        refreshLayout.setOnRefreshListener(this)
        recyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                var layoutManager=recyclerView?.layoutManager as LinearLayoutManager
                var lastPositon=layoutManager.findLastVisibleItemPosition()
                if(newState==RecyclerView.SCROLL_STATE_IDLE&&lastPositon==mList.size-1){
                      if(date!=null){
                          mPresenter?.moreData(date)
                      }
                }
            }
        })
        onRefresh()
    }


    override fun onRefresh() {
        if(!mIsRefresh){
            mIsRefresh=true
            mPresenter?.start()
        }

    }

    override fun setData(bean: HomeBean) {
        //context.showToast("get data successful")
        val regEx="[^0-9]"
        val p=Pattern.compile(regEx)
        val m=p.matcher(bean.nextPageUrl)
        date=m.replaceAll("").substring(1,m.replaceAll("").length-1)
        if(mIsRefresh){
            mIsRefresh=false
            refreshLayout.isRefreshing=false
            if(mList.size>0){
                mList.clear()
            }
        }
        bean.issueList?.flatMap { it.itemList }.filter { it.type.equals("video") }.forEach { mList.add(it) }
        mAdapter?.notifyDataSetChanged()
    }

}