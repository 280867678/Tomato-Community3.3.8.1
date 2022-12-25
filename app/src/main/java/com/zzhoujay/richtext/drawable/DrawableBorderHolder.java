package com.zzhoujay.richtext.drawable;

import android.support.annotation.ColorInt;
import android.support.p002v4.view.ViewCompat;

/* loaded from: classes4.dex */
public class DrawableBorderHolder {
    @ColorInt
    private int borderColor;
    private float borderSize;
    private float radius;
    private boolean showBorder;

    /* JADX INFO: Access modifiers changed from: package-private */
    public DrawableBorderHolder(boolean z, float f, @ColorInt int i, float f2) {
        this.showBorder = z;
        this.borderSize = f;
        this.borderColor = i;
        this.radius = f2;
    }

    public DrawableBorderHolder() {
        this(false, 5.0f, ViewCompat.MEASURED_STATE_MASK, 0.0f);
    }

    public DrawableBorderHolder(DrawableBorderHolder drawableBorderHolder) {
        this(drawableBorderHolder.showBorder, drawableBorderHolder.borderSize, drawableBorderHolder.borderColor, drawableBorderHolder.radius);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isShowBorder() {
        return this.showBorder;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getBorderSize() {
        return this.borderSize;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @ColorInt
    public int getBorderColor() {
        return this.borderColor;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getRadius() {
        return this.radius;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void set(DrawableBorderHolder drawableBorderHolder) {
        this.showBorder = drawableBorderHolder.showBorder;
        this.borderSize = drawableBorderHolder.borderSize;
        this.borderColor = drawableBorderHolder.borderColor;
        this.radius = drawableBorderHolder.radius;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DrawableBorderHolder)) {
            return false;
        }
        DrawableBorderHolder drawableBorderHolder = (DrawableBorderHolder) obj;
        return this.showBorder == drawableBorderHolder.showBorder && Float.compare(drawableBorderHolder.borderSize, this.borderSize) == 0 && this.borderColor == drawableBorderHolder.borderColor && Float.compare(drawableBorderHolder.radius, this.radius) == 0;
    }

    public int hashCode() {
        int i = (this.showBorder ? 1 : 0) * 31;
        float f = this.borderSize;
        int i2 = 0;
        int floatToIntBits = (((i + (f != 0.0f ? Float.floatToIntBits(f) : 0)) * 31) + this.borderColor) * 31;
        float f2 = this.radius;
        if (f2 != 0.0f) {
            i2 = Float.floatToIntBits(f2);
        }
        return floatToIntBits + i2;
    }
}
