package com.facebook.imageutils;

import android.graphics.ColorSpace;
import android.util.Pair;

/* loaded from: classes2.dex */
public class ImageMetaData {
    private final ColorSpace mColorSpace;
    private final Pair<Integer, Integer> mDimensions;

    public ImageMetaData(int i, int i2, ColorSpace colorSpace) {
        this.mDimensions = (i == -1 || i2 == -1) ? null : new Pair<>(Integer.valueOf(i), Integer.valueOf(i2));
        this.mColorSpace = colorSpace;
    }

    public Pair<Integer, Integer> getDimensions() {
        return this.mDimensions;
    }

    public ColorSpace getColorSpace() {
        return this.mColorSpace;
    }
}
