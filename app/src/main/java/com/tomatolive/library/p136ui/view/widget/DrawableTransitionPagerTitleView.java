package com.tomatolive.library.p136ui.view.widget;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import com.tomatolive.library.R$drawable;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

/* renamed from: com.tomatolive.library.ui.view.widget.DrawableTransitionPagerTitleView */
/* loaded from: classes4.dex */
public class DrawableTransitionPagerTitleView extends SimplePagerTitleView {
    public DrawableTransitionPagerTitleView(Context context) {
        super(context);
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView, net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
    public void onSelected(int i, int i2) {
        super.onSelected(i, i2);
        setBackground(ContextCompat.getDrawable(getContext(), R$drawable.fq_achieve_shape_bg_wear_center_btn));
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView, net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
    public void onDeselected(int i, int i2) {
        super.onDeselected(i, i2);
        setBackground(null);
    }
}
