package com.tomatolive.library.p136ui.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/* renamed from: com.tomatolive.library.ui.view.widget.CustomVerticalViewPager */
/* loaded from: classes4.dex */
public class CustomVerticalViewPager extends VerticalViewPager {
    private boolean isScroll = true;
    private boolean disallowIntercept = false;

    public CustomVerticalViewPager(Context context) {
        super(context);
    }

    public CustomVerticalViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.tomatolive.library.p136ui.view.widget.VerticalViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.isScroll && super.onTouchEvent(motionEvent);
    }

    @Override // com.tomatolive.library.p136ui.view.widget.VerticalViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return !this.disallowIntercept && this.isScroll && super.onInterceptTouchEvent(motionEvent);
    }

    public boolean isScroll() {
        return this.isScroll;
    }

    public void setScroll(boolean z) {
        this.isScroll = z;
    }

    public void disallowInterceptTouchEvent(boolean z) {
        this.disallowIntercept = z;
    }
}
