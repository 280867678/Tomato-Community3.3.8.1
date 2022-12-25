package com.tomatolive.library.p136ui.view.widget;

import android.content.Context;
import android.support.p002v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/* renamed from: com.tomatolive.library.ui.view.widget.NoTouchViewPager */
/* loaded from: classes4.dex */
public class NoTouchViewPager extends ViewPager {
    @Override // android.support.p002v4.view.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override // android.support.p002v4.view.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public NoTouchViewPager(Context context) {
        super(context);
    }

    public NoTouchViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
