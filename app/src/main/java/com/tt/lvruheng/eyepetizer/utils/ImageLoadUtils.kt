package com.tt.lvruheng.eyepetizer.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tt.lvruheng.eyepetizer.R

/**
 * Created by Owen on 2019/8/14
 */
class ImageLoadUtils {
    companion object{
        fun display(context: Context,imageView: ImageView?,url:String){
            if(imageView==null){
               throw  IllegalArgumentException("argument error")
            }
            Glide.with(context).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image_loading)
                    .error(R.drawable.ic_empty_picture)
                    .crossFade().into(imageView)

        }
    }

}