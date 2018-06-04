package com.qlckh.purifier.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Andy
 * @date   2018/5/19 21:41
 * @link   {http://blog.csdn.net/andy_l1}
 * Desc:    PicGridView.java
 */
public class PicGridView extends ViewGroup {
    private int size;
    private int visibleCount;
    private int columNum =3;

    public PicGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public PicGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PicGridView(Context context) {
        super(context);
    }

    public void setColumNum(int num) {
        columNum = num;
        requestLayout();
    }

    public void setVisibleCount(int num) {
        this.visibleCount = num;
        for (int i = 0; i < getChildCount(); i++) {
            if (i < num) {
                getChildAt(i).setVisibility(View.VISIBLE);
            } else {
                getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        size = (width - getPaddingLeft() * (columNum + 1)) / columNum;
        int childSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        measureChildren(childSpec, childSpec);
        int lineNum = getChildCount() != 0 ? (getVisibleChildCount() - 1) / columNum + 1 : 0;
        int height = 0;
        if (lineNum != 0) {
            height = lineNum * size + getPaddingLeft() * (lineNum + 1);
//            height = lineNum * size;
        }
        setMeasuredDimension(width, height);
    }

    final int getVisibleChildCount() {
        return visibleCount == 0 ? getChildCount() : visibleCount;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getVisibleChildCount(); i++) {
            int left = i % columNum * size + (i % columNum + 1) * getPaddingLeft();
            int top = i / columNum * size + (i / columNum + 1) * getPaddingLeft();
            getChildAt(i).layout(left, top, left + size, top + size);
        }
    }

}
