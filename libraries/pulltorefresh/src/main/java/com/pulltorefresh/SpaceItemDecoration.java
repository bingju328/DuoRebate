package com.pulltorefresh;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by bingju on 2016/6/22.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration{
    private int space;
    private Type type = Type.VERTICAL;
    private int total = 0;
    public SpaceItemDecoration(Type type, int space) {
        this.type = type;
        this.space = space;
    }
    public SpaceItemDecoration(Type type, int space, int total) {
        this.type = type;
        this.space = space;
        this.total = total;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        if (parent.getChildLayoutPosition(view) != 0 && parent.getChildLayoutPosition(view) != total) {
            if (type == Type.HORIZONTAL) {
                outRect.right = space;
            } else {
                outRect.bottom = space;
            }

//        }
    }
    public enum Type {
        HORIZONTAL,VERTICAL;
    }
}
