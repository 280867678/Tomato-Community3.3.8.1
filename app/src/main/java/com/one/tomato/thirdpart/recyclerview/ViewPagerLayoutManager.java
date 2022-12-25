package com.one.tomato.thirdpart.recyclerview;

import android.content.Context;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.PagerSnapHelper;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;

/* loaded from: classes3.dex */
public class ViewPagerLayoutManager extends LinearLayoutManager {
    private RecyclerView.OnChildAttachStateChangeListener mChildAttachStateChangeListener = new RecyclerView.OnChildAttachStateChangeListener() { // from class: com.one.tomato.thirdpart.recyclerview.ViewPagerLayoutManager.1
        @Override // android.support.p005v7.widget.RecyclerView.OnChildAttachStateChangeListener
        public void onChildViewAttachedToWindow(View view) {
            if (ViewPagerLayoutManager.this.mOnViewPagerListener == null || ViewPagerLayoutManager.this.getChildCount() != 1) {
                return;
            }
            ViewPagerLayoutManager.this.mOnViewPagerListener.onInitComplete();
        }

        @Override // android.support.p005v7.widget.RecyclerView.OnChildAttachStateChangeListener
        public void onChildViewDetachedFromWindow(View view) {
            if (ViewPagerLayoutManager.this.mDrift >= 0) {
                if (ViewPagerLayoutManager.this.mOnViewPagerListener == null) {
                    return;
                }
                ViewPagerLayoutManager.this.mOnViewPagerListener.onPageRelease(true, ViewPagerLayoutManager.this.getPosition(view));
            } else if (ViewPagerLayoutManager.this.mOnViewPagerListener == null) {
            } else {
                ViewPagerLayoutManager.this.mOnViewPagerListener.onPageRelease(false, ViewPagerLayoutManager.this.getPosition(view));
            }
        }
    };
    private int mDrift;
    private ViewPagerLayouListener mOnViewPagerListener;
    private PagerSnapHelper mPagerSnapHelper;
    private RecyclerView mRecyclerView;

    public ViewPagerLayoutManager(Context context, int i) {
        super(context, i, false);
        init();
    }

    private void init() {
        this.mPagerSnapHelper = new PagerSnapHelper();
    }

    @Override // android.support.p005v7.widget.RecyclerView.LayoutManager
    public void onAttachedToWindow(RecyclerView recyclerView) {
        super.onAttachedToWindow(recyclerView);
        this.mPagerSnapHelper.attachToRecyclerView(recyclerView);
        this.mRecyclerView = recyclerView;
        this.mRecyclerView.addOnChildAttachStateChangeListener(this.mChildAttachStateChangeListener);
    }

    @Override // android.support.p005v7.widget.LinearLayoutManager, android.support.p005v7.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
    }

    @Override // android.support.p005v7.widget.RecyclerView.LayoutManager
    public void onScrollStateChanged(int i) {
        View findSnapView;
        boolean z = true;
        if (i == 0) {
            View findSnapView2 = this.mPagerSnapHelper.findSnapView(this);
            if (findSnapView2 == null) {
                return;
            }
            int position = getPosition(findSnapView2);
            if (this.mOnViewPagerListener == null || getChildCount() != 1) {
                return;
            }
            ViewPagerLayouListener viewPagerLayouListener = this.mOnViewPagerListener;
            if (position != getItemCount() - 1) {
                z = false;
            }
            viewPagerLayouListener.onPageSelected(position, z);
        } else if (i != 1) {
            if (i != 2 || (findSnapView = this.mPagerSnapHelper.findSnapView(this)) == null) {
                return;
            }
            int position2 = getPosition(findSnapView);
            ViewPagerLayouListener viewPagerLayouListener2 = this.mOnViewPagerListener;
            if (viewPagerLayouListener2 == null) {
                return;
            }
            viewPagerLayouListener2.onPageSettling(position2);
        } else {
            View findSnapView3 = this.mPagerSnapHelper.findSnapView(this);
            if (findSnapView3 == null) {
                return;
            }
            int position3 = getPosition(findSnapView3);
            ViewPagerLayouListener viewPagerLayouListener3 = this.mOnViewPagerListener;
            if (viewPagerLayouListener3 == null) {
                return;
            }
            viewPagerLayouListener3.onPageDragging(position3);
        }
    }

    @Override // android.support.p005v7.widget.LinearLayoutManager, android.support.p005v7.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.mDrift = i;
        return super.scrollVerticallyBy(i, recycler, state);
    }

    @Override // android.support.p005v7.widget.LinearLayoutManager, android.support.p005v7.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.mDrift = i;
        return super.scrollHorizontallyBy(i, recycler, state);
    }

    public void setOnViewPagerListener(ViewPagerLayouListener viewPagerLayouListener) {
        this.mOnViewPagerListener = viewPagerLayouListener;
    }
}
