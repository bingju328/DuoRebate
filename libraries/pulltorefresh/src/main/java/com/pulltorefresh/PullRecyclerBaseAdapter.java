package com.pulltorefresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pulltorefresh.listener.OnRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class PullRecyclerBaseAdapter<T> extends RecyclerView.Adapter {

    protected static final int TYPE_REFRESH_HEADER = 10000;
    protected static final int TYPE_NORMAL = 0;
    protected static final int TYPE_FOOTER_VIEW = 10001;
    protected static final int HEADER_INIT_INDEX = 10002;
    protected static List<Integer> mHeaderTypes = new ArrayList<>();

    protected boolean pullRefreshEnabled = true;
    protected ArrowRefreshHeader mRefreshHeader;

    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    protected RecyclerView.ViewHolder mViewHolder;

    protected int mScreenWidth;
    public void setScreenWidth(int width) {
        mScreenWidth = width;
    }

    protected ArrayList<T> mDataList = new ArrayList<>();

    protected OnRecyclerItemClickListener<T> onRecyclerItemClickListener;
    public void setHhyjOnRecyclerItemClickListener(OnRecyclerItemClickListener<T> onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void setDataList(Collection<T> list) {
        this.mDataList.clear();
        this.mDataList.addAll(list);
        notifyDataSetChanged();
    }
    public Collection<T> getDataList() {
        return this.mDataList;
    }
    public void addAll(Collection<T> list) {
        int lastIndex = this.mDataList.size();
        if (this.mDataList.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }
}
