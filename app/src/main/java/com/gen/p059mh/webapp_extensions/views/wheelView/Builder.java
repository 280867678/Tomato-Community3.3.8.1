package com.gen.p059mh.webapp_extensions.views.wheelView;

import java.util.List;

/* renamed from: com.gen.mh.webapp_extensions.views.wheelView.Builder */
/* loaded from: classes2.dex */
public class Builder {
    List data;
    String fontPath;
    boolean hasAtmospheric;
    boolean hasCurtain;
    boolean hasIndicator;
    boolean hasSameWidth;
    boolean isCurved;
    boolean isCyclic;
    int mCurtainColor;
    int mIndicatorColor;
    int mIndicatorSizeDp;
    int mItemAlign;
    int mItemSpaceDp;
    int mItemTextColor;
    int mItemTextSizeDp;
    String mMaxWidthText;
    int mSelectedItemPosition;
    int mSelectedItemTextColor;
    int mTextMaxWidthPosition;
    int mVisibleItemCount;

    public Builder setmItemTextSizeDp(int i) {
        this.mItemTextSizeDp = i;
        return this;
    }

    public Builder setmSelectedItemTextColor(int i) {
        this.mSelectedItemTextColor = i;
        return this;
    }

    public Builder setmItemTextColor(int i) {
        this.mItemTextColor = i;
        return this;
    }

    public Builder setCyclic(boolean z) {
        this.isCyclic = z;
        return this;
    }

    public Builder setHasAtmospheric(boolean z) {
        this.hasAtmospheric = z;
        return this;
    }

    public Builder setCurved(boolean z) {
        this.isCurved = z;
        return this;
    }

    public static Builder createBuilder() {
        return new Builder();
    }
}
