package com.one.tomato.widget.mzbanner.transformer;

import android.support.p002v4.view.ViewPager;
import android.view.View;

/* loaded from: classes3.dex */
public class ScaleYTransformer implements ViewPager.PageTransformer {
    @Override // android.support.p002v4.view.ViewPager.PageTransformer
    public void transformPage(View view, float f) {
        if (f < -1.0f) {
            view.setScaleY(0.9f);
        } else if (f <= 1.0f) {
            view.setScaleY(Math.max(0.9f, 1.0f - Math.abs(f)));
        } else {
            view.setScaleY(0.9f);
        }
    }
}
