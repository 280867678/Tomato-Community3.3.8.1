package com.bumptech.glide.integration.webp;

import android.graphics.Bitmap;
import android.support.annotation.Keep;

@Keep
/* loaded from: classes2.dex */
public class WebpFrame {
    static final int FRAME_DURATION_MS_FOR_MIN = 100;
    static final int MIN_FRAME_DURATION_MS = 20;
    boolean blendPreviousFrame;
    int delay;
    boolean disposeBackgroundColor;

    /* renamed from: ih */
    int f1203ih;

    /* renamed from: iw */
    int f1204iw;

    /* renamed from: ix */
    int f1205ix;

    /* renamed from: iy */
    int f1206iy;
    @Keep
    private long mNativePtr;

    private native void nativeDispose();

    private native void nativeFinalize();

    private native void nativeRenderFrame(int i, int i2, Bitmap bitmap);

    WebpFrame(long j, int i, int i2, int i3, int i4, int i5, boolean z, boolean z2) {
        this.mNativePtr = j;
        this.f1205ix = i;
        this.f1206iy = i2;
        this.f1204iw = i3;
        this.f1203ih = i4;
        this.delay = i5;
        this.blendPreviousFrame = z;
        this.disposeBackgroundColor = z2;
        fixFrameDuration();
    }

    private void fixFrameDuration() {
        if (this.delay < 20) {
            this.delay = 100;
        }
    }

    protected void finalize() throws Throwable {
        nativeFinalize();
    }

    public void dispose() {
        nativeDispose();
    }

    public void renderFrame(int i, int i2, Bitmap bitmap) {
        nativeRenderFrame(i, i2, bitmap);
    }

    public int getWidth() {
        return this.f1204iw;
    }

    public int getHeight() {
        return this.f1203ih;
    }

    public int getDurationMs() {
        return this.delay;
    }

    public int getXOffest() {
        return this.f1205ix;
    }

    public int getYOffest() {
        return this.f1206iy;
    }

    public boolean shouldDisposeToBackgroundColor() {
        return this.disposeBackgroundColor;
    }

    public boolean isBlendWithPreviousFrame() {
        return this.blendPreviousFrame;
    }
}
