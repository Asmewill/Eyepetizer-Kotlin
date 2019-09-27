package com.tt.lvruheng.eyepetizer.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.mvp.bean.HotBean
import com.tt.lvruheng.eyepetizer.mvp.bean.VideoBean
import com.tt.lvruheng.eyepetizer.ui.VideoDetailActivity
import com.tt.lvruheng.eyepetizer.utils.ImageLoadUtils
import java.text.SimpleDateFormat

/**
 * Created by Owen on 2019/9/26
 */
class FeedAdapter(mContext:Context,list:ArrayList<HotBean.ItemListBean.DataBean>?):RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {
    var list:ArrayList<HotBean.ItemListBean.DataBean>?=null
    var mContext :Context?=null
    var inflate:LayoutInflater= LayoutInflater.from(mContext)
    init {
        this.list=list
        this.mContext=mContext
    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int):FeedAdapter.FeedViewHolder {
        return FeedViewHolder(mContext!!,inflate.inflate(R.layout.item_feed_result,parent,false))
    }

    override fun getItemCount(): Int {
       return list?.size?:0
    }

    override fun onBindViewHolder(holder: FeedAdapter.FeedViewHolder?, position: Int) {
        var bean=list?.get(position)
        var smf=SimpleDateFormat("YYYY-MM-dd")
        var date=smf.format(list?.get(position)?.releaseTime)
        list?.get(position)?.cover?.feed.let {
            ImageLoadUtils.display(mContext!!,holder?.iv_photo,it?:"")
        }
        holder?.tv_title?.text=list?.get(position)?.title
        holder?.tv_detail?.text=list?.get(position)?.category+"/"+"$date"
        holder?.rl_content?.setOnClickListener {
            var videoBean= VideoBean()
            videoBean.title=bean?.title
            videoBean.feed=bean?.cover?.feed
            videoBean.blurred=bean?.cover?.blurred
            videoBean.category=bean?.category
            videoBean.collect=bean?.consumption?.collectionCount
            videoBean.duration=bean?.duration
            videoBean.playUrl=bean?.playUrl
            videoBean.description=bean?.description
            videoBean.reply=bean?.consumption?.replyCount
            videoBean.share=bean?.consumption?.shareCount
            videoBean.time=System.currentTimeMillis()
            var intents= Intent(mContext, VideoDetailActivity::class.java)
            intents.putExtra(HomeAdapter.VIDEOBEAN,videoBean)
            mContext?.let {
                it.startActivity(intents)
            }

        }
    }

    class FeedViewHolder(mContext:Context,itemView:View) :RecyclerView.ViewHolder(itemView){
        var iv_photo:ImageView= itemView.findViewById(R.id.iv_photo) as ImageView
        var tv_title:TextView= itemView.findViewById(R.id.tv_title) as TextView
        var tv_detail:TextView= itemView.findViewById(R.id.tv_detail) as TextView
        var rl_content:RelativeLayout=itemView.findViewById(R.id.rl_content) as RelativeLayout

    }
}