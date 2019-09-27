package com.tt.lvruheng.eyepetizer.network

import android.content.Context
import com.tt.lvruheng.eyepetizer.utils.NetworkUtils
import okhttp3.CacheControl

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Owen on 2019/8/15
 */
class CacheInterceptor(context: Context):Interceptor {
    val mContext=context
    override fun intercept(chain: Interceptor.Chain?): Response? {
        var request=chain?.request()
        if(NetworkUtils.isNetConneted(mContext)){
            val response=chain?.proceed(request)
            val maxAge=60
            val cacheControl=request?.cacheControl().toString()
            return  response?.newBuilder()?.removeHeader("Pragma")?.removeHeader("Cache-Control")?.header("Cache-Control","public, max-age"+maxAge)?.build()
        }else{
            request=request?.newBuilder()?.cacheControl(CacheControl.FORCE_CACHE)?.build()
            val response=chain?.proceed(request)
            val maxStale=60*60*24*3
            return response?.newBuilder()?.removeHeader("Pragma")?.removeHeader("Cahce-Control")?.header("Cache-Control","public ,only-if-cached,max-stale"+maxStale)?.build();
        }
    }
}