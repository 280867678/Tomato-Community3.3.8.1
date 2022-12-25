package com.tomatolive.library.p136ui.view.widget;

import android.content.Context;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

/* renamed from: com.tomatolive.library.ui.view.widget.ScaleTransitionPagerTitleView */
/* loaded from: classes4.dex */
public class ScaleTransitionPagerTitleView extends ColorTransitionPagerTitleView {
    private float mMinScale = 0.85f;

    public ScaleTransitionPagerTitleView(Context context) {
        super(context);
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView, net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView, net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
    public void onEnter(int i, int i2, float f, boolean z) {
        super.onEnter(i, i2, f, z);
        float f2 = this.mMinScale;
        setScaleX(f2 + ((1.0f - f2) * f));
        float f3 = this.mMinScale;
        setScaleY(f3 + ((1.0f - f3) * f));
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView, net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView, net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
    public void onLeave(int i, int i2, float f, boolean z) {
        super.onLeave(i, i2, f, z);
        setScaleX(((this.mMinScale - 1.0f) * f) + 1.0f);
        setScaleY(((this.mMinScale - 1.0f) * f) + 1.0f);
    }

    public float getMinScale() {
        return this.mMinScale;
    }

    public void setMinScale(float f) {
        this.mMinScale = f;
    }
}
