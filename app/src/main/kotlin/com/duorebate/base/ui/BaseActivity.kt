//package com.duorebate.base.ui
//
//import android.animation.ObjectAnimator
//import android.app.Activity
//import android.graphics.drawable.AnimationDrawable
//import android.os.Bundle
//import android.support.annotation.DrawableRes
//import android.support.annotation.LayoutRes
//import android.view.View
//import android.view.ViewGroup
//import android.widget.FrameLayout
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.TextView
//import com.duorebate.base.view.TitleBarBuilder
//import com.duorebate.utils.checkNull
//import com.duorebate.utils.getStatusBarHeight
//import com.duorebate.utils.isCheckNull
//import com.duorebate.utils.isNetworkAvailable
//import kotlinx.android.synthetic.main.include_layout_loading.*
//import kotlinx.android.synthetic.main.include_view_statusbar.*
//
///**
// * Created by bingj on 2017/6/16.
// */
//abstract class BaseActivity: Activity(){
//
//    /** 状态栏*/
//    protected var view_statusbar: View? = null
//    /** title bar*/
//    protected lateinit var titleBarBuilder: TitleBarBuilder
//    protected var isTitleBar = false
//    /** 加载时状态布局*/
//    protected var layout_load: FrameLayout? = null
//    protected lateinit var animation: AnimationDrawable
//    protected var layout_loading: View? = null
//    protected var layout_load_fail: View? = null
//    protected var layout_load_no_data: View? = null
//    protected var loading_image: ImageView? = null
//
//    protected lateinit var context: Activity
//    protected var isActive: Boolean = false
//
//    protected var tv_reset: TextView? = null
//
//    protected lateinit var httpModel: HttpModel
//
//    protected abstract @LayoutRes fun getContentViewResId(): Int
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        context = this
//        if (getContentViewResId() != 0) {
//            setContentView(getContentViewResId())
//        }
//        initConfigs()
//        initDefaultViews()
//        initViews()
//        initEvents()
//        loadDatas()
//    }
//
//    override fun onStart() {
//        super.onStart()
//        isActive = true
//    }
//    protected open fun initConfigs() {
//
//    }
//    protected open fun initDefaultViews() {
//        //设置状态栏高度
//        view_statusbar = context.view_statusbar
//
//        if (view_statusbar != null) {
//            var ll: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    getStatusBarHeight(context)
//            )
//            view_statusbar?.setBackgroundColor(resources.getColor(R.color.main_color_normal))
//            view_statusbar?.setLayoutParams(ll)
//        }
//        //设置titlebar
//        titleBarBuilder = TitleBarBuilder(context)
//        if (titleBarBuilder.build() == null) {
//            //没有自定义titlebar
//            isTitleBar = false;
//        } else {
//            isTitleBar = true;
//        }
//    }
//    protected open fun initViews() {
//
//    }
//    protected open fun initEvents() {
//
//    }
//    protected open fun loadDatas() {
//
//        layout_load = context.layout_load
//        layout_loading = context.layout_loading
//        loading_image = context.loading_image
//        layout_load_no_data = context.layout_load_no_data
//        layout_load_fail = context.layout_load_fail
//        if (loading_image != null && layout_loading != null) {
//            layout_loading!!.visibility=View.VISIBLE
//            loading_image!!.visibility=View.VISIBLE
//            loading_image!!.setBackgroundResource(R.drawable.anim_loading)
//            alphaAnimRun(loading_image!!)
//            animation = loading_image!!.background as AnimationDrawable
//            animation.isOneShot=false
//            startAnimation()
//        }
//
//        if (layout_load_fail != null && layout_loading != null) {
//            //todo 加载失败时页面的控件操作 eg,重新加载等
//            tv_reset?.setOnClickListener { v->
//                //                    if (loading_image != null && layout_loading != null) {
////                        layout_loading.setVisibility(View.VISIBLE);
////                        loading_image.setVisibility(View.VISIBLE);
////                        loading_image.setBackgroundResource(R.anim.anim_loading);
////                        alphaAnimRun(loading_image);
////                        animation = (AnimationDrawable) loading_image.getBackground();
////                        animation.setOneShot(false);
////                        // animation.start();
////                        startAnimation();
////                    }
//                loadDatas()
//            }
//        }
//        if (isNetworkAvailable(context) == 0)
//            netWorkNot()
//        else
//            onLoadDatas()
//    }
//    fun alphaAnimRun(view: View) {
//        var anim = ObjectAnimator
//                .ofFloat(view,"alpha", 0.5F,  1.0F)
//                .setDuration(150)
//        anim.start()
//    }
//    fun startAnimation() {
//        if (layout_load!!.visibility == View.GONE) layout_load!!.visibility = View.VISIBLE
//        if (layout_loading!!.visibility == View.GONE) layout_loading!!.visibility = View.VISIBLE
//        if (layout_load_fail!!.visibility == View.VISIBLE) layout_load_fail!!.visibility = View.GONE
//        if (animation != null && loading_image != null) animation.start()
//    }
//    /**
//     * 无网络时回调的方法
//     * */
//    open fun netWorkNot(){
//
//    }
//    /**
//     * 加载数据
//     * */
//    open fun onLoadDatas() {}
//
//    /**
//     * 数据未到达
//     */
////    protected fun void onNodataArrived(){}
////    protected final void onNoDataArrived() {
////        onNoDataArrived(null);
////    }
//    /**
//     * 数据到达
//     * */
//    protected fun onDataArrived() {
//        onDataArrived(null)
//    }
//    /**
//     * 数据到达
//     * */
//    protected fun onDataArrived(msg: String?){
//        if(!isCheckNull(msg)) {
//            //todo msg 不为空
//        }
//        if (layout_load != null && layout_load_fail != null) {
//            if (layout_load_fail!!.visibility == View.VISIBLE) {
//                layout_load_fail!!.visibility = View.GONE
//            }
//            layout_load!!.visibility = View.GONE
//        }
//    }
//    /**
//     * 数据未到达
//     * */
//    protected fun onNoDataArrived() {
//        onNoDataArrived(null)
//    }
//    /**
//     * 数据未到达,数据加载失败
//     * */
//    protected fun onNoDataArrived(msg: String?) {
//        if(!isCheckNull(msg)) {
//            //todo msg 不为空
//        }
//        if (layout_load == null || layout_loading == null || layout_load_fail == null) return
//        if(layout_load!!.visibility == View.GONE) layout_load!!.visibility = View.VISIBLE
//        if(layout_loading!!.visibility == View.VISIBLE) layout_loading!!.visibility = View.GONE
//        if(layout_load_fail!!.visibility == View.GONE) layout_load_fail!!.visibility = View.VISIBLE
//
//    }
//    /**
//     * 请求成功，但是数据为空，页面显示暂无数据
//     * */
//    protected fun onDataArrvedEmpty() {
//        if (layout_load != null &&
//                layout_load_no_data != null && layout_loading != null) {
//            if (layout_loading!!.getVisibility() == View.VISIBLE) layout_loading!!.visibility = View.GONE
//            layout_load!!.visibility = View.VISIBLE
//            layout_load_fail!!.visibility=View.GONE
//            layout_load_no_data!!.visibility = View.VISIBLE
//        }
//    }
//    /**
//     * titlebar
//     * */
//    protected fun setTitleText(text: String?) {
//        if (isTitleBar && titleBarBuilder != null) {
//            titleBarBuilder.setTitleText(checkNull(text))
//        }
//    }
//    protected fun setTitleTextSize(text: Float) {
//        if (isTitleBar && titleBarBuilder != null) {
//            titleBarBuilder.setTitleTextSize(text)
//        }
//    }
//    /**
//     * left
//     * */
//    protected fun setLeftImage(@DrawableRes resId: Int) {
//        if (isTitleBar && titleBarBuilder != null) {
//            titleBarBuilder.setLeftImage(resId)
//        }
//    }
//    protected fun setLeftText(text: String?) {
//        if (isTitleBar && titleBarBuilder != null) {
//            titleBarBuilder.setLeftText(checkNull(text))
//        }
//    }
//    protected fun setLeftOnClickListener(onClickListener: (v: View)-> Unit) {
//        if (isTitleBar && titleBarBuilder != null) {
//            titleBarBuilder.setLeftOnClickListener(onClickListener)
//        }
//    }
//    /**
//     * Right
//     * */
//    protected fun setRightImage(@DrawableRes resId: Int) {
//        if (isTitleBar && titleBarBuilder != null) {
//            titleBarBuilder.setRightImage(resId)
//        }
//    }
//    protected fun setRightText(text: String?) {
//        if (isTitleBar && titleBarBuilder != null) {
//            titleBarBuilder.setRightText(checkNull(text))
//        }
//    }
//    protected fun setRightOnClickListener(onClickListener: (v: View)-> Unit) {
//        if (isTitleBar && titleBarBuilder != null) {
//            titleBarBuilder.setRightOnClickListener(onClickListener)
//        }
//    }
//
//
//}