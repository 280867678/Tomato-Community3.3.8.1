package com.tomatolive.library.p136ui.view.widget.bgabanner;

import android.content.Context;
import android.widget.Scroller;

/* renamed from: com.tomatolive.library.ui.view.widget.bgabanner.BGABannerScroller */
/* loaded from: classes4.dex */
public class BGABannerScroller extends Scroller {
    private int mDuration;

    public BGABannerScroller(Context context, int i) {
        super(context);
        this.mDuration = 1000;
        this.mDuration = i;
    }

    @Override // android.widget.Scroller
    public void startScroll(int i, int i2, int i3, int i4) {
        super.startScroll(i, i2, i3, i4, this.mDuration);
    }

    @Override // android.widget.Scroller
    public void startScroll(int i, int i2, int i3, int i4, int i5) {
        super.startScroll(i, i2, i3, i4, this.mDuration);
    }
}
