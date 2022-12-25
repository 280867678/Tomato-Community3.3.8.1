package com.one.tomato.widget.mzbanner.transformer;

import android.support.p002v4.view.ViewPager;
import android.view.View;

/* loaded from: classes3.dex */
public class CoverModeTransformer implements ViewPager.PageTransformer {
    private int mCoverWidth;
    private ViewPager mViewPager;
    private float reduceX = 0.0f;
    private float itemWidth = 0.0f;
    private float offsetPosition = 0.0f;
    private float mScaleMax = 1.0f;
    private float mScaleMin = 0.9f;

    public CoverModeTransformer(ViewPager viewPager) {
        this.mViewPager = viewPager;
    }

    @Override // android.support.p002v4.view.ViewPager.PageTransformer
    public void transformPage(View view, float f) {
        if (this.offsetPosition == 0.0f) {
            float paddingLeft = this.mViewPager.getPaddingLeft();
            this.offsetPosition = paddingLeft / ((this.mViewPager.getMeasuredWidth() - paddingLeft) - this.mViewPager.getPaddingRight());
        }
        float f2 = f - this.offsetPosition;
        if (this.itemWidth == 0.0f) {
            this.itemWidth = view.getWidth();
            this.reduceX = (((2.0f - this.mScaleMax) - this.mScaleMin) * this.itemWidth) / 2.0f;
        }
        if (f2 <= -1.0f) {
            view.setTranslationX(this.reduceX + this.mCoverWidth);
            view.setScaleX(this.mScaleMin);
            view.setScaleY(this.mScaleMin);
            return;
        }
        double d = f2;
        if (d <= 1.0d) {
            float abs = (this.mScaleMax - this.mScaleMin) * Math.abs(1.0f - Math.abs(f2));
            float f3 = (-this.reduceX) * f2;
            if (d <= -0.5d) {
                view.setTranslationX(f3 + ((this.mCoverWidth * Math.abs(Math.abs(f2) - 0.5f)) / 0.5f));
            } else if (f2 <= 0.0f) {
                view.setTranslationX(f3);
            } else if (d >= 0.5d) {
                view.setTranslationX(f3 - ((this.mCoverWidth * Math.abs(Math.abs(f2) - 0.5f)) / 0.5f));
            } else {
                view.setTranslationX(f3);
            }
            view.setScaleX(this.mScaleMin + abs);
            view.setScaleY(abs + this.mScaleMin);
            return;
        }
        view.setScaleX(this.mScaleMin);
        view.setScaleY(this.mScaleMin);
        view.setTranslationX((-this.reduceX) - this.mCoverWidth);
    }
}
