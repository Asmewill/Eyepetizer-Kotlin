package com.tt.lvruheng.eyepetizer.mvp.contract

import com.tt.lvruheng.eyepetizer.base.BasePresenter
import com.tt.lvruheng.eyepetizer.base.BaseView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean

/**
 * Created by Owen on 2019/8/14
 */
interface HomeContract {
    interface View:BaseView<Presenter>{
        fun setData(bean:HomeBean)
    }
    interface Presenter:BasePresenter{
        fun requestData()
    }
}