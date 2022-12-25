package com.one.tomato.thirdpart.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.support.p005v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import com.one.tomato.utils.LogUtil;

/* loaded from: classes3.dex */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private int mDividerHeight;
    private Drawable mDrawable;
    private int mOrientation;
    private Paint mPaint = new Paint(1);

    static {
        new int[1][0] = 16843284;
    }

    public DividerItemDecoration(Context context, int i, int i2, int i3) {
        this.mDividerHeight = 2;
        setOrientation(i);
        this.mDividerHeight = i2;
        LogUtil.m3785e("mDividerHeight", this.mDividerHeight + "===================");
        this.mPaint.setColor(i3);
        this.mPaint.setStyle(Paint.Style.FILL);
    }

    public void setOrientation(int i) {
        if (i < 0 || i > 2) {
            throw new IllegalArgumentException("invalid orientation");
        }
        this.mOrientation = i;
    }

    @Override // android.support.p005v7.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        super.getItemOffsets(rect, view, recyclerView, state);
        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        int viewLayoutPosition = layoutParams.getViewLayoutPosition();
        int itemCount = recyclerView.getAdapter().getItemCount();
        view.getX();
        int i = this.mOrientation;
        if (i == 0) {
            rect.set(0, 0, 0, viewLayoutPosition != itemCount + (-1) ? this.mDividerHeight : 0);
        } else if (i == 1) {
            rect.set(0, 0, viewLayoutPosition != itemCount + (-1) ? this.mDividerHeight : 0, 0);
        } else if (i != 2) {
        } else {
            int spanCount = getSpanCount(recyclerView);
            int spanIndex = layoutParams.getSpanIndex();
            if (isLastRaw(recyclerView, viewLayoutPosition, spanCount, itemCount)) {
                rect.set(0, 0, this.mDividerHeight, 0);
            } else if (spanIndex == 1) {
                rect.set(0, 0, 0, this.mDividerHeight);
            } else {
                int i2 = this.mDividerHeight;
                rect.set(0, 0, i2, i2);
            }
        }
    }

    @Override // android.support.p005v7.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        super.onDraw(canvas, recyclerView, state);
        int i = this.mOrientation;
        if (i == 1) {
            drawVertical(canvas, recyclerView);
        } else if (i == 0) {
            drawHorizontal(canvas, recyclerView);
        } else {
            drawHorizontal(canvas, recyclerView);
            drawVertical(canvas, recyclerView);
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView recyclerView) {
        int paddingLeft = recyclerView.getPaddingLeft();
        int measuredWidth = recyclerView.getMeasuredWidth() - recyclerView.getPaddingRight();
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            int bottom = childAt.getBottom() + ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) childAt.getLayoutParams())).bottomMargin;
            int i2 = this.mDividerHeight + bottom;
            Drawable drawable = this.mDrawable;
            if (drawable != null) {
                drawable.setBounds(paddingLeft, bottom, measuredWidth, i2);
                this.mDrawable.draw(canvas);
            }
            Paint paint = this.mPaint;
            if (paint != null) {
                canvas.drawRect(paddingLeft, bottom, measuredWidth, i2, paint);
            }
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView recyclerView) {
        int paddingTop = recyclerView.getPaddingTop();
        int measuredHeight = recyclerView.getMeasuredHeight() - recyclerView.getPaddingBottom();
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            int right = childAt.getRight() + ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) childAt.getLayoutParams())).rightMargin;
            int i2 = this.mDividerHeight + right;
            Drawable drawable = this.mDrawable;
            if (drawable != null) {
                drawable.setBounds(right, paddingTop, i2, measuredHeight);
                this.mDrawable.draw(canvas);
            }
            Paint paint = this.mPaint;
            if (paint != null) {
                canvas.drawRect(right, paddingTop, i2, measuredHeight, paint);
            }
        }
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
            int i4 = i3 - (i3 % i2);
            return ((GridLayoutManager) layoutManager).getOrientation() == 1 ? i >= i4 - (i4 % i2) : (i + 1) % i2 == 0;
        } else if (!(layoutManager instanceof StaggeredGridLayoutManager)) {
            return false;
        } else {
            return ((StaggeredGridLayoutManager) layoutManager).getOrientation() == 1 ? i >= i3 - (i3 % i2) : (i + 1) % i2 == 0;
        }
    }
}
