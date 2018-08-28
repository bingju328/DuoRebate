package com.duorebate.utils

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import com.duorebate.R
import com.refresh.ProgressHUD
import kotlinx.android.synthetic.main.popup_photo_select.view.*
import kotlinx.android.synthetic.main.popup_single_select.view.*
import kotlinx.android.synthetic.main.popup_view_select.view.*

/**
 * Created by bingj on 2017/6/26.
 */
var mProgressHUD: ProgressHUD? = null
fun Context.startPregressUI(){
    mProgressHUD = ProgressHUD.show(this, "正在加载，请稍后...", true){
        mProgressHUD = null
    }
}
fun Context.endPregressUI() {
    if (mProgressHUD != null) {
        mProgressHUD!!.cancel()
        mProgressHUD = null
    }
}
fun Fragment.startPregressUI(context: Context){
    mProgressHUD = ProgressHUD.show(context, "正在加载，请稍后...", true){
        mProgressHUD = null
    }
}
fun Fragment.endPregressUI() {
    if (mProgressHUD != null) {
        mProgressHUD!!.cancel()
        mProgressHUD = null
    }
}

fun Context.show_select_diolog(list: List<String>,title: String,gray_layout: View,body: (content: String)->Unit){
    val view: View = LayoutInflater.from(this).inflate(R.layout.popup_single_select,null)//创建View
    view.tv_title.text = title
    val popup_select: PopupWindow = PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true) // 实例化popupwindow的方法
    popup_select.setBackgroundDrawable(ColorDrawable())
    popup_select.animationStyle = R.style.popwin_anim_style
    // 这个方法是为了当点击除了popupwindow的其他区域可以关闭popupwindow的方法
    popup_select.showAtLocation(gray_layout, Gravity.BOTTOM, 0, 0)
    popup_select.setOnDismissListener {
        gray_layout.visibility = View.GONE
    }
    gray_layout.visibility = View.VISIBLE
    view.whlv_popup_single_select.data = list
    view.tv_cancel.setOnClickListener {
        popup_select.dismiss()
    }
    var dataStr: String = list?.get(0)
    view.tv_confirm.setOnClickListener {
        body(dataStr)
        popup_select.dismiss()
    }
    view.whlv_popup_single_select.setOnItemSelectedListener { picker, data, position ->
        dataStr = data.toString()
    }
}

/**
 * 纸质签名去调用相机 需要两个权限
 */
private val requestPermissions = arrayOf<String>(PERMISSION_CAMERA, PERMISSION_WRITE_EXTERNAL_STORAGE,
        PERMISSION_READ_EXTERNAL_STORAGE)
var view: View? = null
var popup_select: PopupWindow? = null
fun Activity.show_photo(gray_layout: View){
    view = LayoutInflater.from(this).inflate(R.layout.popup_photo_select,null)//创建View
    popup_select = PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true) // 实例化popupwindow的方法
    popup_select!!.setBackgroundDrawable(ColorDrawable())
    popup_select!!.animationStyle = R.style.popwin_anim_style
    // 这个方法是为了当点击除了popupwindow的其他区域可以关闭popupwindow的方法
    popup_select!!.showAtLocation(gray_layout, Gravity.BOTTOM, 0, 0)
    popup_select!!.setOnDismissListener {
        gray_layout.visibility = View.GONE
    }
    view!!.ll_popwindow.setOnClickListener {
        closePopWindow()
    }
    gray_layout.visibility = View.VISIBLE
    view!!.button_takephoto.setOnClickListener {
        requestMultiPermissions(this, requestPermissions){
            closePopWindow()
            pickImageFromCamera(IMAGEUTIL.REQUEST_CODE_FROM_CAMERA)
        }
    }
    view!!.button_album.setOnClickListener {
        requestPermission(this, CODE_READ_EXTERNAL_STORAGE) {
            closePopWindow()
            pickImageFromAlbum(IMAGEUTIL.REQUEST_CODE_FROM_ALBUM)
        }
    }
    view!!.button_quxiao.setOnClickListener {
        closePopWindow()
    }
}
fun Activity.show_mult_photo(gray_layout: View,maxCunt: Int?){
    view = LayoutInflater.from(this).inflate(R.layout.popup_photo_select,null)//创建View
    popup_select = PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true) // 实例化popupwindow的方法
    popup_select!!.setBackgroundDrawable(ColorDrawable())
    popup_select!!.animationStyle = R.style.popwin_anim_style
    // 这个方法是为了当点击除了popupwindow的其他区域可以关闭popupwindow的方法
    popup_select!!.showAtLocation(gray_layout, Gravity.BOTTOM, 0, 0)
    popup_select!!.setOnDismissListener {
        gray_layout.visibility = View.GONE
    }
    view!!.ll_popwindow.setOnClickListener {
        closePopWindow()
    }
    gray_layout.visibility = View.VISIBLE
    view!!.button_takephoto.setOnClickListener {
        if (maxCunt?:0 <= 0) {
            toast("最多可选$ALBUM_MULT_COUNT 张")
            return@setOnClickListener
        }
        requestMultiPermissions(this, requestPermissions){
            closePopWindow()
            pickImageFromCamera(IMAGEUTIL.REQUEST_CODE_FROM_CAMERA)
        }
    }
    view!!.button_album.setOnClickListener {
        if (maxCunt?:0 <= 0) {
            toast("最多可选$ALBUM_MULT_COUNT 张")
            return@setOnClickListener
        }
        requestPermission(this, CODE_READ_EXTERNAL_STORAGE) {
            closePopWindow()
            pickImageFromMultAlbum(IMAGEUTIL.REQUEST_CODE_FROM_ALBUM_MULT,maxCunt)
        }
    }
    view!!.button_quxiao.setOnClickListener {
        closePopWindow()
    }
}
fun Activity.show_isOrNo_Pup(gray_layout: View,confirm: String?,fail: String?,cancel: String?,body:(content: String?)->Unit) {
    view = LayoutInflater.from(this).inflate(R.layout.popup_view_select,null)
    popup_select = PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true) // 实例化popupwindow的方法
    popup_select!!.setBackgroundDrawable(ColorDrawable())
    popup_select!!.animationStyle = R.style.popwin_anim_style
    // 这个方法是为了当点击除了popupwindow的其他区域可以关闭popupwindow的方法
    popup_select!!.showAtLocation(gray_layout, Gravity.BOTTOM, 0, 0)
    popup_select!!.setOnDismissListener {
        gray_layout.visibility = View.GONE
    }
    view!!.ll_view_popwindow.setOnClickListener {
        closePopWindow()
    }
    gray_layout.visibility = View.VISIBLE
    view!!.button_pass.text = confirm?:"是"
    view!!.button_fail.text = fail?:"否"
    view!!.button_cancel.text = cancel?:"取消"
    view!!.button_pass.setOnClickListener {
        body(view!!.button_pass.text.toString())
        closePopWindow()
    }
    view!!.button_fail.setOnClickListener {
        body(view!!.button_fail.text.toString())
        closePopWindow()
    }
    view!!.button_cancel.setOnClickListener {
        body(null)
        closePopWindow()
    }
}
fun Activity.show_dialog(ok: String?,cancel: String?,message:String,okListener:(dialog: DialogInterface,which: Int)-> Unit){
    AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton(ok ?:"OK",okListener)
            .setNegativeButton(cancel ?: "Cancel",null)
            .create()
            .show()
}
fun Activity.show_approve(gray_layout: View,body:(content: Int)->Unit) {
    view = LayoutInflater.from(this).inflate(R.layout.popup_photo_select,null)
    popup_select = PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true) // 实例化popupwindow的方法
    popup_select!!.setBackgroundDrawable(ColorDrawable())
    popup_select!!.animationStyle = R.style.popwin_anim_style
    // 这个方法是为了当点击除了popupwindow的其他区域可以关闭popupwindow的方法
    popup_select!!.showAtLocation(gray_layout, Gravity.BOTTOM, 0, 0)
    popup_select!!.setOnDismissListener {
        gray_layout.visibility = View.GONE
    }
    view!!.ll_popwindow.setOnClickListener {
        closePopWindow()
    }
    gray_layout.visibility = View.VISIBLE
    view!!.button_takephoto.text="上报"
    view!!.button_album.text="回退"
    view!!.button_takephoto.setOnClickListener {
//        requestMultiPermissions(this, requestPermissions){
//            closePopWindow()
//            pickImageFromCamera(IMAGEUTIL.REQUEST_CODE_FROM_CAMERA)
//        }
    }
    view!!.button_album.setOnClickListener {
//        requestPermission(this,CODE_READ_EXTERNAL_STORAGE) {
//            closePopWindow()
//            pickImageFromAlbum(IMAGEUTIL.REQUEST_CODE_FROM_ALBUM)
//        }
    }
    view!!.button_quxiao.setOnClickListener {
        closePopWindow()
    }
}
fun closePopWindow() {
    if (popup_select != null && popup_select!!.isShowing()) {
        popup_select?.dismiss()
    }
}