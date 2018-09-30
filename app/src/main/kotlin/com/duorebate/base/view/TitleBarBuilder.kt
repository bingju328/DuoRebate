package com.duorebate.base.view

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.include_layout_titlebar.*
import kotlinx.android.synthetic.main.include_layout_titlebar.view.*

/**
 * 标题栏构造器,使用方法 new TitleBarBuilder().setMethod().setMethod()......
 * <p/>
 * 统一格式为标题文字,左右各自是文字/图片按钮
 * 按钮都默认不显示,只有在你调用setLeftText时才会显示左侧按钮文字,图片同理
 * 图片或文字的点击事件都用Left/RightOnClickListener
 */
class TitleBarBuilder{
    private var rootView: View? = null
    private lateinit var tvTitle: TextView
    private lateinit var tvLeft: TextView
    private lateinit var tvRight: TextView
    private lateinit var ivLeft: ImageView
    private lateinit var ivRight: ImageView

    fun getRootView(): View? = rootView
    fun getTvTitle(): TextView = tvTitle
    fun getIvLeft(): ImageView = ivLeft
    fun getIvRight(): ImageView = ivRight
    fun getTvLeft(): TextView = tvLeft
    fun getTvRight(): TextView = tvRight

    /**
     * Activity中使用这个构造方法
     */
    constructor(context: Activity) {
        rootView = context.titlebar
        if (rootView == null) return
        tvTitle = context.titlebar_tv
        ivLeft = context.titlebar_iv_left
        ivRight = context.titlebar_iv_right
        tvLeft = context.titlebar_tv_left
        tvRight = context.titlebar_tv_right
    }

    /**
     * Fragment中使用这个构造方法
     */
    constructor(context: View) {
        rootView = context.titlebar
        if (rootView == null) return
        tvTitle = rootView!!.titlebar_tv
        ivLeft = rootView!!.titlebar_iv_left
        ivRight = rootView!!.titlebar_iv_right
        tvLeft = rootView!!.titlebar_tv_left
        tvRight = rootView!!.titlebar_tv_right
    }

    // title
    fun setTitleBgRes(resid: Int): TitleBarBuilder {
        rootView!!.setBackgroundResource(resid)
        return this@TitleBarBuilder
    }
    fun setTitleBgColor(resid: Int): TitleBarBuilder {
        rootView!!.setBackgroundColor(resid)
        return this@TitleBarBuilder
    }
    fun setTitleText(text: String): TitleBarBuilder {
        tvTitle.visibility = if (TextUtils.isEmpty(text)) View.GONE else View.VISIBLE
        tvTitle.text = text
        return this@TitleBarBuilder
    }
    fun setTitleTextSize(textSize: Float): TitleBarBuilder {
        tvTitle.visibility = if (textSize <= 0f) View.GONE else View.VISIBLE
        tvTitle.textSize = textSize
        return this@TitleBarBuilder
    }

    // left
    fun setLeftImage(resId: Int): TitleBarBuilder {
        ivLeft.visibility = if (resId < 0) View.GONE else View.VISIBLE
        ivLeft.setImageResource(resId)
        return this@TitleBarBuilder
    }
    fun setLeftText(text: String): TitleBarBuilder {
        tvLeft.visibility = if (TextUtils.isEmpty(text)) View.GONE else View.VISIBLE
        tvLeft.text = text
        return this@TitleBarBuilder
    }
    fun setLeftTextSize(textSize: Float): TitleBarBuilder {
        tvLeft.visibility = if (textSize <= 0f) View.GONE else View.VISIBLE
        tvLeft.textSize = textSize
        return this@TitleBarBuilder
    }
    fun setLeftOnClickListener(listener: (v: View)-> Unit): TitleBarBuilder {
        if (ivLeft.visibility == View.VISIBLE) {
            ivLeft.setOnClickListener(listener)
        } else if (tvLeft.visibility == View.VISIBLE) {
            tvLeft.setOnClickListener(listener)
        }
        return this@TitleBarBuilder
    }

    // right
    fun setRightImage(resId: Int): TitleBarBuilder {
        ivRight.visibility = if (resId < 0) View.GONE else View.VISIBLE
        ivRight.setImageResource(resId)
        return this@TitleBarBuilder
    }
    fun setRightText(text: String): TitleBarBuilder {
        tvRight.visibility = if (TextUtils.isEmpty(text)) View.GONE else View.VISIBLE
        tvRight.text = text
        return this@TitleBarBuilder
    }
    fun setRightTextSize(textSize: Float): TitleBarBuilder {
        tvRight.visibility = if (textSize <= 0f) View.GONE else View.VISIBLE
        tvRight.textSize = textSize
        return this@TitleBarBuilder
    }
    fun setRightTextColor(context: Context,resId: Int): TitleBarBuilder {
        tvRight.setTextColor(context.resources.getColor(resId))
        return this@TitleBarBuilder
    }
    fun setTitleTextColor(context: Context,resId: Int): TitleBarBuilder {
        tvTitle.setTextColor(context.resources.getColor(resId))
        return this@TitleBarBuilder
    }

    fun setRightOnClickListener(listener: (v: View)-> Unit): TitleBarBuilder {
        if (ivRight.visibility == View.VISIBLE) {
            ivRight.setOnClickListener(listener)
        } else if (tvRight.visibility == View.VISIBLE) {
            tvRight.setOnClickListener(listener)
        }
        return this@TitleBarBuilder
    }
    fun build(): View? = rootView
}