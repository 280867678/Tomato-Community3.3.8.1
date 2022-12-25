package net.lucode.hackware.magicindicator.buildins.commonnavigator.model;

/* loaded from: classes4.dex */
public class PositionData {
    public int mBottom;
    public int mContentBottom;
    public int mContentLeft;
    public int mContentRight;
    public int mContentTop;
    public int mLeft;
    public int mRight;
    public int mTop;

    public int width() {
        return this.mRight - this.mLeft;
    }

    public int horizontalCenter() {
        return this.mLeft + (width() / 2);
    }
}
