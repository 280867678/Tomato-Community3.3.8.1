package com.tencent.liteav.beauty.p113a.p114a;

import android.view.Surface;

/* renamed from: com.tencent.liteav.beauty.a.a.c */
/* loaded from: classes3.dex */
public class WindowSurface extends EglSurfaceBase {

    /* renamed from: b */
    private Surface f2785b;

    /* renamed from: c */
    private boolean f2786c;

    public WindowSurface(EglCore eglCore, int i, int i2, boolean z) {
        super(eglCore);
        m2849a(i, i2);
        this.f2786c = z;
    }

    /* renamed from: c */
    public void m2847c() {
        m2850a();
        Surface surface = this.f2785b;
        if (surface != null) {
            if (this.f2786c) {
                surface.release();
            }
            this.f2785b = null;
        }
    }
}
