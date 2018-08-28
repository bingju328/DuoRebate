package com.refresh

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.pulltorefresh.R
import kotlinx.android.synthetic.main.progress_hud.*

/**
 * Created by bingju on 2017/6/26.
 *
 */
open class ProgressHUD : Dialog{
    constructor(context: Context?): super(context)
    constructor(context: Context?,theme: Int): super(context,theme)
    companion object {
        val show = fun (context: Context,message: CharSequence?,cancelable: Boolean,cancelListener: (DialogInterface)->Unit): ProgressHUD {
            // 初始化dialog 加载透明样式
            var dialog = ProgressHUD(context,R.style.progressHUD)
            //加载自定义布局
            dialog.setContentView(getView(context))
            if (message == null || message.length == 0) {
                dialog.progress_message.visibility = View.GONE
            } else {
                dialog.progress_message.setText(message)
            }
            // 设置点击空白区域可以隐藏dialog
            dialog.setCancelable(cancelable)
            // dialog隐藏时的回调
            dialog.setOnCancelListener(cancelListener)
            // dialog窗口居中显示
            dialog.window.attributes.gravity = Gravity.CENTER
            val lp: WindowManager.LayoutParams = dialog.window.attributes
            lp.dimAmount = 0.2f
            dialog.window.attributes = lp
            dialog.show()
            return dialog
        }
        fun getView(context: Context): View {
            //加载自定义显示布局
            var view: View = View.inflate(context, R.layout.progress_hud,null)
            return view
        }
    }

}