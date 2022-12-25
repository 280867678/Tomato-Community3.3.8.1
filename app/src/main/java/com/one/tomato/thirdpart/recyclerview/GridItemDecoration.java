package com.one.tomato.thirdpart.recyclerview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.support.p005v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes3.dex */
public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;
    private int mHorizonSpan;
    private boolean mShowLastLine;
    private int mVerticalSpan;

    private GridItemDecoration(int i, int i2, int i3, boolean z) {
        this.mHorizonSpan = i;
        this.mShowLastLine = z;
        this.mVerticalSpan = i2;
        this.mDivider = new ColorDrawable(i3);
    }

    @Override // android.support.p005v7.widget.RecyclerView.ItemDecoration
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        drawHorizontal(canvas, recyclerView);
        drawVertical(canvas, recyclerView);
    }

    private void drawHorizontal(Canvas canvas, RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            if (!isLastRaw(recyclerView, i, getSpanCount(recyclerView), childCount) || this.mShowLastLine) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
                int bottom = childAt.getBottom() + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
                this.mDivider.setBounds(childAt.getLeft() - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, bottom, childAt.getRight() + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, this.mHorizonSpan + bottom);
                this.mDivider.draw(canvas);
            }
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            if ((recyclerView.getChildViewHolder(childAt).getAdapterPosition() + 1) % getSpanCount(recyclerView) != 0) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
                int top2 = childAt.getTop() - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
                int bottom = childAt.getBottom() + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + this.mHorizonSpan;
                int right = childAt.getRight() + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
                int i2 = this.mVerticalSpan;
                int i3 = right + i2;
                if (i == childCount - 1) {
                    i3 -= i2;
                }
                this.mDivider.setBounds(right, top2, i3, bottom);
                this.mDivider.draw(canvas);
            }
        }
    }

    @Override // android.support.p005v7.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        int i;
        int spanCount = getSpanCount(recyclerView);
        int itemCount = recyclerView.getAdapter().getItemCount();
        int viewLayoutPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (viewLayoutPosition < 0) {
            return;
        }
        int i2 = viewLayoutPosition % spanCount;
        int i3 = this.mVerticalSpan;
        int i4 = (i2 * i3) / spanCount;
        int i5 = i3 - (((i2 + 1) * i3) / spanCount);
        if (isLastRaw(recyclerView, viewLayoutPosition, spanCount, itemCount)) {
            i = this.mShowLastLine ? this.mHorizonSpan : 0;
        } else {
            i = this.mHorizonSpan;
        }
        rect.set(i4, 0, i5, i);
    }

    private int getSpanCount(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanCount();
        }
        if (!(layoutManager instanceof StaggeredGridLayoutManager)) {
            return -1;
        }
        return ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
    }

    private boolean isLastRaw(RecyclerView recyclerView, int i, int i2, int i3) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return getResult(i, i2, i3);
        }
        if (!(layoutManager instanceof StaggeredGridLayoutManager)) {
            return false;
        }
        if (((StaggeredGridLayoutManager) layoutManager).getOrientation() == 1) {
            return getResult(i, i2, i3);
        }
        return (i + 1) % i2 == 0;
    }

    private boolean getResult(int i, int i2, int i3) {
        int i4 = i3 % i2;
        return i4 == 0 ? i >= i3 - i2 : i >= i3 - i4;
    }

    /* loaded from: classes3.dex */
    public static class Builder {
        private Context mContext;
        private Resources mResources;
        private boolean mShowLastLine = true;
        private int mHorizonSpan = 0;
        private int mVerticalSpan = 0;
        private int mColor = -1;

        public Builder(Context context) {
            this.mContext = context;
            this.mResources = context.getResources();
        }

        public Builder setColorResource(@ColorRes int i) {
            setColor(ContextCompat.getColor(this.mContext, i));
            return this;
        }

        public Builder setColor(@ColorInt int i) {
            this.mColor = i;
            return this;
        }

        public Builder setVerticalSpan(@DimenRes int i) {
            this.mVerticalSpan = this.mResources.getDimensionPixelSize(i);
            return this;
        }

        public Builder setHorizontalSpan(@DimenRes int i) {
            this.mHorizonSpan = this.mResources.getDimensionPixelSize(i);
            return this;
        }

        public Builder setShowLastLine(boolean z) {
            this.mShowLastLine = z;
            return this;
        }

        public GridItemDecoration build() {
            return new GridItemDecoration(this.mHorizonSpan, this.mVerticalSpan, this.mColor, this.mShowLastLine);
        }
    }
}
