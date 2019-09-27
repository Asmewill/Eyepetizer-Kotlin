package com.tt.lvruheng.eyepetizer.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.mvp.bean.VideoBean
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import com.tt.lvruheng.eyepetizer.ui.VideoDetailActivity
import com.tt.lvruheng.eyepetizer.utils.ImageLoadUtils

/**
 * Created by Owen on 2019/8/14
 */
class HomeAdapter(context: Context, list: MutableList<HomeBean.Issue.Item>?) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    var context: Context? = null
    var list: MutableList<HomeBean.Issue.Item>? = null
    var inflate: LayoutInflater? = null
    companion object{
        val VIDEOBEAN:String="videoBean"
    }
    init {
        this.context = context
        this.list = list
        this.inflate = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(holder: HomeViewHolder?, position: Int) {
        var bean = list?.get(position)
        var title = bean?.data?.title
        var category = bean?.data?.category
        var minute = bean?.data?.duration?.div(60)
        var second = bean?.data?.duration?.minus((minute?.times(60)!!))//minus减去
        var realMinute: String
        var realSecond: String
        if (minute!! < 10) {
            realMinute = "0" + minute
        } else {
            realMinute = minute.toString()
        }
        if (second!! < 10) {
            realSecond = "0" + second
        } else {
            realSecond = second.toString()
        }
        holder?.tv_title?.text=title
        holder?.tv_detail?.text="发布于$category/$realMinute:$realSecond"
       if(bean?.data?.author!=null){
           holder?.iv_user?.visibility=View.VISIBLE
           ImageLoadUtils.display(context!!,holder?.iv_user,bean.data.author.icon)
       }else{
           holder?.iv_user?.visibility=View.GONE
       }
        ImageLoadUtils.display(context!!,holder?.iv_photo,bean?.data?.cover?.feed+"")
        holder?.itemView?.setOnClickListener {
               var videoBean=VideoBean()
              videoBean.title=bean?.data?.title
              videoBean.feed=bean?.data?.cover?.feed
              videoBean.blurred=bean?.data?.cover?.blurred
              videoBean.category=bean?.data?.category
              videoBean.collect=bean?.data?.consumption?.collectionCount
              videoBean.duration=bean?.data?.duration
              videoBean.playUrl=bean?.data?.playUrl
              videoBean.description=bean?.data?.description
              videoBean.reply=bean?.data?.consumption?.replyCount
              videoBean.share=bean?.data?.consumption?.shareCount
              videoBean.time=System.currentTimeMillis()
             var intents=Intent(context,VideoDetailActivity::class.java)
             intents.putExtra(VIDEOBEAN,videoBean)
             context?.let {
                it.startActivity(intents)
             }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HomeViewHolder {
        return HomeViewHolder(inflate?.inflate(R.layout.item_home, parent, false), context!!)//context为null时报错
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0  //list=null时 为0
    }

    class HomeViewHolder(itemView: View?, context: Context) : RecyclerView.ViewHolder(itemView) {
        var tv_detail: TextView? = null
        var tv_title: TextView? = null
        var iv_user: ImageView? = null
        var iv_photo: ImageView? = null

        init {
            tv_detail = itemView?.findViewById(R.id.tv_detail) as TextView?
            tv_title = itemView?.findViewById(R.id.tv_title) as TextView?
            iv_user = itemView?.findViewById(R.id.iv_user) as ImageView?
            iv_photo = itemView?.findViewById(R.id.iv_photo) as ImageView?
        }
    }
}