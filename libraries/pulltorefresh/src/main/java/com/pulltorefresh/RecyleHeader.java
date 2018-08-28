package com.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;


/**
 * Created by bingju on 2016/6/20.
 *
 * RecyclerView的HeaderView
 */
public class RecyleHeader extends RelativeLayout {

    /** header*/
    private Context context;

    private int layoutId;


    public RecyleHeader(Context context, int layoutId) {
        super(context);
        this.context = context;
        this.layoutId = layoutId;
        init(context);
    }

    public RecyleHeader(Context context, AttributeSet attrs, int layoutId) {
        super(context, attrs);
        this.context = context;
        this.layoutId = layoutId;
        init(context);
    }

    public RecyleHeader(Context context, AttributeSet attrs, int defStyleAttr, int layoutId) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.layoutId = layoutId;
        init(context);
    }

    public void init(Context context) {
        if (layoutId != 0) {
            inflate(context, layoutId, this);
        } else {
            new IllegalArgumentException("layoutId cant is 0");
        }

    }
}