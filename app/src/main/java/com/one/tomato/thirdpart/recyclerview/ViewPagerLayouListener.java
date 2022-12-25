package com.one.tomato.thirdpart.recyclerview;

/* loaded from: classes3.dex */
public interface ViewPagerLayouListener {
    void onInitComplete();

    void onPageDragging(int i);

    void onPageRelease(boolean z, int i);

    void onPageSelected(int i, boolean z);

    void onPageSettling(int i);
}
