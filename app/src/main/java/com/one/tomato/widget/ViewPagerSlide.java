package com.one.tomato.widget;

import android.content.Context;
import android.support.p002v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/* loaded from: classes3.dex */
public class ViewPagerSlide extends ViewPager {
    private int downX;
    private int downY;
    private boolean isSlide = false;
    private boolean isS = true;

    public void setSlide(boolean z) {
        this.isSlide = z;
    }

    public ViewPagerSlide(Context context) {
        super(context);
    }

    public ViewPagerSlide(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.support.p002v4.view.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.isSlide) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            this.isS = true;
            this.downX = (int) motionEvent.getX();
            this.downY = (int) motionEvent.getY();
        } else if (action == 2) {
            if (Math.abs(this.downX - ((int) motionEvent.getX())) > Math.abs(this.downY - ((int) motionEvent.getY()))) {
                this.isS = false;
            } else {
                this.isS = true;
            }
        }
        if (this.isS) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.support.p002v4.view.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.isSlide) {
            return super.onTouchEvent(motionEvent);
        }
        return false;
    }
}
