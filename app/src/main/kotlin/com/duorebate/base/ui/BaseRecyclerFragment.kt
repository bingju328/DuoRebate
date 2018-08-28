//package com.duorebate.base.ui
//
//import android.os.Handler
//import android.os.Message
//import android.util.Log
//import android.view.View
//import com.duorebate.utils.isNetworkAvailable
//import com.pulltorefresh.*
//import rx.Observable
//import java.lang.ref.WeakReference
//
///**
// * Created by bingju on 2017/7/6.
// */
//abstract class BaseRecyclerFragment<T>: BaseFragment() {
//    protected lateinit var pull_recyclerview: PullRecyclerView
//    protected lateinit var recyclerBaseAdapter: RecyclerBaseAdapter<T>
//    protected lateinit var mHeaderAndFooterRecyclerViewAdapter: HeaderAndFooterRecyclerViewAdapter
//
//    protected lateinit var mDataList: ArrayList<T>
//    /*是否正在刷新or加载更多*/
//    protected var isRefreshing: Boolean = false
//    protected var isLoadingMore: Boolean = false
//    /*是否显示底部的没有更多*/
//    protected var isShowMore: Boolean = false
//    /** 是否第一次加载*/
//    protected var isFirst: Boolean = true
//    protected var isAddFooter: Boolean = false
//    /**每一页展示多少条数据*/
//    protected val REQUEST_COUNT = 10
//    /** 页码默认从第一页开始*/
//    protected var page = 1
//    protected var isFirstPage: Boolean = false
//    /** 每页显示的大小默认为10*/
//    protected var pageSize = 10
//    /** 当前页加载的数据大小用来判断分页的*/
//    protected var currentPageSize = 0
//    protected lateinit var httpModel: HttpModel
//
//
//
//    override fun getContentViewResId(): Int {
//        return R.layout.common_pull_recycler
//    }
//
//    override fun initViews() {
//        pull_recyclerview = mView.findViewById(R.id.pull_recyclerview) as PullRecyclerView
//        initRecyclerData()
//    }
//    protected open fun initRecyclerData() {
//        //需要子类调用此方法
//        mDataList = arrayListOf()
//        recyclerBaseAdapter = getListAdapter()
//        recyclerBaseAdapter.addAll(mDataList)
//        mHeaderAndFooterRecyclerViewAdapter = HeaderAndFooterRecyclerViewAdapter(context,recyclerBaseAdapter)
////        添加Item间距把此处复制到子类中
////        pull_recyclerview.addItemDecoration(new HhyjSpaceItemDecoration(
////                HhyjSpaceItemDecoration.Type.VERTICAL, HhyjBaseUtils.dip2px(mContext,10)));
//        pull_recyclerview.setAdapter(mHeaderAndFooterRecyclerViewAdapter)
//        pull_recyclerview.addOnScrollListener(recyclerOnScrollListener)
//        pull_recyclerview.setLoadingListener {
//            setRefresh()
//        }
//
//        notifyDataSetChanged();
//    }
//
//    protected fun setRefresh() {
//        isRefreshing = true
//        page = 1
//        onLoadDatas()
//    }
//
//    protected abstract fun getListAdapter(): RecyclerBaseAdapter<T>
//
//    protected fun addItems(list: List<T>) {
//        recyclerBaseAdapter.addAll(list)
//    }
//    protected fun refreshComplete() {
//        if (pull_recyclerview != null && isRefreshing) {
//            isRefreshing = false;
//            pull_recyclerview.refreshComplete()
//        }
//    }
//
//    /**
//     * 处理分页时Item的显示动画
//     * -1: //数据正常
//     * -2: //list数据变化
//     * -3: //加载下一页时没网络
//     * -4: //加载下一页没有更多数据
//     */
//    protected inner class PreviewHandler(fragment: BaseRecyclerFragment<*>): Handler() {
//        private var ref: WeakReference<BaseRecyclerFragment<*>>
//        init {
//            ref = WeakReference(fragment)
//        }
//        override fun handleMessage(msg: Message?) {
//            val fragment: BaseRecyclerFragment<*>? = ref.get()
//            if (fragment?.activity == null || fragment?.activity?.isFinishing == null) {
//                return
//            }
//            when (msg?.what) {
//                -1 ->  //数据正常
//                    RecyclerViewStateUtils.setFooterViewState(pull_recyclerview, LoadingFooter.State.Normal)
//                -2 ->  //list数据变化
//                    notifyDataSetChanged()
//                -3 -> //加载下一页时没网络
//                    RecyclerViewStateUtils.setFooterViewState(context, pull_recyclerview, REQUEST_COUNT, LoadingFooter.State.NetWorkError, mFooterClick)
//                -4 -> //加载下一页没有更多数据
//                    RecyclerViewStateUtils.setFooterViewState(context,
//                            pull_recyclerview, REQUEST_COUNT, LoadingFooter.State.TheEnd, null)
//            }
//        }
//    }
//    protected var recyclerOnScrollListener = object: RecyclerOnScrollListener(){
//        override fun onLoadNextPage(view: View?) {
//            super.onLoadNextPage(view)
//            if (isAddFooter || isRefreshing) {
//                return
//            }
//            val state = RecyclerViewStateUtils.getFooterViewState(pull_recyclerview)
//            Log.i("@Cundong", "the state is Loading, just wait.."+state.toString())
//            if(state == LoadingFooter.State.Loading) {
//                Log.i("@Cundong", "the state is Loading, just wait..")
//                return
//            }
//            if (currentPageSize == pageSize) {
//                // loading more
//                RecyclerViewStateUtils.setFooterViewState(context,
//                        pull_recyclerview, REQUEST_COUNT, LoadingFooter.State.Loading, null);
//                // TODO: 2016/6/25 loading more
//                loadMore()
//            } else {
//                if (!isFirstPage && isShowMore) {
//                    //第一页不要添加，不然会crash （操作慎重）
//                    //the end // TODO: 2016/9/18
//                    RecyclerViewStateUtils.setFooterViewState(context,
//                            pull_recyclerview, REQUEST_COUNT, LoadingFooter.State.TheEnd, null);
//                }
//            }
//        }
//    }
//    /**
//     * 刷新适配器
//     * */
//    protected fun notifyDataSetChanged() {
//        mHeaderAndFooterRecyclerViewAdapter.notifyDataSetChanged()
//    }
//    protected fun clearItemData() {
//        recyclerBaseAdapter.getDataList().clear()
//    }
//    protected var mFooterClick = {v: View ->
//        RecyclerViewStateUtils.setFooterViewState(context, pull_recyclerview, REQUEST_COUNT, LoadingFooter.State.Loading, null)
//        loadMore()
//    }
//    /**
//     * 加载更多
//     * */
//    protected fun loadMore() {
//        isLoadingMore = true
//        if (isNetworkAvailable(context) == 0) {
//            Observable.timer(1, java.util.concurrent.TimeUnit.SECONDS).subscribe {
//                RecyclerViewStateUtils.setFooterViewState(context, pull_recyclerview, REQUEST_COUNT, LoadingFooter.State.NetWorkError, mFooterClick)
//            }
//        } else {
//            onLoadDatas()
//        }
//    }
//}