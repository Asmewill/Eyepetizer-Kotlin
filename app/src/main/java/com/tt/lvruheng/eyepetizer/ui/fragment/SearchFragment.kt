package com.tt.lvruheng.eyepetizer.ui.fragment

import android.app.DialogFragment
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.DefaultItemAnimator
import android.text.TextUtils
import android.view.*
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.adapter.SearchAdapter
import com.tt.lvruheng.eyepetizer.ani.CircularRevealAnim
import com.tt.lvruheng.eyepetizer.ui.ResultActivity
import com.tt.lvruheng.eyepetizer.utils.KeyBoardUtils
import com.tt.lvruheng.eyepetizer.utils.showToast
import kotlinx.android.synthetic.main.search_fragment.*

/**
 * Created by Owen on 2019/9/25
 */
const val SEARCH_TAG = "SearchFragment"
class SearchFragment:DialogFragment(),View.OnClickListener {
  lateinit  private var mAni: CircularRevealAnim
    var list : MutableList<String> = arrayListOf("脱口秀","城会玩","666","笑cry","漫威",
            "清新","匠心","VR","心理学","舞蹈","品牌广告","粉丝自制","电影相关","萝莉","魔性"
            ,"第一视角","教程","毕业设计","奥斯卡","燃","冰与火之歌","温情","线下campaign","公益").toMutableList()
    lateinit  var mAdapter: SearchAdapter
    lateinit  var rootView:View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle)

    }

    override fun onStart() {
        super.onStart()
        val window=dialog.window
        val metrics=resources.displayMetrics
        val width=(metrics.widthPixels*0.98).toInt()
        window.setLayout(width,WindowManager.LayoutParams.MATCH_PARENT)
        window.setGravity(Gravity.TOP)
        window.setWindowAnimations(R.style.DialogEmptyAnimation)
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        rootView= inflater?.inflate(R.layout.search_fragment,container,false)!!
        return rootView
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter=SearchAdapter(context, list as ArrayList<String>)
        val manager= FlexboxLayoutManager()
        manager.flexDirection= FlexDirection.ROW
        manager.flexWrap= FlexWrap.WRAP
        recyclerView.layoutManager=manager
        recyclerView.itemAnimator= DefaultItemAnimator()
        recyclerView.adapter=mAdapter
        iv_search_search.setOnClickListener(this)
        iv_search_back.setOnClickListener(this)
        mAdapter.setOnDialogDismissListener(object :SearchAdapter.OnDialogDismiss{
            override fun onDismiss() {
                    dismiss()
            }
        })
        //动画
        mAni=CircularRevealAnim()
        mAni.setAnimListener(object :CircularRevealAnim.AnimListener{
            override fun onHideAnimationEnd() {
                et_search_keyword.setText("")
                dismiss()
            }

            override fun onShowAnimationEnd() {
                if (isVisible) {
                    KeyBoardUtils.openKeyboard(activity, et_search_keyword)
                }
            }

        })
        //开始动画
        iv_search_search.post {
            mAni.show(iv_search_search,rootView)
        }

        dialog.setOnKeyListener(object :DialogInterface.OnKeyListener{
            override fun onKey(dialog: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_UP) {
                    mAni.hide(iv_search_search,rootView)
                } else if (keyCode == KeyEvent.KEYCODE_ENTER && event?.action == KeyEvent.ACTION_DOWN) {
                    search()
                }
                return  false
            }

        })
       isCancelable=false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_search_search->{
              search()
            }
            R.id.iv_search_back->{
                 mAni.hide(iv_search_search,rootView)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun search(){
        var keyWord:String=et_search_keyword.text.toString()
        if(!TextUtils.isEmpty(keyWord)){
            mAni.hide(iv_search_search,rootView)
            var intent= Intent(context, ResultActivity::class.java)
            intent.putExtra(SearchAdapter.KEYWORD,keyWord)
            startActivity(intent)

        }else{
            context.showToast("请输入关键字")
        }
    }
}