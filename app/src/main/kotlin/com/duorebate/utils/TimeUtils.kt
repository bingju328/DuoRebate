package com.duorebate.utils

import java.util.*


/**
 * Created by bingj on 2017/7/27.
 */

/**
 * 获取两个日期之间的间隔天数
 * @return
 */
fun getGapCount(startDate: Date, endDate: Date): Int {
    val fromCalendar = Calendar.getInstance()
    fromCalendar.setTime(startDate)
    fromCalendar.set(Calendar.HOUR_OF_DAY, 0)
    fromCalendar.set(Calendar.MINUTE, 0)
    fromCalendar.set(Calendar.SECOND, 0)
    fromCalendar.set(Calendar.MILLISECOND, 0)

    val toCalendar = Calendar.getInstance()
    toCalendar.setTime(endDate)
    toCalendar.set(Calendar.HOUR_OF_DAY, 0)
    toCalendar.set(Calendar.MINUTE, 0)
    toCalendar.set(Calendar.SECOND, 0)
    toCalendar.set(Calendar.MILLISECOND, 0)

    return ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24f)).toInt()
}