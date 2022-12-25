package com.one.tomato.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.luck.picture.lib.widget.PreviewViewPager;

/* loaded from: classes3.dex */
public class NoScorllViewPage extends PreviewViewPager {
    private boolean noScroll = false;

    public NoScorllViewPage(Context context) {
        super(context);
    }

    public NoScorllViewPage(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setNoScroll(boolean z) {
        this.noScroll = z;
    }

    @Override // com.luck.picture.lib.widget.PreviewViewPager, android.support.p002v4.view.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.noScroll) {
            return false;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // com.luck.picture.lib.widget.PreviewViewPager, android.support.p002v4.view.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.noScroll) {
            return false;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public void scrollTo(int i, int i2) {
        super.scrollTo(i, i2);
    }

    @Override // android.support.p002v4.view.ViewPager
    public void setCurrentItem(int i, boolean z) {
        super.setCurrentItem(i, z);
    }
}
