package com.tt.lvruheng.eyepetizer.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.adapter.HotAdapter
import com.tt.lvruheng.eyepetizer.base.BaseFragment
import kotlinx.android.synthetic.main.hot_fragment.*

/**
 * Created by Owen on 2019/8/13
 */
class HotFragment:BaseFragment() {
    var mTabs= arrayListOf("周排行","月排行","总排行")
    var mTabssss= listOf("周排行","月排行","总排行").toMutableList()
    lateinit  var mFragments:MutableList<Fragment>
    val STRATEGY= arrayOf("weekly","monthly","historical")
    override fun getLayoutResources(): Int {
        return R.layout.hot_fragment
    }

    override fun initView() {
        var weekFragment= RankFragment()
        var weekBundle=Bundle()
        weekBundle.putString("strategy",STRATEGY[0])
        weekFragment.arguments=weekBundle

        var monthFragment=RankFragment()
        var monthBundle=Bundle()
        monthBundle.putString("strategy",STRATEGY[1])
        monthFragment.arguments=monthBundle

        var allFragment=RankFragment()
        var allBundle=Bundle()
        allBundle.putString("strategy",STRATEGY[2])
        allFragment.arguments=allBundle
        mFragments= ArrayList()
        mFragments.add(weekFragment)
        mFragments.add(monthFragment)
        mFragments.add(allFragment)
        vp_content.adapter=HotAdapter(fragmentManager,mFragments,mTabs)
        tabs.setupWithViewPager(vp_content)
    }

}