package com.tencent.liteav.p121f;

import android.graphics.SurfaceTexture;
import android.opengl.GLUtils;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

/* compiled from: EglCore.java */
/* renamed from: com.tencent.liteav.f.d */
/* loaded from: classes3.dex */
public class C3469d {

    /* renamed from: a */
    private int f3845a = 12440;

    /* renamed from: b */
    private int f3846b = 4;

    /* renamed from: c */
    private EGL10 f3847c;

    /* renamed from: d */
    private EGLDisplay f3848d;

    /* renamed from: e */
    private EGLConfig f3849e;

    /* renamed from: f */
    private EGLSurface f3850f;

    /* renamed from: g */
    private EGLContext f3851g;

    /* renamed from: a */
    public void m1879a(SurfaceTexture surfaceTexture) {
        this.f3847c = (EGL10) EGLContext.getEGL();
        this.f3848d = this.f3847c.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        this.f3847c.eglInitialize(this.f3848d, new int[2]);
        this.f3849e = m1876c();
        this.f3850f = this.f3847c.eglCreateWindowSurface(this.f3848d, this.f3849e, surfaceTexture, null);
        this.f3851g = m1878a(this.f3847c, this.f3848d, this.f3849e, EGL10.EGL_NO_CONTEXT);
        EGLSurface eGLSurface = this.f3850f;
        if (eGLSurface == null || eGLSurface == EGL10.EGL_NO_SURFACE) {
            throw new RuntimeException("GL error:" + GLUtils.getEGLErrorString(this.f3847c.eglGetError()));
        } else if (this.f3847c.eglMakeCurrent(this.f3848d, eGLSurface, eGLSurface, this.f3851g)) {
        } else {
            throw new RuntimeException("GL Make current Error" + GLUtils.getEGLErrorString(this.f3847c.eglGetError()));
        }
    }

    /* renamed from: a */
    public void m1880a() {
        EGL10 egl10 = this.f3847c;
        if (egl10 == null) {
            return;
        }
        EGLDisplay eGLDisplay = this.f3848d;
        EGLSurface eGLSurface = EGL10.EGL_NO_SURFACE;
        egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL10.EGL_NO_CONTEXT);
        this.f3847c.eglDestroyContext(this.f3848d, this.f3851g);
        this.f3847c.eglDestroySurface(this.f3848d, this.f3850f);
        this.f3847c.eglTerminate(this.f3848d);
        this.f3847c = null;
    }

    /* renamed from: a */
    private EGLContext m1878a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, EGLContext eGLContext) {
        return egl10.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, new int[]{this.f3845a, 2, 12344});
    }

    /* renamed from: c */
    private EGLConfig m1876c() {
        int[] iArr = new int[1];
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        if (!this.f3847c.eglChooseConfig(this.f3848d, m1875d(), eGLConfigArr, 1, iArr)) {
            throw new IllegalArgumentException("Failed to choose config:" + GLUtils.getEGLErrorString(this.f3847c.eglGetError()));
        } else if (iArr[0] <= 0) {
            return null;
        } else {
            return eGLConfigArr[0];
        }
    }

    /* renamed from: d */
    private int[] m1875d() {
        return new int[]{12352, this.f3846b, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12326, 0, 12344};
    }

    /* renamed from: b */
    public void m1877b() {
        EGLDisplay eGLDisplay;
        EGLSurface eGLSurface;
        EGL10 egl10 = this.f3847c;
        if (egl10 == null || (eGLDisplay = this.f3848d) == null || (eGLSurface = this.f3850f) == null) {
            return;
        }
        egl10.eglSwapBuffers(eGLDisplay, eGLSurface);
    }
}
