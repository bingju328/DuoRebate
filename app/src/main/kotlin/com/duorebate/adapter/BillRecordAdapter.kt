package com.duorebate.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.duorebate.R
import com.duorebate.utils.dip2px
import com.duorebate.utils.getScreenWidth
import com.pulltorefresh.RecyclerBaseAdapter
import kotlinx.android.synthetic.main.item_gd_goods.view.*

/**
 * Created by bingju on 2017/7/6.
 */
class BillRecordAdapter(context: Context): RecyclerBaseAdapter<String>() {
    init {
        this.context = context
        layoutInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        viewHolder = ViewHolder(layoutInflater.inflate(R.layout.item_gd_goods, parent, false))
        return viewHolder
    }
    override fun onBindViewHolders(holder: RecyclerView.ViewHolder?, position: Int) {
        //计算控件宽度
        val screenWith = getScreenWidth(context as Activity)
        val marginLeft = dip2px(context,0f)
//        val marginRight = dip2px(context,0f)
        val marginCenter = 8
        val itemContentWidth = screenWith/2-marginCenter/2-marginLeft
        val ll = LinearLayout.LayoutParams(itemContentWidth, itemContentWidth)
        holder!!.itemView.iv_icon_gradview.layoutParams = ll
        val rl_content = LinearLayout.LayoutParams(
                itemContentWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
        if (position == 0) {
            rl_content.setMargins(marginLeft, dip2px(context, 4f), marginLeft / 2, dip2px(context, 4f))
        } else if (position == 1) {
            rl_content.setMargins(marginLeft / 2, dip2px(context, 4f), marginLeft, dip2px(context, 4f))
        } else if (position % 2 == 0) {
            rl_content.setMargins(marginLeft, 0, marginLeft / 2, dip2px(context, 4f))
        } else {
            rl_content.setMargins(marginLeft / 2, 0, marginLeft, dip2px(context, 4f))
        }
        holder.itemView.ll_item_content_gradview.layoutParams = rl_content
    }

    override fun getItemCount(): Int {
        return 9
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
}