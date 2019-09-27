package com.tt.lvruheng.eyepetizer.mvp.presenter

import android.content.Context
import com.tt.lvruheng.eyepetizer.mvp.contract.HomeContract
import com.tt.lvruheng.eyepetizer.mvp.model.HomeModel
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import com.tt.lvruheng.eyepetizer.utils.applySchedulers
import io.reactivex.Observable

/**
 * Created by Owen on 2019/8/14
 */
class HomePresenter(context: Context,view:HomeContract.View):HomeContract.Presenter {
    var mContext:Context?=null
    var mView:HomeContract.View?=null
    val mModel:HomeModel by lazy {
        HomeModel()
    }
    init {
        mView=view
        mContext=context
    }

    override fun requestData() {
        //let 用法 1在函数体内使用it替代object对象去访问其公有的属性和方法
        //let 用法 2另一种用途 判断object为null的操作,表示mContext不为null的条件下，才会去执行let函数体
        val observable:Observable<HomeBean>?=mContext?.let { mModel.loadData(it,true,"0") }
        observable?.applySchedulers()?.subscribe {it
            mView?.setData(it)
        }

    }

    override fun start() {
         requestData()
    }
    fun moreData(data:String?){
         val observable:Observable<HomeBean>?=mContext?.let { mModel.loadData(it,false,data!!) }
         observable?.applySchedulers()?.subscribe {
              mView?.setData(it)
         }
    }

}