package com.tencent.liteav.beauty.p113a.p114a;

import javax.microedition.khronos.egl.EGL11;
import javax.microedition.khronos.egl.EGLSurface;

/* renamed from: com.tencent.liteav.beauty.a.a.b */
/* loaded from: classes3.dex */
public class EglSurfaceBase {

    /* renamed from: a */
    protected EglCore f2781a;

    /* renamed from: b */
    private EGLSurface f2782b = EGL11.EGL_NO_SURFACE;

    /* renamed from: c */
    private int f2783c = -1;

    /* renamed from: d */
    private int f2784d = -1;

    /* JADX INFO: Access modifiers changed from: protected */
    public EglSurfaceBase(EglCore eglCore) {
        this.f2781a = eglCore;
    }

    /* renamed from: a */
    public void m2849a(int i, int i2) {
        if (this.f2782b != EGL11.EGL_NO_SURFACE) {
            throw new IllegalStateException("surface already created");
        }
        this.f2782b = this.f2781a.m2855a(i, i2);
        this.f2783c = i;
        this.f2784d = i2;
    }

    /* renamed from: a */
    public void m2850a() {
        this.f2781a.m2853a(this.f2782b);
        this.f2782b = EGL11.EGL_NO_SURFACE;
        this.f2784d = -1;
        this.f2783c = -1;
    }

    /* renamed from: b */
    public void m2848b() {
        this.f2781a.m2851b(this.f2782b);
    }
}
