package com.tt.lvruheng.eyepetizer.mvp.bean

import java.io.Serializable

/**
 * Created by Owen on 2019/9/23
 */
class VideoBean : Serializable {
    var feed: String? = null
    var title: String? = null
    var description: String? = null
    var duration: Int? = null
    var playUrl: String? = null
    var category: String? = null
    var blurred: String? = null
    var collect: Int? = null
    var share: Int? = null
    var reply: Int? = null
    var time: Long? = null
}
