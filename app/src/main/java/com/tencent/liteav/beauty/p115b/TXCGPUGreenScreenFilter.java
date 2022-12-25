package com.tencent.liteav.beauty.p115b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.p109e.TXCGPUOESTextureFilter;

/* renamed from: com.tencent.liteav.beauty.b.m */
/* loaded from: classes3.dex */
public class TXCGPUGreenScreenFilter {

    /* renamed from: i */
    private static String f3086i = "GPUGreenScreen";

    /* renamed from: a */
    private int f3087a;

    /* renamed from: b */
    private int f3088b;

    /* renamed from: c */
    private boolean f3089c;

    /* renamed from: d */
    private TXCGPUVideoPlayerFilter f3090d;

    /* renamed from: e */
    private boolean f3091e;

    /* renamed from: f */
    private TXCGPUOESTextureFilter f3092f;

    /* renamed from: g */
    private TXCGPUColorScreenFilter f3093g;

    /* renamed from: h */
    private boolean f3094h;

    /* renamed from: a */
    public int m2683a(int i) {
        return i;
    }

    /* renamed from: b */
    private void m2682b() {
        TXCGPUVideoPlayerFilter tXCGPUVideoPlayerFilter = this.f3090d;
        if (tXCGPUVideoPlayerFilter != null) {
            tXCGPUVideoPlayerFilter.m2738a();
        }
        this.f3090d = null;
        this.f3091e = false;
        this.f3094h = false;
    }

    /* renamed from: a */
    public void m2684a() {
        m2682b();
        m2681c();
        TXCGPUOESTextureFilter tXCGPUOESTextureFilter = this.f3092f;
        if (tXCGPUOESTextureFilter != null) {
            tXCGPUOESTextureFilter.mo1351e();
            this.f3092f = null;
        }
        TXCGPUColorScreenFilter tXCGPUColorScreenFilter = this.f3093g;
        if (tXCGPUColorScreenFilter != null) {
            tXCGPUColorScreenFilter.mo1351e();
            this.f3093g = null;
        }
        this.f3089c = false;
    }

    /* renamed from: c */
    private void m2681c() {
        int i = this.f3088b;
        if (i != -1 && i != this.f3087a) {
            GLES20.glDeleteTextures(1, new int[]{i}, 0);
            this.f3088b = -1;
        }
        int i2 = this.f3087a;
        if (i2 != -1) {
            GLES20.glDeleteTextures(1, new int[]{i2}, 0);
            this.f3087a = -1;
        }
    }
}
