package com.duorebate.utils

import android.os.Environment

/**
 * Created by bingj on 2017/7/18.
 */

/**
 * 是否有SD卡
 * */
fun isSDCard(): Boolean{
    return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
}
/**
 * Check the SD card
 *
 * @return
 */
fun checkSDCardAvailable(): Boolean {
    return android.os.Environment.getExternalStorageState() == android.os.Environment.MEDIA_MOUNTED
}