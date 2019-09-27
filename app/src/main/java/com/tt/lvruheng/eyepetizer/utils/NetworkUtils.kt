package com.tt.lvruheng.eyepetizer.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Owen on 2019/8/15
 */
object NetworkUtils {
    fun isNetConneted(context: Context):Boolean{
        var connectManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectManager.activeNetworkInfo
        if(networkInfo==null){
            return false
        }else{
            return networkInfo.isAvailable&&networkInfo.isConnected
        }
    }

}