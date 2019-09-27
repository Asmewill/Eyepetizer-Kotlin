package com.tt.lvruheng.eyepetizer.ui

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.ui.fragment.*
import com.tt.lvruheng.eyepetizer.utils.showToast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * Created by Owen on 2019/8/9
 */
class MainActivity : AppCompatActivity() , View.OnClickListener{

    lateinit var searchFragment: SearchFragment
    var  mExitTime:Long=0
    var toast:Toast?=null
    var homeFragment: HomeFragment? =null
    var findFragment:FindFragment?=null
    var hotFragment:HotFragment?=null
    var mineFragment:MineFragment?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRadioButton()
        initToolbar()
        initFragment(savedInstanceState)

    }

    private fun initToolbar() {
        var today:String=getToday()
        tv_bar_title.text=today
        tv_bar_title.typeface= Typeface.createFromAsset(this.assets,"fonts/Lobster-1.4.otf")
        
    }

    private fun getToday(): String {
        var list=arrayOf("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday")
        var date = Date()
        var calendar:Calendar= Calendar.getInstance()
        calendar.time=date
        var index:Int=calendar.get(Calendar.DAY_OF_WEEK)-1
        if(index<0){
            index=0
        }
        return list[index]
    }


    private fun setRadioButton() {
        rb_home.isChecked=true
        rb_home.setTextColor(resources.getColor(R.color.black))
        rb_home.setOnClickListener(this)
        rb_find.setOnClickListener(this)
        rb_hot.setOnClickListener(this)
        rb_mine.setOnClickListener(this)
        iv_search.setOnClickListener(this)

    }

    private fun initFragment(savedInstanceState: Bundle?) {
        if(savedInstanceState!=null){
            val mFragments:List<android.support.v4.app.Fragment> =supportFragmentManager.fragments
            for(item in mFragments){
                if(item is HomeFragment){
                    homeFragment=item
                }
                if(item is FindFragment){
                    findFragment=item
                }
                if(item is HotFragment){
                    hotFragment=item
                }
                if(item is MineFragment){
                    mineFragment=item
                }
            }
        }else{
            homeFragment= HomeFragment()
            findFragment= FindFragment()
            hotFragment= HotFragment()
            mineFragment= MineFragment()
            val fragmentTrans =supportFragmentManager.beginTransaction()
            fragmentTrans.add(R.id.fl_content,homeFragment)
            fragmentTrans.add(R.id.fl_content,findFragment)
            fragmentTrans.add(R.id.fl_content,hotFragment)
            fragmentTrans.add(R.id.fl_content,mineFragment)
            fragmentTrans.commit()
        }
        supportFragmentManager.beginTransaction().show(homeFragment)
                .hide(findFragment)
                .hide(hotFragment)
                .hide(mineFragment)
                .commit()

    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_search->{
                searchFragment=SearchFragment()
                searchFragment.show(fragmentManager,SEARCH_TAG)
            }
            R.id.rb_home->{
                clearState()
                rb_home.isChecked=true
                rb_home.setTextColor(resources.getColor(R.color.black))
                tv_bar_title.text=getToday()
                tv_bar_title.visibility=View.VISIBLE
                iv_search.setImageResource(R.drawable.icon_search)
                supportFragmentManager.beginTransaction().show(homeFragment).
                        hide(findFragment).hide(hotFragment).hide(mineFragment).commit()

            }
            R.id.rb_find->{
                clearState()
                rb_find.isChecked=true
                rb_find.setTextColor(resources.getColor(R.color.black))
                tv_bar_title.text="Discover"
                tv_bar_title.visibility=View.VISIBLE
                supportFragmentManager.beginTransaction().show(findFragment).
                        hide(homeFragment).hide(hotFragment).hide(mineFragment).commit()
            }
            R.id.rb_hot->{
                clearState()
                rb_hot.isChecked=true
                rb_hot.setTextColor(resources.getColor(R.color.black))
                tv_bar_title.text="Ranking"
                tv_bar_title.visibility=View.VISIBLE
                supportFragmentManager.beginTransaction().show(hotFragment).
                        hide(homeFragment).hide(findFragment).hide(mineFragment).commit()

            }
            R.id.rb_mine->{
                clearState()
                rb_mine.isChecked=true
                rb_mine.setTextColor(resources.getColor(R.color.black))
                tv_bar_title.visibility=View.VISIBLE
                tv_bar_title.text="Mine"
                supportFragmentManager.beginTransaction().show(mineFragment).
                        hide(homeFragment).hide(findFragment).hide(hotFragment).commit()
            }

        }



    }

    private fun clearState() {
        rg_root.clearCheck()
        rb_home.setTextColor(resources.getColor(R.color.gray))
        rb_find.setTextColor(resources.getColor(R.color.gray))
        rb_hot.setTextColor(resources.getColor(R.color.gray))
        rb_mine.setTextColor(resources.getColor(R.color.gray))
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(System.currentTimeMillis().minus(mExitTime)<3000){
                finish()
                toast!!.cancel()//如果toast为null,会报错
            }else{
                mExitTime=System.currentTimeMillis()
                toast= showToast("再按一次退出应用")
            }
            return  true
        }
        return super.onKeyDown(keyCode, event)
    }
}