package com.pulltorefresh.listener;

/**
 * Created by bingju on 2017/1/11.
 * RecyclerView Item的回调接口
 */

public interface OnRecyclerItemClickListener<T> {
    /**
     * RecyclerView Item 点击回调方法
     * */
    void onRecyclerItemClick(int position, T t);

}
