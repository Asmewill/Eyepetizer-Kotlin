package com.tt.lvruheng.eyepetizer.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayoutManager
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.ui.ResultActivity
import com.tt.lvruheng.eyepetizer.utils.showToast

/**
 * Created by Owen on 2019/9/25
 */
class SearchAdapter(mContext: Context,list:ArrayList<String>):RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){
    var mContext:Context?=mContext
    var list:ArrayList<String>?=list
    var inflate:LayoutInflater?= LayoutInflater.from(mContext)
    var mDialogDismiss:OnDialogDismiss?=null
    companion object{
        val KEYWORD:String="keyWord"
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SearchAdapter.SearchViewHolder {
         return  SearchViewHolder(mContext!!,inflate?.inflate(R.layout.item_search,parent,false)!!)
    }

    override fun getItemCount(): Int {
        return list?.size?:0
    }

    override fun onBindViewHolder(holder:SearchAdapter.SearchViewHolder?, position: Int) {
        holder?.tv_title?.text=list?.get(position)
        val params=holder?.tv_title?.layoutParams
        if(params is FlexboxLayoutManager.LayoutParams){
            params.flexGrow=1.0f
        }
        holder?.itemView?.setOnClickListener {
            var keyWord=list?.get(position)
            var intent=Intent(mContext, ResultActivity::class.java)
            intent.putExtra(KEYWORD,keyWord)
            mContext?.startActivity(intent)
            mContext?.showToast(keyWord?:"xxx")
            mDialogDismiss?.onDismiss()
        }

    }

    class SearchViewHolder(mContext:Context,itemView:View):RecyclerView.ViewHolder(itemView){
          var tv_title:TextView = itemView?.findViewById(R.id.tv_title) as TextView

    }
    interface  OnDialogDismiss{
        fun onDismiss()
    }
    fun setOnDialogDismissListener(mDialogDismiss:OnDialogDismiss){
        if(mDialogDismiss!=null){
          this.mDialogDismiss=mDialogDismiss
        }
    }
}