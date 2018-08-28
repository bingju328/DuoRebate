package com.pulltorefresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lizhixian on 16/1/4.
 */
public class NoAbleRecyclerView extends RecyclerView {

    public NoAbleRecyclerView(Context context) {
        super(context);
    }

    public NoAbleRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoAbleRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }
}
