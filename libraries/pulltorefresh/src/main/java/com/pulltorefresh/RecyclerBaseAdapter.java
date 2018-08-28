package com.pulltorefresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by bingju on 2017/1/16.
 * RecyclerBaseaAdapter的基础类
 */

public abstract class RecyclerBaseAdapter<T> extends RecyclerView.Adapter{
    protected Context context;
    protected LayoutInflater layoutInflater;
    protected RecyclerView.ViewHolder viewHolder;

    protected List<T> dataList = new ArrayList<>();

//    protected OnRecyclerItemClickListener<T> onRecyclerItemClickListener;
//    public void setOnRecyclerItemClickListener(
//            OnRecyclerItemClickListener<T> onRecyclerItemClickListener) {
//        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
//    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindViewHolders(holder,position);
    }
    public abstract void onBindViewHolders(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public void setDataList(Collection<T> list) {
        this.dataList.clear();
        this.dataList.addAll(list);
        notifyDataSetChanged();
    }
    public Collection<T> getDataList() {
        return this.dataList;
    }
    public void addAll(Collection<T> list) {
        int lastIndex =  this.dataList.size();
        if (this.dataList.addAll(list))
            notifyItemRangeInserted(lastIndex,list.size());
    }
    public void clear() {
        dataList.clear();
        notifyDataSetChanged();
    }
    protected String checkNull(String s) {
        return s == null ? "" : s.trim();
    }
    protected boolean isCheckNull(String s) {
        return checkNull(s).length() <= 0;
    }

}
