package com.tt.lvruheng.eyepetizer.mvp.model

import android.content.Context
import com.tt.lvruheng.eyepetizer.mvp.bean.HotBean
import com.tt.lvruheng.eyepetizer.network.ApiService
import com.tt.lvruheng.eyepetizer.network.RetrofitClient
import io.reactivex.Observable

/**
 * Created by Owen on 2019/9/26
 */
class ResultModel {
    fun loadData(context:Context,query:String,start:Int):Observable<HotBean>?{
       var retrofitClient=RetrofitClient.getInstance(context,ApiService.BASE_URL)
       var apiService=retrofitClient.create(ApiService::class.java)
       return  apiService?.getSearchData(10,query,start)
    }
}