package net.lucode.hackware.magicindicator.abs;

/* loaded from: classes4.dex */
public interface IPagerNavigator {
    void onAttachToMagicIndicator();

    void onDetachFromMagicIndicator();

    void onPageScrollStateChanged(int i);

    void onPageScrolled(int i, float f, int i2);

    void onPageSelected(int i);
}
