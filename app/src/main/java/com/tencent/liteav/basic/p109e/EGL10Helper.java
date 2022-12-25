package com.tencent.liteav.basic.p109e;

import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

/* renamed from: com.tencent.liteav.basic.e.b */
/* loaded from: classes3.dex */
public class EGL10Helper {

    /* renamed from: a */
    public static final String f2537a = "b";

    /* renamed from: l */
    private static int[] f2538l = {12339, 1, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12326, 0, 12352, 4, 12344};

    /* renamed from: m */
    private static int[] f2539m = {12339, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12326, 0, 12352, 4, 12610, 1, 12344};

    /* renamed from: b */
    private EGL10 f2540b;

    /* renamed from: c */
    private EGLDisplay f2541c;

    /* renamed from: d */
    private EGLConfig f2542d;

    /* renamed from: e */
    private boolean f2543e;

    /* renamed from: f */
    private EGLContext f2544f;

    /* renamed from: g */
    private boolean f2545g;

    /* renamed from: h */
    private EGLSurface f2546h;

    /* renamed from: i */
    private int f2547i = 0;

    /* renamed from: j */
    private int f2548j = 0;

    /* renamed from: k */
    private int[] f2549k = new int[2];

    private EGL10Helper() {
    }

    /* renamed from: a */
    public static EGL10Helper m3082a(EGLConfig eGLConfig, EGLContext eGLContext, Surface surface, int i, int i2) {
        EGL10Helper eGL10Helper = new EGL10Helper();
        eGL10Helper.f2547i = i;
        eGL10Helper.f2548j = i2;
        if (eGL10Helper.m3083a(eGLConfig, eGLContext, surface)) {
            return eGL10Helper;
        }
        return null;
    }

    /* renamed from: a */
    public boolean m3084a() {
        boolean eglSwapBuffers = this.f2540b.eglSwapBuffers(this.f2541c, this.f2546h);
        m3079d();
        return eglSwapBuffers;
    }

    /* renamed from: b */
    public void m3081b() {
        EGL10 egl10 = this.f2540b;
        EGLDisplay eGLDisplay = this.f2541c;
        EGLSurface eGLSurface = EGL10.EGL_NO_SURFACE;
        egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL10.EGL_NO_CONTEXT);
        EGLSurface eGLSurface2 = this.f2546h;
        if (eGLSurface2 != null) {
            this.f2540b.eglDestroySurface(this.f2541c, eGLSurface2);
        }
        EGLContext eGLContext = this.f2544f;
        if (eGLContext != null) {
            this.f2540b.eglDestroyContext(this.f2541c, eGLContext);
        }
        this.f2540b.eglTerminate(this.f2541c);
        m3079d();
        this.f2541c = null;
        this.f2546h = null;
        this.f2541c = null;
    }

    /* renamed from: a */
    private boolean m3083a(EGLConfig eGLConfig, EGLContext eGLContext, Surface surface) {
        this.f2540b = (EGL10) EGLContext.getEGL();
        this.f2541c = this.f2540b.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        this.f2540b.eglInitialize(this.f2541c, this.f2549k);
        if (eGLConfig == null) {
            EGLConfig[] eGLConfigArr = new EGLConfig[1];
            this.f2540b.eglChooseConfig(this.f2541c, surface == null ? f2538l : f2539m, eGLConfigArr, 1, new int[1]);
            this.f2542d = eGLConfigArr[0];
            this.f2543e = true;
        } else {
            this.f2542d = eGLConfig;
        }
        int[] iArr = {12440, 2, 12344};
        if (eGLContext == null) {
            this.f2544f = this.f2540b.eglCreateContext(this.f2541c, this.f2542d, EGL10.EGL_NO_CONTEXT, iArr);
        } else {
            this.f2544f = this.f2540b.eglCreateContext(this.f2541c, this.f2542d, eGLContext, iArr);
            this.f2545g = true;
        }
        if (this.f2544f == EGL10.EGL_NO_CONTEXT) {
            m3079d();
            return false;
        }
        int[] iArr2 = {12375, this.f2547i, 12374, this.f2548j, 12344};
        if (surface == null) {
            this.f2546h = this.f2540b.eglCreatePbufferSurface(this.f2541c, this.f2542d, iArr2);
        } else {
            this.f2546h = this.f2540b.eglCreateWindowSurface(this.f2541c, this.f2542d, surface, null);
        }
        EGLSurface eGLSurface = this.f2546h;
        if (eGLSurface == EGL10.EGL_NO_SURFACE) {
            m3079d();
            return false;
        } else if (this.f2540b.eglMakeCurrent(this.f2541c, eGLSurface, eGLSurface, this.f2544f)) {
            return true;
        } else {
            m3079d();
            return false;
        }
    }

    /* renamed from: c */
    public EGLContext m3080c() {
        return this.f2544f;
    }

    /* renamed from: d */
    public void m3079d() {
        int eglGetError = this.f2540b.eglGetError();
        if (eglGetError != 12288) {
            String str = f2537a;
            TXCLog.m2914e(str, "EGL error: 0x" + Integer.toHexString(eglGetError));
        }
    }
}
