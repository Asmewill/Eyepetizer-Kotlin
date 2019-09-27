package com.tt.lvruheng.eyepetizer.mvp.presenter

import android.content.Context
import com.tt.lvruheng.eyepetizer.mvp.bean.HotBean
import com.tt.lvruheng.eyepetizer.mvp.contract.ResultContract
import com.tt.lvruheng.eyepetizer.mvp.model.ResultModel
import com.tt.lvruheng.eyepetizer.utils.applySchedulers
import io.reactivex.Observable

/**
 * Created by Owen on 2019/9/26
 */
class ResultPresenter(mContext:Context,mView:ResultContract.View) :ResultContract.Presenter{
    var mContext:Context?=null
    var mView:ResultContract.View?=null
    val mModel by lazy {
        ResultModel()
    }
    init {
        this.mContext=mContext
        this.mView=mView
    }

    override fun requestData(query: String, start: Int) {
        var observable:Observable<HotBean>?=mModel.loadData(mContext!!,query,start)
        observable?.applySchedulers()?.subscribe {
            mView?.setData(it)
        }
    }
    override fun start() {

    }
}