package com.tt.lvruheng.eyepetizer.ui

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.adapter.FeedAdapter
import com.tt.lvruheng.eyepetizer.adapter.SearchAdapter
import com.tt.lvruheng.eyepetizer.mvp.bean.HotBean
import com.tt.lvruheng.eyepetizer.mvp.contract.ResultContract
import com.tt.lvruheng.eyepetizer.mvp.presenter.ResultPresenter
import kotlinx.android.synthetic.main.activity_result.*

/**
 * Created by Owen on 2019/9/26
 */
class ResultActivity:AppCompatActivity(),ResultContract.View {
    lateinit var mAdpter: FeedAdapter
    lateinit var keyWord: String
    var mList:ArrayList<HotBean.ItemListBean.DataBean>?=ArrayList()
    lateinit var mPresenter:ResultPresenter
    var isRefresh:Boolean=false
    var start: Int = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        keyWord=intent.getStringExtra(SearchAdapter.KEYWORD)
        setToolbar()
        recyclerView.layoutManager=LinearLayoutManager(this)
        mAdpter=FeedAdapter(this,mList)
        recyclerView.adapter=mAdpter
        mPresenter= ResultPresenter(this@ResultActivity,this@ResultActivity)
        mPresenter.requestData(keyWord,10)
        refreshLayout.setOnRefreshListener(object:SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                if(!isRefresh){
                    isRefresh=true
                    start=10
                    mPresenter.requestData(keyWord,10)
                }
            }
        })
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                var layoutManager=recyclerView?.layoutManager as LinearLayoutManager
                var lastPositon=layoutManager.findLastVisibleItemPosition()
                if(newState==RecyclerView.SCROLL_STATE_IDLE&&lastPositon==mList?.size?.minus(1)){
                    if(!isRefresh){
                        start = start.plus(10)
                        mPresenter.requestData(keyWord,start)
                    }

                }
            }

        })
    }
    private fun setToolbar() {
       setSupportActionBar(toolbar)
        var bar=supportActionBar
       // bar?.title="$keyWord"+"相关"
        bar?.title="'$keyWord'相关"
        bar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
           finish()
        }
    }

    override fun setData(bean: HotBean) {
        if(isRefresh){
            mList?.clear()
            isRefresh=false
            refreshLayout.isRefreshing=isRefresh
        }
      bean.itemList?.forEach {
          if(it.data!=null){
              mList?.add(it.data!!)
          }
      }
      mAdpter.notifyDataSetChanged()
    }
}