package com.tt.lvruheng.eyepetizer.mvp.contract

import com.tt.lvruheng.eyepetizer.base.BasePresenter
import com.tt.lvruheng.eyepetizer.base.BaseView
import com.tt.lvruheng.eyepetizer.mvp.bean.HotBean

/**
 * Created by Owen on 2019/9/26
 */
class ResultContract {
    interface View:BaseView<Presenter>{
       fun setData(bean:HotBean)
    }

    interface Presenter:BasePresenter{
        fun requestData(query:String,start:Int)
    }
}