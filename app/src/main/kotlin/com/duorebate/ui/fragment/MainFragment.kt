package com.duorebate.ui.fragment

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.duorebate.R
import com.duorebate.adapter.BillRecordAdapter
import com.duorebate.base.ui.BaseRecyclerFragment
import com.duorebate.base.view.DRGridLayoutManager
import com.duorebate.base.view.RollViewPagerFitXY
import com.duorebate.utils.dip2px
import com.duorebate.utils.getScreenWidth
import com.duorebate.utils.isFastDoubleClick
import com.pulltorefresh.RecyclerBaseAdapter
import com.pulltorefresh.RecyclerViewUtils
import com.pulltorefresh.RecyleHeader
import kotlinx.android.synthetic.main.banner_view.view.*
import kotlinx.android.synthetic.main.df_main_header.view.*

/**
 * Created by bingj on 2017/6/18.
 */
class MainFragment: BaseRecyclerFragment<String>() {

    /** header*/
    private lateinit var recyleHeader: RecyleHeader
    private lateinit var tv_list_title: TextView
    private lateinit var ll_advice_content: View
    private lateinit var ll_shangxin: View

    /** 轮播图 */
    private lateinit var include_banner: View
    private lateinit var mViewPagerContainer: LinearLayout
    private lateinit var mTvDescription: TextView
    private lateinit var mPointsContainer:LinearLayout
    private lateinit var mDotLists: ArrayList<View>
    private lateinit var banners: ArrayList<String>
    private lateinit var mRollViewPager: RollViewPagerFitXY

    override fun initViews() {
        initHeaderData()
        super.initViews()
    }
    /**
     * headerView
     */
    private fun initHeaderData() {
        recyleHeader = RecyleHeader(context,R.layout.df_main_header)
        recyleHeader.hhyj_ll_shangxin.visibility = View.GONE
    }
    private fun prepareViewPage(context: Context,list: List<String>) {
        if (list.isEmpty()) {
            return
        }
        initDots(list.size)
        mRollViewPager = RollViewPagerFitXY(null
                ,context,mDotLists,R.mipmap.hhyj_home_banner_bg_pic_white,R.mipmap.hhyj_home_banner_bg_pic_active,
                object : RollViewPagerFitXY.MyOnTouchCllickCallBack {
                    override fun OnTouchCllick(position: Int) {
                        if (isFastDoubleClick()) {

                        }
                    }
                } )
        mRollViewPager.layoutParams = LinearLayout.LayoutParams(getScreenWidth(activity), getScreenWidth(activity)/3)
        mRollViewPager.setImageUrl(list)
        mRollViewPager.startRoll()
        recyleHeader.banner_viewpager.removeAllViews()
        recyleHeader.banner_viewpager.addView(mRollViewPager)
    }
    private fun initDots(size: Int) {
        if (size == 1) {
            recyleHeader.ll_points.visibility = View.GONE
        } else {
            recyleHeader.ll_points.visibility = View.VISIBLE
        }
        mDotLists = arrayListOf()
        recyleHeader.ll_points.removeAllViews()
        for (i in 0 until size) {
            val layoutParams = LinearLayout.LayoutParams(
                    dip2px(context,8f), dip2px(context,8f)
            )
            layoutParams.setMargins(10,0,10,10)
            val imageView = ImageView(context)
            if (i == 0) {
                imageView.setBackgroundResource(R.mipmap.hhyj_home_banner_bg_pic_active)
            } else {
                imageView.setBackgroundResource(R.mipmap.hhyj_home_banner_bg_pic_white)
            }
            imageView.layoutParams = layoutParams
            mDotLists.add(imageView)
            recyleHeader.ll_points.addView(imageView)
        }
    }
    override fun getContentViewResId(): Int {
        return R.layout.common_titlebar_pull_recycler
    }
    private lateinit var recordAdapter: BillRecordAdapter
    override fun getListAdapter(): RecyclerBaseAdapter<String> {
        recordAdapter = BillRecordAdapter(context)
        return recordAdapter
    }
    override fun initEvents() {
        setTitleText("多返")
//        setTitleBgColor(Color.parseColor("#55C53333"))
        onDataArrived()
    }
    override fun initRecyclerData() {
        val gradLayoutManager = DRGridLayoutManager(context,2)
        pull_recyclerview.layoutManager = gradLayoutManager
//        val linearLayoutManager = LinearLayoutManager(context)
//        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
//        pull_recyclerview.layoutManager = linearLayoutManager
        prepareViewPage(context, arrayListOf("https://www.baidu.com/img/bd_logo1.png","https://www.baidu.com/img/bd_logo1.png"))
        super.initRecyclerData()
        RecyclerViewUtils.setHeaderView(pull_recyclerview,recyleHeader)
//        HhyjRecyclerViewUtils.setHeaderView(pull_recyclerview, hhyjListHeader);
    }

    override fun onLoadDatas() {
        onDataArrived()
    }


}