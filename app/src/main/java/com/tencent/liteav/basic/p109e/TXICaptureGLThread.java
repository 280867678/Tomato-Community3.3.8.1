package com.tencent.liteav.basic.p109e;

import android.graphics.SurfaceTexture;
import javax.microedition.khronos.egl.EGLContext;

/* renamed from: com.tencent.liteav.basic.e.l */
/* loaded from: classes3.dex */
public interface TXICaptureGLThread {
    /* renamed from: a */
    void mo1026a();

    /* renamed from: a */
    void mo1025a(int i);

    /* renamed from: a */
    void mo1023a(int i, boolean z, int i2, int i3, int i4);

    /* renamed from: a */
    void mo1017a(Runnable runnable);

    /* renamed from: a */
    void mo1015a(boolean z);

    EGLContext getGLContext();

    SurfaceTexture getSurfaceTexture();

    void setSurfaceTextureListener(TXIGLSurfaceTextureListener tXIGLSurfaceTextureListener);
}
