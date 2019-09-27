package com.tt.lvruheng.eyepetizer.base


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 *
 * Created by Owen on 2019/8/13
 */
abstract  class BaseFragment: Fragment() {
    var isFirst:Boolean = false
    var rootView:View? =null
    var isFragmentVisible:Boolean =false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(rootView==null){
            rootView=inflater?.inflate(getLayoutResources(),container,false)
        }


        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    abstract fun initView()


    abstract fun getLayoutResources():Int




}