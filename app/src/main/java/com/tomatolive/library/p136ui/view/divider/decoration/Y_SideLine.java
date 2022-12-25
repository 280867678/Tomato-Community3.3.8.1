package com.tomatolive.library.p136ui.view.divider.decoration;

import android.support.annotation.ColorInt;

/* renamed from: com.tomatolive.library.ui.view.divider.decoration.Y_SideLine */
/* loaded from: classes3.dex */
public class Y_SideLine {
    public int color;
    public float endPaddingDp;
    public boolean isHave;
    public float startPaddingDp;
    public float widthDp;

    public Y_SideLine(boolean z, @ColorInt int i, float f, float f2, float f3) {
        this.isHave = false;
        this.isHave = z;
        this.color = i;
        this.widthDp = f;
        this.startPaddingDp = f2;
        this.endPaddingDp = f3;
    }

    public float getStartPaddingDp() {
        return this.startPaddingDp;
    }

    public void setStartPaddingDp(float f) {
        this.startPaddingDp = f;
    }

    public float getEndPaddingDp() {
        return this.endPaddingDp;
    }

    public void setEndPaddingDp(float f) {
        this.endPaddingDp = f;
    }

    public boolean isHave() {
        return this.isHave;
    }

    public void setHave(boolean z) {
        this.isHave = z;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int i) {
        this.color = i;
    }

    public float getWidthDp() {
        return this.widthDp;
    }

    public void setWidthDp(float f) {
        this.widthDp = f;
    }
}
