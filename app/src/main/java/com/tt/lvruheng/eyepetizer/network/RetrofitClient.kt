package com.tt.lvruheng.eyepetizer.network

import android.content.Context
import android.util.Log
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by Owen on 2019/8/14
 */
class RetrofitClient private constructor(context: Context,baseUrl:String){//构造方法私有
    var httpCacheDirectory: File?=null
    var mContext:Context =context
    var cache:Cache?=null
    var okHttpClient:OkHttpClient?=null
    var retrofit :Retrofit?=null
    var url=baseUrl
    val DEFAULT_TIMEOUT : Long = 20
    init {
        if(httpCacheDirectory==null){
            httpCacheDirectory= File(mContext.cacheDir,"app_cache")
        }
        try {
            if(cache==null){
                cache=Cache(httpCacheDirectory,10*1024*1024)
            }
        }catch (e:Exception){
              Log.e("OKHttp","Could not create http cache",e)
        }
        okHttpClient=OkHttpClient.Builder()
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(cache)
                .addInterceptor(CacheInterceptor(context))
                .addNetworkInterceptor(CacheInterceptor(context))
                .connectTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
                .build()
        retrofit=Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build()

    }
    companion object{
        @Volatile
        var instance:RetrofitClient?=null
        fun getInstance(context :Context,baseUrl:String):RetrofitClient{
            if(instance==null){
                synchronized(RetrofitClient::class){
                    if(instance==null){
                        instance=RetrofitClient(context,baseUrl)
                    }
                }
            }
            return instance!!
        }
    }
    fun <T> create(service:Class<T>?):T?{
        if(service==null){
            throw  RuntimeException("Api service is null!")
        }
        return retrofit?.create(service)
    }
}