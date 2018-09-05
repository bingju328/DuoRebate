package com.duorebate.ui.activity

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.duorebate.R
import com.duorebate.base.ui.BaseFragment
import com.duorebate.ui.fragment.HomeFragment
import com.duorebate.utils.getStatusBarHeight
import kotlinx.android.synthetic.main.activity_home.*

/**
 * Created by bingju on 2017/6/18.
 */
class RDHomeActivity: FragmentActivity(){
    private val Tag = "RDHomeActivity"
    private lateinit var statusbar_view: View
    /** fragment*/
    private lateinit var mFragment: Array<Class<out BaseFragment>>
    private var mFragmentMaps = HashMap<Int, Fragment>()
    private var mCurrentSelectedPosition = -1

    private lateinit var ll_content: LinearLayout
    /** BottomBar*/
    private lateinit var vBottomTabs: Array<View>
    private lateinit var vTextViews: Array<TextView>

    private lateinit var context: Activity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        context = this
        initConfig()
        initView()
        initEvent()

    }

    private fun initConfig() {
//        if (MoccaPreferences.is_client.get()!!) { //客户登录
            mFragment = arrayOf(HomeFragment().javaClass, HomeFragment().javaClass,HomeFragment().javaClass)
//        } else { //管理员登录
//            mFragment = arrayOf(HomeFragment().javaClass, ApproveFragment().javaClass,PersonalFragment().javaClass)
//        }

    }

    private fun initView() {
        statusbar_view = context.statusbar_view
        val temp1 = getStatusBarHeight(this)
        val ll = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                temp1
        )
        statusbar_view.setBackgroundColor(resources.getColor(R.color.main_color_normal))
        statusbar_view.layoutParams=ll
        //fragment的内容显示
        ll_content = context.ll_content
        vBottomTabs = arrayOf(
                context.bottom_tab_bar_index,
                context.bottom_tab_bar_mall,
                context.bottom_tab_bar_me
        )
        vTextViews = arrayOf(
                context.tv_tab_bar_index,
                context.tv_tab_bar_mall,
                context.tv_tab_bar_me
        )
    }

    private fun initEvent() {
        /* 默认是选中第一个*/
        vBottomTabs[0].setSelected(true)
        setSeleted(0)
        vTextViews[0].setTextColor(resources.getColor(R.color.bottom_tabbar_title_color_normal))
        for ((index) in vBottomTabs.withIndex()) {
            vBottomTabs[index].setOnClickListener {
                setSeleted(index)
            }
        }
    }

    private fun setSeleted(position: Int) {
        if (mCurrentSelectedPosition == position) {
            return
        }
        var fragment: Fragment? = mFragmentMaps.get(position)
        val transaction = fragmentManager.beginTransaction()
        //hide the olf fragment
        if (mCurrentSelectedPosition != -1) {
            transaction.hide(mFragmentMaps.get(mCurrentSelectedPosition))
        }
        if (fragment == null) {
            fragment = mFragment[position].newInstance()
            //TODO 传值
            val bundle = Bundle()
            fragment!!.arguments=bundle
            mFragmentMaps.put(position,fragment)
            transaction.add(R.id.ll_content,fragment).commit()
        } else {
            transaction.show(fragment)
            transaction.commit()
        }
        mCurrentSelectedPosition = position
        for ((index) in vBottomTabs.withIndex()){
             if (index == position) {
                 vBottomTabs[index].isSelected=true
                 vTextViews[index].setTextColor(resources.getColor(R.color.bottom_tabbar_title_color_normal))
             } else {
                 vBottomTabs[index].isSelected=false
                 vTextViews[index].setTextColor(resources.getColor(R.color.bottom_tabbar_title_color_press))
             }
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
        super.onBackPressed()
    }

}