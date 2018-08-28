package com.pulltorefresh.listener;

/**
 * Created by Administrator on 2017/1/11.
 */

public interface OnRecyclerItemLongClickListener<T> {
    void OnRecyclerLongClick(int position, T t);
}
