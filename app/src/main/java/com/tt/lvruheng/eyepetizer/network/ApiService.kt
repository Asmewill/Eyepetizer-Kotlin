package com.tt.lvruheng.eyepetizer.network

import com.tt.lvruheng.eyepetizer.mvp.bean.FindBean
import com.tt.lvruheng.eyepetizer.mvp.bean.HotBean
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Owen on 2019/9/20
 */
interface ApiService {
    companion object{
        val BASE_URL:String="http://baobab.kaiyanapp.com/api/"
        //get() ="http://baobab.kaiyanapp.com/api/"
//        get() {
//            return "http://baobab.kaiyanapp.com/api/"
//        }
    }
    @GET("v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun getHomeData():Observable<HomeBean>
    //获取首页第一页之后的数据  ?date=1499043600000&num=2
    @GET("v2/feed")
    fun getHomeMoreData(@Query("date") date :String, @Query("num") num :String) : Observable<HomeBean>
    //获取发现频道信息
    @GET("v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun getFindData() : Observable<MutableList<FindBean>>
    //获取热门排行信息
    @GET("v3/ranklist")
    fun getHotData(@Query("num") num :Int,@Query("strategy") strategy :String,
                   @Query("udid") udid :String,@Query("vc") vc :Int) : Observable<HotBean>


    //获取关键词搜索相关信息
    @GET("v1/search")
    fun getSearchData(@Query("num") num :Int,@Query("query") query :String,
                      @Query("start") start :Int) : Observable<HotBean>
}