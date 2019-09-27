package com.tt.lvruheng.eyepetizer.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.shuyu.gsyvideoplayer.GSYVideoPlayer
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.adapter.HomeAdapter
import com.tt.lvruheng.eyepetizer.mvp.bean.VideoBean
import com.tt.lvruheng.eyepetizer.utils.VideoListener
import kotlinx.android.synthetic.main.activity_video_detail.*

/**
 * Created by Owen on 2019/9/27
 */
class VideoDetailActivity: AppCompatActivity() {
    private var isPlay: Boolean=false
    var mContext:Context=this
     var orientationUtils: OrientationUtils?=null
    lateinit  private var videoBean: VideoBean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
        videoBean= intent.getSerializableExtra(HomeAdapter.VIDEOBEAN) as VideoBean
        initView()
        prepareVideo()
    }

    private fun prepareVideo() {
        gsy_player.setUp(videoBean.playUrl,false,null,null)
        gsy_player.titleTextView.visibility=View.GONE
        gsy_player.backButton.visibility= View.VISIBLE
        gsy_player.setIsTouchWiget(true)
        orientationUtils = OrientationUtils(this, gsy_player)
        //关闭自动旋转
        gsy_player.isRotateViewAuto = false
        gsy_player.isLockLand = false
        gsy_player.isShowFullAnimation = false
        gsy_player.isNeedLockFull = true
        gsy_player.fullscreenButton.setOnClickListener {
            orientationUtils?.resolveByClick()
            gsy_player.startWindowFullscreen(mContext,true,true)
        }
        gsy_player.setStandardVideoAllCallBack(object: VideoListener(){
            override fun onPrepared(url: String?, vararg objects: Any?) {
                super.onPrepared(url, *objects)
                orientationUtils?.isEnable=true
                isPlay=true
            }

            override fun onAutoComplete(url: String?, vararg objects: Any?) {
                super.onAutoComplete(url, *objects)
            }

            override fun onClickStartError(url: String?, vararg objects: Any?) {
                super.onClickStartError(url, *objects)
            }

            override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                super.onQuitFullscreen(url, *objects)
                orientationUtils?.let {
                    orientationUtils?.backToProtVideo()
                }
            }
        })
        gsy_player.setLockClickListener { view, lock ->
            orientationUtils?.isEnable=!lock
        }
        gsy_player.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initView() {
        tv_video_title.text=videoBean.title
        tv_video_time.text=videoBean.category+"/"
        tv_video_desc.text=videoBean.description
        tv_video_favor.text=videoBean.collect.toString()
        tv_video_share.text=videoBean.share.toString()
        tv_video_reply.text=videoBean.reply.toString()
        tv_video_download.text=videoBean.reply.toString()
        Glide.with(this).load(videoBean.blurred?:"")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.mipmap.bg)
                .error(R.mipmap.bg)
                .crossFade().into(iv_bottom_bg)

    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoPlayer.releaseAllVideos()
    }
}