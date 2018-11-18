package com.bwie.fjd.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class LiuShi extends ViewGroup {
    private int mLeftMargin = 20;
    private int mTopMargin = 20;

    public LiuShi(Context context) {
        this(context, null);
    }

    public LiuShi(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LiuShi(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int leftMargin = mLeftMargin;
        int topMargin = mTopMargin;
        int ViewWidth = 0;
        int ViewHeight = 0;
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modelWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modelHeight = MeasureSpec.getMode(heightMeasureSpec);
        switch (modelHeight) {
            case MeasureSpec.AT_MOST:
                int measuredHeight = 0;
                for (int i = 0; i < getChildCount(); i++) {
                    int measuredWidth = getChildAt(i).getMeasuredWidth();
                    measuredHeight = getChildAt(i).getMeasuredHeight();
                    if (leftMargin + measuredWidth + mLeftMargin > getWidth()) {
                        leftMargin = mLeftMargin;
                        topMargin += measuredHeight + mTopMargin;
                    }
                    leftMargin += measuredWidth + mLeftMargin;
                }
                topMargin += measuredHeight + mTopMargin;
                break;
        }
        setMeasuredDimension(sizeWidth, topMargin);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int leftMargin = mLeftMargin;
        int topMargin = mTopMargin;
        for (int i = 0; i < getChildCount(); i++) {
            int measuredWidth = getChildAt(i).getMeasuredWidth();
            int measuredHeight = getChildAt(i).getMeasuredHeight();
            if (leftMargin + measuredWidth + mLeftMargin > getWidth()) {
                leftMargin = mLeftMargin;
                topMargin += measuredHeight + mTopMargin;
                getChildAt(i).layout(leftMargin, topMargin, leftMargin + measuredWidth, topMargin + measuredHeight);
            } else {
                getChildAt(i).layout(leftMargin, topMargin, leftMargin + measuredWidth, topMargin + measuredHeight);
            }
            leftMargin += measuredWidth + mLeftMargin;
        }
    }
}
