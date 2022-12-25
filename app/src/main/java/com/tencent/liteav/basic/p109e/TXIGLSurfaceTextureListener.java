package com.tencent.liteav.basic.p109e;

import android.graphics.SurfaceTexture;

/* renamed from: com.tencent.liteav.basic.e.m */
/* loaded from: classes3.dex */
public interface TXIGLSurfaceTextureListener {
    void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture);

    void onSurfaceTextureDestroy(SurfaceTexture surfaceTexture);

    int onTextureProcess(int i, float[] fArr);
}
