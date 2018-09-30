package com.duorebate

import android.app.Application
import com.network.glide.GlideHelper
import com.spcenter.PreferenceFile

/**
 * @author Administrator
 * @date 2018/9/25
 */
class DRebateApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        init()
    }
    private fun init() {
        PreferenceFile.init(this)
        GlideHelper.initGlide(this)//初始化Glide图片加载工具
    }
}