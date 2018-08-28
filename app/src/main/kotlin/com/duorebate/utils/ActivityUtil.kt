package com.duorebate.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import java.util.regex.Pattern

/**
 * Created by bingj on 2017/6/26.
 */
fun startLoginActivity() {

}
fun startCommonActivity(context: Context,javaClass: String) {
    val classPath = Class.forName(javaClass)
    context.startActivity(Intent(context,classPath))
}
fun <T>startCommonActivity(context: Context,javaClass: Class<T>) {
    context.startActivity(Intent(context,javaClass))
}
fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
fun <T>Activity.startCommonActivity(javaClass: Class<T>){
    this.startActivity(Intent(this,javaClass))
}

/**
 * 校验手机号是否合法
 *
 */
fun Context.checkVifyPhone(phone: String?): Boolean {
    val p = Pattern.compile("[1][34578]\\d{9}")
    when  {
        TextUtils.isEmpty(phone)->{
            toast("请输入手机号")
            return false
        }
        !p.matcher(phone).matches()->{
            toast("请输入正确的手机号码")
            return false
        }
        else -> return true
    }
}
fun Context.checkVifyUserCode(phone: String?): Boolean {
//    val p = Pattern.compile("[1][34578]\\d{9}")
    when  {
        TextUtils.isEmpty(phone)->{
            toast("用户登录码")
            return false
        }
//        !p.matcher(phone).matches()->{
//            toast("请输入正确的手机号码")
//            return false
//        }
        else -> return true
    }
}
fun Context.checkVifyCode(checkCode: String?): Boolean {
    when {
        TextUtils.isEmpty(checkCode) ->{
            toast("请输入验证码")
            return false
        }
        else -> return true
    }
}
fun Context.checkVifyPwd(pwd: String?): Boolean {
    when  {
        TextUtils.isEmpty(pwd)->{
            toast("请输入密码")
            return false
        }
        else -> return true
    }
}
fun Context.checkVifyFeedBackInfo(backInfo: String?):Boolean{
    when {
        TextUtils.isEmpty(backInfo) -> {
            toast("请输入反馈信息")
            return false
        }
        else ->{
            return true
        }
    }
}
/**dp转px**/
fun Context.dip2px(dipValue: Float): Int {
    val reSize = this.resources.displayMetrics.density
    return (dipValue*reSize+0.5).toInt()
}
//inline fun <reified T: Activity> Activity.startClientActivity(){
//    if (MoccaPreferences.is_client.get()!!){
//        startActivity(Intent(this,T::class.java))
//    } else {
//        toast("此为客户功能")
//    }
//}
inline fun <reified T: Activity> Activity.startActivity(){
    startActivity(Intent(this,T::class.java))
}
inline fun <reified T: Activity> Activity.startActivity(intent:Intent){
    intent.setClass(this,T::class.java)
    startActivity(intent)
}
//fun Activity.goLogin(){
//    // 退出程序方法有多种o
//    // 这里使用clear + new task的方式清空整个任务栈,只保留新打开的Main页面
//    // 然后Main页面接收到退出的标志位exit=true,finish自己,这样就关闭了全部页面
//    val intent = Intent(this, LoginActivity::class.java)
//    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//    startActivity(intent)
//}
// inline fun <reified T: Activity> Context.startClientActivity(){
//     if (MoccaPreferences.is_client.get()!!){
//         startActivity(Intent(this,T::class.java))
//     } else {
//         toast("此为客户功能")
//     }
//}
//inline public fun <reified T : Activity> Activity.navigate(id: String) { 
//    val intent = Intent(this, javaClass<T>())
//     intent.putExtra("id", id)
//     startActivity(intent) 
//}
