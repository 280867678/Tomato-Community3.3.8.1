package com.one.tomato.thirdpart.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.p002v4.view.MotionEventCompat;
import android.support.p002v4.view.ViewConfigurationCompat;
import android.support.p005v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/* loaded from: classes3.dex */
public class BetterHorScrollRecyclerView extends RecyclerView {
    private int mInitialTouchX;
    private int mInitialTouchY;
    private int mScrollPointerId;
    private int mTouchSlop;

    public BetterHorScrollRecyclerView(Context context) {
        this(context, null);
    }

    public BetterHorScrollRecyclerView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BetterHorScrollRecyclerView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mScrollPointerId = -1;
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override // android.support.p005v7.widget.RecyclerView
    public void setScrollingTouchSlop(int i) {
        super.setScrollingTouchSlop(i);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        if (i == 0) {
            this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        } else if (i != 1) {
        } else {
            this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);
        }
    }

    @Override // android.support.p005v7.widget.RecyclerView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
        if (actionMasked == 0) {
            this.mScrollPointerId = MotionEventCompat.getPointerId(motionEvent, 0);
            this.mInitialTouchX = (int) (motionEvent.getX() + 0.5f);
            this.mInitialTouchY = (int) (motionEvent.getY() + 0.5f);
            return super.onInterceptTouchEvent(motionEvent);
        } else if (actionMasked != 2) {
            if (actionMasked == 5) {
                this.mScrollPointerId = MotionEventCompat.getPointerId(motionEvent, actionIndex);
                this.mInitialTouchX = (int) (MotionEventCompat.getX(motionEvent, actionIndex) + 0.5f);
                this.mInitialTouchY = (int) (MotionEventCompat.getY(motionEvent, actionIndex) + 0.5f);
                return super.onInterceptTouchEvent(motionEvent);
            }
            return super.onInterceptTouchEvent(motionEvent);
        } else {
            int findPointerIndex = MotionEventCompat.findPointerIndex(motionEvent, this.mScrollPointerId);
            if (findPointerIndex < 0) {
                return false;
            }
            int x = (int) (MotionEventCompat.getX(motionEvent, findPointerIndex) + 0.5f);
            int y = (int) (MotionEventCompat.getY(motionEvent, findPointerIndex) + 0.5f);
            if (getScrollState() != 1) {
                int i = x - this.mInitialTouchX;
                int i2 = y - this.mInitialTouchY;
                boolean canScrollHorizontally = getLayoutManager().canScrollHorizontally();
                boolean canScrollVertically = getLayoutManager().canScrollVertically();
                boolean z = canScrollHorizontally && Math.abs(i) > this.mTouchSlop && (Math.abs(i) >= Math.abs(i2) || canScrollVertically);
                if (canScrollVertically && Math.abs(i2) > this.mTouchSlop && (Math.abs(i2) >= Math.abs(i) || canScrollHorizontally)) {
                    z = true;
                }
                return z && super.onInterceptTouchEvent(motionEvent);
            }
            return super.onInterceptTouchEvent(motionEvent);
        }
    }
}
