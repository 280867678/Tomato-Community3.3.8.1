package net.lucode.hackware.magicindicator;

import android.support.p002v4.view.ViewPager;

/* loaded from: classes4.dex */
public class ViewPagerHelper {
    public static void bind(final MagicIndicator magicIndicator, ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: net.lucode.hackware.magicindicator.ViewPagerHelper.1
            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
                MagicIndicator.this.onPageScrolled(i, f, i2);
            }

            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                MagicIndicator.this.onPageSelected(i);
            }

            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
                MagicIndicator.this.onPageScrollStateChanged(i);
            }
        });
    }
}
