package com.duorebate.utils

import android.text.TextUtils
import java.util.regex.Pattern

/**
 * @author Administrator
 * @date 2018/9/25
 */

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

