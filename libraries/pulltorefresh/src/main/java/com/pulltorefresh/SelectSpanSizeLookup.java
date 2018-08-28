package com.pulltorefresh;

import android.support.v7.widget.GridLayoutManager;


/**
 * Created by bingju on 2016/6/20.
 *
 * RecyclerView为GridLayoutManager时，设置了HeaderView，就会用到这个SpanSizeLookup
 */
public class SelectSpanSizeLookup extends GridLayoutManager.SpanSizeLookup{
    private int mSpanSize = 1;

    @Override
    public int getSpanSize(int position) {
        return 0;
    }

//    public HhyjSelectSpanSizeLookup(HhyjSelectHeaderAdapter adapter, int spanSize) {
//        this.adapter = adapter;
//        this.mSpanSize = spanSize;
//    }
//
//    @Override
//    public int getSpanSize(int position) {
////        boolean isHeaderOrFooter = adapter.isHeader(position) || adapter.isFooter(position);
//        return 1;
//    }

}
