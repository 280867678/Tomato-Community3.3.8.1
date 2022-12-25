package com.one.tomato.thirdpart.pictureselector;

import android.content.Context;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes3.dex */
public class FullyGridLayoutManager extends GridLayoutManager {
    private int[] mMeasuredDimension;
    final RecyclerView.State mState;

    public FullyGridLayoutManager(Context context, int i) {
        super(context, i);
        this.mMeasuredDimension = new int[2];
        this.mState = new RecyclerView.State();
    }

    public FullyGridLayoutManager(Context context, int i, int i2, boolean z) {
        super(context, i, i2, z);
        this.mMeasuredDimension = new int[2];
        this.mState = new RecyclerView.State();
    }

    @Override // android.support.p005v7.widget.RecyclerView.LayoutManager
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int itemCount = getItemCount();
        int spanCount = getSpanCount();
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < itemCount; i5++) {
            measureScrapChild(recycler, i5, View.MeasureSpec.makeMeasureSpec(i5, 0), View.MeasureSpec.makeMeasureSpec(i5, 0), this.mMeasuredDimension);
            if (getOrientation() == 0) {
                if (i5 % spanCount == 0) {
                    i3 += this.mMeasuredDimension[0];
                }
                if (i5 == 0) {
                    i4 = this.mMeasuredDimension[1];
                }
            } else {
                if (i5 % spanCount == 0) {
                    i4 += this.mMeasuredDimension[1];
                }
                if (i5 == 0) {
                    i3 = this.mMeasuredDimension[0];
                }
            }
        }
        if (mode == 1073741824) {
            i3 = size;
        }
        if (mode2 != 1073741824) {
            size2 = i4;
        }
        setMeasuredDimension(i3, size2);
    }

    private void measureScrapChild(RecyclerView.Recycler recycler, int i, int i2, int i3, int[] iArr) {
        if (i < this.mState.getItemCount()) {
            try {
                View viewForPosition = recycler.getViewForPosition(0);
                if (viewForPosition == null) {
                    return;
                }
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) viewForPosition.getLayoutParams();
                viewForPosition.measure(ViewGroup.getChildMeasureSpec(i2, getPaddingLeft() + getPaddingRight(), ((ViewGroup.MarginLayoutParams) layoutParams).width), ViewGroup.getChildMeasureSpec(i3, getPaddingTop() + getPaddingBottom(), ((ViewGroup.MarginLayoutParams) layoutParams).height));
                iArr[0] = viewForPosition.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
                iArr[1] = viewForPosition.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
                recycler.recycleView(viewForPosition);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
