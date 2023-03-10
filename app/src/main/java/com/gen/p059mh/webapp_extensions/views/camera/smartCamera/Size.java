package com.gen.p059mh.webapp_extensions.views.camera.smartCamera;

import android.support.annotation.NonNull;

/* renamed from: com.gen.mh.webapp_extensions.views.camera.smartCamera.Size */
/* loaded from: classes2.dex */
public class Size implements Comparable<Size> {
    private final int mHeight;
    private final int mWidth;

    public Size(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Size)) {
            return false;
        }
        Size size = (Size) obj;
        return this.mWidth == size.mWidth && this.mHeight == size.mHeight;
    }

    public String toString() {
        return this.mWidth + "x" + this.mHeight;
    }

    public int hashCode() {
        int i = this.mHeight;
        int i2 = this.mWidth;
        return i ^ ((i2 >>> 16) | (i2 << 16));
    }

    @Override // java.lang.Comparable
    public int compareTo(@NonNull Size size) {
        return (this.mWidth * this.mHeight) - (size.mWidth * size.mHeight);
    }
}
