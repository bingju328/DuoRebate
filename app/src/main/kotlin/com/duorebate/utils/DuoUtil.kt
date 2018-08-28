package com.duorebate.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.regex.Pattern

/**
 * Created by bingj on 2017/6/14.
 */
private const val STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height"

fun getStatusBarHeight(context: Context): Int {
    return if (!isSelfStatus()) 0 else getInternalDimensionSize(context.resources, STATUS_BAR_HEIGHT_RES_NAME)
}
private fun getInternalDimensionSize(res: Resources,key: String): Int{
    var result = 0
    val resourceId = res.getIdentifier(key,"dimen","android")
    return if (resourceId > 0) res.getDimensionPixelSize(resourceId) else result

}
private fun isSelfStatus(): Boolean{
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
}

/**
 * 判断当前是否有可用的网络以及网络类型 0：无网络 1：WIFI 2：CMWAP 3：CMNET
 *
 * @param context
 * @return
 */
 fun isNetworkAvailable(context: Context): Int {
    val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivity != null) {
        val info: Array<out NetworkInfo> = connectivity.allNetworkInfo
        if (info != null) {
            for (value in info) {
                if (value.state == NetworkInfo.State.CONNECTED) {
                    val netWorkInfo = value
                    if (netWorkInfo.type == ConnectivityManager.TYPE_WIFI)
                        return 1
                    else if(netWorkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                        val extraInfo: String = netWorkInfo.extraInfo
                        if ("cmwap".equals(extraInfo,true) || "cmwap:gsm".equals(extraInfo,true)) {
                            return 2
                        } else {
                            return 3
                        }
                    }
                }
            }
        }
    }
    return 0
}
fun checkNull(s: String?): String {
    return if (s == null) "" else {s.trim()}
}
fun isCheckNull(s: String?): Boolean{
    return checkNull(s).length <= 0
}
fun getScreenHeight(activity: Activity): Int {
    val dm = DisplayMetrics()
    activity.windowManager.defaultDisplay.getMetrics(dm)
    return dm.heightPixels
}
fun getScreenWidth(activity: Activity): Int {
    val dm = DisplayMetrics()
    activity.windowManager.defaultDisplay.getMetrics(dm)
    return dm.widthPixels
}
/**可见屏幕高度**/
fun getAppHeight(activity: Activity): Int {
    val localRect = Rect()
    activity.window.decorView.getWindowVisibleDisplayFrame(localRect)
    return localRect.height()
}
/**键盘是否在显示**/
fun isKeyBoardShow(acitivty: Activity): Boolean {
    val height = getScreenHeight(acitivty) - getStatusBarHeight(acitivty) - getAppHeight(acitivty)
    return height != 0
}
/**关闭键盘**/
fun hideSoftInput(view: View, context: Context) {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(view.windowToken,0)
}
/**显示键盘**/
fun showKeyBoard(view: View, context: Context) {
    view.requestFocus()
    view.post {
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .showSoftInput(view,0)
    }
}
//格式化
fun checkPhone(phone: String): Boolean{
    val p = Pattern.compile("(\\+\\d{2}\\s{0,1}|\\d{0,4}\\s{0,1})\\d{7,14}")
    if (TextUtils.isEmpty(phone) ||
            phone.length < 7 ||
            !p.matcher(phone).matches()) {
        return false
    }
    return true
}
fun replacePhone(phone: String): String{
    return phone.replace("^(\\w{3})(\\w*)(\\w{4})$","$1****$2")
}
/**dp转px**/
fun dip2px(dipValue: Float,context: Context): Int {
    val reSize = context.resources.displayMetrics.density
    return (dipValue*reSize+0.5).toInt()
}
