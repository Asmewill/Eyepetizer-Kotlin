package com.tt.lvruheng.eyepetizer.mvp.model

import android.content.Context
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import com.tt.lvruheng.eyepetizer.network.ApiService
import com.tt.lvruheng.eyepetizer.network.RetrofitClient
import io.reactivex.Observable

/**
 * Created by Owen on 2019/8/14
 */
class HomeModel {
    fun  loadData(context: Context,isFirst:Boolean,data:String):Observable<HomeBean>?{
        val retrofitClient=RetrofitClient.getInstance(context,ApiService.BASE_URL)
        val apiService=retrofitClient.create(ApiService::class.java)
        when(isFirst){
          true->return apiService?.getHomeData()
          false ->return apiService?.getHomeMoreData(data,"2")
        }
    }
}