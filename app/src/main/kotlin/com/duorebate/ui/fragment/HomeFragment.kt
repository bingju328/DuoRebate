package com.duorebate.ui.fragment

import com.duorebate.R
import com.duorebate.base.ui.BaseFragment
import com.duorebate.utils.toast
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by bingj on 2017/6/18.
 */
class HomeFragment: BaseFragment() {

    override fun getContentViewResId(): Int {
        return R.layout.fragment_home
    }

    override fun initEvents() {
        setTitleText("盛周理财")
        tv_find.setOnClickListener {
            context.toast("此功能暂未开放")
        }
    }
    override fun onLoadDatas() {
        onDataArrvedEmpty()
    }
}