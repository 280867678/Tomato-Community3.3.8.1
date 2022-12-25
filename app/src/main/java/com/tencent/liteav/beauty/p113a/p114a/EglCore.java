package com.tencent.liteav.beauty.p113a.p114a;

import com.tencent.liteav.basic.log.TXCLog;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGL11;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

/* renamed from: com.tencent.liteav.beauty.a.a.a */
/* loaded from: classes3.dex */
public class EglCore {

    /* renamed from: a */
    private EGL10 f2776a;

    /* renamed from: b */
    private final EGLContext f2777b;

    /* renamed from: c */
    private EGLDisplay f2778c;

    /* renamed from: d */
    private EGLConfig f2779d;

    /* renamed from: e */
    private EGLSurface f2780e;

    public EglCore() {
        this(null);
    }

    public EglCore(EGLConfig eGLConfig) {
        this.f2776a = (EGL10) EGLContext.getEGL();
        this.f2778c = this.f2776a.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        EGLDisplay eGLDisplay = this.f2778c;
        if (eGLDisplay == EGL10.EGL_NO_DISPLAY) {
            throw new RuntimeException("unable to get EGL10 display");
        }
        if (!this.f2776a.eglInitialize(eGLDisplay, new int[2])) {
            this.f2778c = null;
            throw new RuntimeException("unable to initialize EGL10");
        }
        if (eGLConfig != null) {
            this.f2779d = eGLConfig;
        } else {
            this.f2779d = m2852b();
        }
        this.f2777b = this.f2776a.eglCreateContext(this.f2778c, this.f2779d, EGL10.EGL_NO_CONTEXT, new int[]{12440, 2, 12344});
    }

    /* renamed from: b */
    private EGLConfig m2852b() {
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        if (!this.f2776a.eglChooseConfig(this.f2778c, new int[]{12339, 1, 12325, 16, 12326, 0, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, 4, 12344}, eGLConfigArr, eGLConfigArr.length, new int[1])) {
            TXCLog.m2911w("ImageEglSurface", "unable to find RGB8888  EGLConfig");
            return null;
        }
        return eGLConfigArr[0];
    }

    /* renamed from: a */
    public void m2856a() {
        EGL10 egl10 = this.f2776a;
        EGLDisplay eGLDisplay = this.f2778c;
        EGLSurface eGLSurface = EGL10.EGL_NO_SURFACE;
        egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL10.EGL_NO_CONTEXT);
        this.f2776a.eglDestroyContext(this.f2778c, this.f2777b);
        this.f2776a.eglTerminate(this.f2778c);
    }

    /* renamed from: a */
    public void m2853a(EGLSurface eGLSurface) {
        this.f2776a.eglDestroySurface(this.f2778c, eGLSurface);
    }

    /* renamed from: a */
    public EGLSurface m2855a(int i, int i2) {
        this.f2780e = this.f2776a.eglCreatePbufferSurface(this.f2778c, this.f2779d, new int[]{12375, i, 12374, i2, 12344});
        m2854a("eglCreatePbufferSurface");
        EGLSurface eGLSurface = this.f2780e;
        if (eGLSurface != null) {
            return eGLSurface;
        }
        throw new RuntimeException("surface was null");
    }

    /* renamed from: b */
    public void m2851b(EGLSurface eGLSurface) {
        EGLDisplay eGLDisplay = this.f2778c;
        if (eGLDisplay == EGL11.EGL_NO_DISPLAY) {
            TXCLog.m2915d("EglCore", "NOTE: makeCurrent w/o display");
        }
        if (this.f2776a.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.f2777b)) {
            return;
        }
        throw new RuntimeException("eglMakeCurrent failed");
    }

    /* renamed from: a */
    private void m2854a(String str) {
        int eglGetError = this.f2776a.eglGetError();
        if (eglGetError == 12288) {
            return;
        }
        throw new RuntimeException(str + ": EGL error: 0x" + Integer.toHexString(eglGetError));
    }
}
