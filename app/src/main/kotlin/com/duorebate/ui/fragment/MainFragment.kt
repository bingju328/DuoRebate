package com.duorebate.ui.fragment

import com.duorebate.R
import com.duorebate.adapter.BillRecordAdapter
import com.duorebate.base.ui.BaseRecyclerFragment
import com.duorebate.base.view.DRGridLayoutManager
import com.pulltorefresh.RecyclerBaseAdapter
import com.pulltorefresh.RecyleHeader

/**
 * Created by bingj on 2017/6/18.
 */
class MainFragment: BaseRecyclerFragment<String>() {

    /** header*/
    private lateinit var recyleHeader: RecyleHeader

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
        super.initRecyclerData()
    }

    override fun onLoadDatas() {
        onDataArrived()
    }

}