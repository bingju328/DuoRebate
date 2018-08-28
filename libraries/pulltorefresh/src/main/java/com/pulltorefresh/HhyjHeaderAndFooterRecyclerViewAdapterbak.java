package com.pulltorefresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView.Adapter with Header and Footer
 */
public class HhyjHeaderAndFooterRecyclerViewAdapterbak extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

//    private static final int TYPE_HEADER_VIEW = Integer.MIN_VALUE;
//    private static final int TYPE_FOOTER_VIEW = Integer.MIN_VALUE + 1;

    private static final int TYPE_REFRESH_HEADER = 10000;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER_VIEW = 10001;
    private static final int HEADER_INIT_INDEX = 10002;
    private static List<Integer> mHeaderTypes = new ArrayList<>();

    private boolean pullRefreshEnabled = true;
    private ArrowRefreshHeader mRefreshHeader;
    /**
     * RecyclerView使用的，真正的Adapter
     */
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mInnerAdapter;

    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private ArrayList<View> mFooterViews = new ArrayList<>();

    private Context mContext;

    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {

        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            notifyItemRangeChanged(positionStart + getHeaderViewsCount(), itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            notifyItemRangeInserted(positionStart + getHeaderViewsCount(), itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            notifyItemRangeRemoved(positionStart + getHeaderViewsCount(), itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            int headerViewsCountCount = getHeaderViewsCount();
            notifyItemRangeChanged(fromPosition + headerViewsCountCount, toPosition + headerViewsCountCount + itemCount);
        }
    };

    public HhyjHeaderAndFooterRecyclerViewAdapterbak() {
    }

    public HhyjHeaderAndFooterRecyclerViewAdapterbak(Context context, RecyclerView.Adapter innerAdapter) {
        mContext = context;
        setRefreshHeader();
        setAdapter(innerAdapter);
    }
    public void setRefreshHeader(){
        if (pullRefreshEnabled) {
            ArrowRefreshHeader refreshHeader = new ArrowRefreshHeader(mContext);
            mRefreshHeader = refreshHeader;
//            mRefreshHeader.setProgressStyle(mRefreshProgressStyle);
        }
    }
    public void setRefreshHeader(ArrowRefreshHeader refreshHeader){
        mHeaderViews.add(0, refreshHeader);
        mRefreshHeader = refreshHeader;
//        mRefreshHeader.setProgressStyle(mRefreshProgressStyle);
        this.notifyDataSetChanged();
    }
    public ArrowRefreshHeader getRefreshHeader(){
        return mRefreshHeader;
    }

    /**
     * 设置adapter
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {

        if (adapter != null) {
            if (!(adapter instanceof RecyclerView.Adapter))
                throw new RuntimeException("your adapter must be a RecyclerView.Adapter");
        }

        if (mInnerAdapter != null) {
            notifyItemRangeRemoved(getHeaderViewsCount(), mInnerAdapter.getItemCount());
            mInnerAdapter.unregisterAdapterDataObserver(mDataObserver);
        }

        this.mInnerAdapter = adapter;
        mInnerAdapter.registerAdapterDataObserver(mDataObserver);
        notifyItemRangeInserted(getHeaderViewsCount(), mInnerAdapter.getItemCount());
    }

    public RecyclerView.Adapter getInnerAdapter() {
        return mInnerAdapter;
    }

    public void addHeaderView(View header) {

        if (header == null) {
            throw new RuntimeException("header is null");
        }
        mHeaderTypes.add(HEADER_INIT_INDEX + mHeaderViews.size());
        mHeaderViews.add(header);
        this.notifyDataSetChanged();
    }

    public void addFooterView(View footer) {

        if (footer == null) {
            throw new RuntimeException("footer is null");
        }

        mFooterViews.add(footer);
        this.notifyDataSetChanged();
    }
    /**
     * 根据header的ViewType判断是哪个header
     * @param itemType
     * @return
     */
    private View getHeaderViewByType(int itemType) {
        if(!isHeaderType(itemType)) {
            return null;
        }
        return mHeaderViews.get(itemType - HEADER_INIT_INDEX);
    }
    /**
     * 判断一个type是否为HeaderType
     * @param itemViewType
     * @return
     */
    private boolean isHeaderType(int itemViewType) {
        return  mHeaderViews.size() > 0 &&  mHeaderTypes.contains(itemViewType);
    }

    /**
     * 返回第一个FoView
     * @return
     */
    public View getFooterView() {
        return  getFooterViewsCount()>0 ? mFooterViews.get(0) : null;
    }

    /**
     * 返回第一个HeaderView
     * @return
     */
    public View getHeaderView() {
        return  getHeaderViewsCount()>0 ? mHeaderViews.get(0) : null;
    }
    public ArrayList<View> getHeaderViews() {
        return mHeaderViews;
    }
    public void removeHeaderView(View view) {
        mHeaderViews.remove(view);
        this.notifyDataSetChanged();
    }

    public void removeFooterView(View view) {
        mFooterViews.remove(view);
        this.notifyDataSetChanged();
    }

    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    public int getFooterViewsCount() {
        return mFooterViews.size();
    }

//    public boolean isHeader(int position) {
//        return getHeaderViewsCount() > 0 && position == 0;
//    }
    public boolean isHeader(int position) {
        return position >= 1 && position < mHeaderViews.size() + 1;
    }

    public boolean isRefreshHeader(int position) {
//        Log.e("lzx","isRefreshHeader  + " + position);
        return position == 0;
    }

    public boolean isFooter(int position) {
        int lastPosition = getItemCount() - 1;
        return getFooterViewsCount() > 0 && position == lastPosition;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        int headerViewsCountCount = getHeaderViewsCount();
//        if (viewType < TYPE_HEADER_VIEW + headerViewsCountCount) {
//            return new ViewHolder(mHeaderViews.get(viewType - TYPE_HEADER_VIEW));
//        } else if (viewType >= TYPE_FOOTER_VIEW && viewType < Integer.MAX_VALUE / 2) {
//            return new ViewHolder(mFooterViews.get(viewType - TYPE_FOOTER_VIEW));
//        } else {
//            return mInnerAdapter.onCreateViewHolder(parent, viewType - Integer.MAX_VALUE / 2);
//        }

        if (viewType == TYPE_REFRESH_HEADER) {
            return new ViewHolder(mRefreshHeader);
        } else if (isHeaderType(viewType)) {
            return new ViewHolder(getHeaderViewByType(viewType));
        } else if (viewType == TYPE_FOOTER_VIEW) {
            return new ViewHolder(mFooterViews.get(0));
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        int headerViewsCountCount = getHeaderViewsCount();
//        if (position >= headerViewsCountCount && position < headerViewsCountCount + mInnerAdapter.getItemCount()) {
//            mInnerAdapter.onBindViewHolder(holder, position - headerViewsCountCount);
//        } else {
//            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
//            if(layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
//                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
//            }
//        }

        if (isHeader(position) || isRefreshHeader(position)) {
            return;
        }
        final int adjPosition = position - (getHeaderViewsCount() + 1);
        int adapterCount;
        if (mInnerAdapter != null) {
            adapterCount = mInnerAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                mInnerAdapter.onBindViewHolder(holder, adjPosition);

//                if (mOnItemClickLitener != null) {
//                    holder.itemView.setOnClickListener(new View.OnClickListener()
//                    {
//                        @Override
//                        public void onClick(View v)
//                        {
//                            mOnItemClickLitener.onItemClick(holder.itemView, adjPosition);
//                        }
//                    });
//
//                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
//                    {
//                        @Override
//                        public boolean onLongClick(View v)
//                        {
//                            mOnItemClickLitener.onItemLongClick(holder.itemView, adjPosition);
//                            return false;
//                        }
//                    });
//                }

                return;
            }
        }

    }

    @Override
    public int getItemCount() {
//        return getHeaderViewsCount() + getFooterViewsCount() + mInnerAdapter.getItemCount();
        if (mInnerAdapter != null) {
            return getHeaderViewsCount() + getFooterViewsCount() + mInnerAdapter.getItemCount() + 1;
        } else {
            return getHeaderViewsCount() + getFooterViewsCount() + 1;
        }

    }

    @Override
    public int getItemViewType(int position) {
//        int innerCount = mInnerAdapter.getItemCount();
//        int headerViewsCountCount = getHeaderViewsCount();
//        if (position < headerViewsCountCount) {
//            return TYPE_HEADER_VIEW + position;
//        } else if (headerViewsCountCount <= position && position < headerViewsCountCount + innerCount) {
//
//            int innerItemViewType = mInnerAdapter.getItemViewType(position - headerViewsCountCount);
//            if(innerItemViewType >= Integer.MAX_VALUE / 2) {
//                throw new IllegalArgumentException("your adapter's return value of getViewTypeCount() must < Integer.MAX_VALUE / 2");
//            }
//            return innerItemViewType + Integer.MAX_VALUE / 2;
//        } else {
//            return TYPE_FOOTER_VIEW + position - headerViewsCountCount - innerCount;
//        }

        int adjPosition = position - (getHeaderViewsCount() + 1);
        if (isRefreshHeader(position)) {
            return TYPE_REFRESH_HEADER;
        }
        if (isHeader(position)) {
            position = position - 1;
            return mHeaderTypes.get(position);
        }
        if (isFooter(position)) {
            return TYPE_FOOTER_VIEW;
        }
        int adapterCount;
        if (mInnerAdapter != null) {
            adapterCount = mInnerAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mInnerAdapter.getItemViewType(adjPosition);
            }
        }
        return TYPE_NORMAL;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
