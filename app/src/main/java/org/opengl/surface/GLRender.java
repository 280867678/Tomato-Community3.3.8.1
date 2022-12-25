package org.opengl.surface;

import android.view.Surface;

/* loaded from: classes4.dex */
public class GLRender {
    long canvas;
    long render;

    public native void createCanvas(int i, int i2, float f);

    public native boolean draw();

    public native byte[] getImageData(float[] fArr);

    public native Object nativeGetResult(String str);

    public native void nativeInput(String str);

    public native void nativeOnPause();

    public native void nativeOnStart();

    public native void nativeOnStop();

    public native void nativeSetSize(int i, int i2);

    public native void nativeSetSurface(Surface surface);

    public native String nativeSetup(String str, Object obj, String str2);

    public GLRender() {
        nativeOnStart();
    }

    static {
        System.loadLibrary("render");
    }

    public long getRender() {
        return this.render;
    }

    public void setRender(long j) {
        this.render = j;
    }

    public long getCanvas() {
        return this.canvas;
    }

    public void setCanvas(long j) {
        this.canvas = j;
    }
}
