package com.tencent.liteav.basic.p109e;

import android.annotation.TargetApi;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLExt;
import android.opengl.EGLSurface;
import android.util.Log;
import android.view.Surface;

@TargetApi(17)
/* renamed from: com.tencent.liteav.basic.e.c */
/* loaded from: classes3.dex */
public class EGL14Helper {

    /* renamed from: a */
    private static int f2550a = 2;

    /* renamed from: b */
    private static final String f2551b = "c";

    /* renamed from: k */
    private static int[] f2552k;

    /* renamed from: l */
    private static int[] f2553l;

    /* renamed from: h */
    private boolean f2559h;

    /* renamed from: i */
    private EGLSurface f2560i;

    /* renamed from: c */
    private EGLDisplay f2554c = EGL14.EGL_NO_DISPLAY;

    /* renamed from: d */
    private EGLContext f2555d = EGL14.EGL_NO_CONTEXT;

    /* renamed from: e */
    private EGLConfig f2556e = null;

    /* renamed from: f */
    private int f2557f = 0;

    /* renamed from: g */
    private int f2558g = 0;

    /* renamed from: j */
    private int f2561j = -1;

    /* renamed from: a */
    public static EGL14Helper m3075a(EGLConfig eGLConfig, EGLContext eGLContext, Surface surface, int i, int i2) {
        EGL14Helper eGL14Helper = new EGL14Helper();
        eGL14Helper.f2557f = i;
        eGL14Helper.f2558g = i2;
        if (eGL14Helper.m3076a(eGLConfig, eGLContext, surface)) {
            return eGL14Helper;
        }
        return null;
    }

    /* renamed from: a */
    public void m3078a() {
        int eglGetError = EGL14.eglGetError();
        if (eglGetError == 12288) {
            return;
        }
        String str = f2551b;
        Log.e(str, "EGL error:" + eglGetError);
        throw new RuntimeException(": EGL error: 0x" + Integer.toHexString(eglGetError));
    }

    /* renamed from: b */
    public void m3074b() {
        EGLDisplay eGLDisplay = this.f2554c;
        if (eGLDisplay != EGL14.EGL_NO_DISPLAY) {
            EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
            EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT);
            EGL14.eglDestroySurface(this.f2554c, this.f2560i);
            EGL14.eglDestroyContext(this.f2554c, this.f2555d);
            this.f2555d = EGL14.EGL_NO_CONTEXT;
            EGL14.eglReleaseThread();
            EGL14.eglTerminate(this.f2554c);
        }
        this.f2554c = EGL14.EGL_NO_DISPLAY;
    }

    /* renamed from: c */
    public boolean m3073c() {
        return EGL14.eglSwapBuffers(this.f2554c, this.f2560i);
    }

    /* renamed from: a */
    private boolean m3076a(EGLConfig eGLConfig, EGLContext eGLContext, Surface surface) {
        EGLContext eGLContext2;
        this.f2554c = EGL14.eglGetDisplay(0);
        EGLDisplay eGLDisplay = this.f2554c;
        if (eGLDisplay == EGL14.EGL_NO_DISPLAY) {
            throw new RuntimeException("unable to get EGL14 display");
        }
        int[] iArr = new int[2];
        if (!EGL14.eglInitialize(eGLDisplay, iArr, 0, iArr, 1)) {
            this.f2554c = null;
            throw new RuntimeException("unable to initialize EGL14");
        }
        if (eGLConfig != null) {
            this.f2556e = eGLConfig;
        } else {
            EGLConfig[] eGLConfigArr = new EGLConfig[1];
            if (!EGL14.eglChooseConfig(this.f2554c, surface == null ? f2553l : f2552k, 0, eGLConfigArr, 0, eGLConfigArr.length, new int[1], 0)) {
                return false;
            }
            this.f2556e = eGLConfigArr[0];
        }
        if (eGLContext != null) {
            this.f2559h = true;
            eGLContext2 = eGLContext;
        } else {
            eGLContext2 = EGL14.EGL_NO_CONTEXT;
        }
        this.f2555d = EGL14.eglCreateContext(this.f2554c, this.f2556e, eGLContext2, new int[]{12440, f2550a, 12344}, 0);
        int[] iArr2 = {12344};
        if (this.f2555d == EGL14.EGL_NO_CONTEXT) {
            m3078a();
            return false;
        }
        if (surface == null) {
            this.f2560i = EGL14.eglCreatePbufferSurface(this.f2554c, this.f2556e, new int[]{12375, this.f2557f, 12374, this.f2558g, 12344}, 0);
        } else {
            this.f2560i = EGL14.eglCreateWindowSurface(this.f2554c, this.f2556e, surface, iArr2, 0);
        }
        m3078a();
        EGLDisplay eGLDisplay2 = this.f2554c;
        EGLSurface eGLSurface = this.f2560i;
        if (EGL14.eglMakeCurrent(eGLDisplay2, eGLSurface, eGLSurface, this.f2555d)) {
            return true;
        }
        m3078a();
        return false;
    }

    /* renamed from: a */
    public void m3077a(long j) {
        EGLExt.eglPresentationTimeANDROID(this.f2554c, this.f2560i, j);
    }

    /* renamed from: d */
    public EGLContext m3072d() {
        return this.f2555d;
    }

    static {
        int[] iArr = new int[17];
        iArr[0] = 12324;
        iArr[1] = 8;
        iArr[2] = 12323;
        iArr[3] = 8;
        int i = 4;
        iArr[4] = 12322;
        iArr[5] = 8;
        iArr[6] = 12321;
        iArr[7] = 8;
        iArr[8] = 12325;
        iArr[9] = 0;
        iArr[10] = 12326;
        iArr[11] = 0;
        iArr[12] = 12352;
        iArr[13] = f2550a == 2 ? 4 : 68;
        iArr[14] = 12610;
        iArr[15] = 1;
        iArr[16] = 12344;
        f2552k = iArr;
        int[] iArr2 = new int[19];
        iArr2[0] = 12339;
        iArr2[1] = 1;
        iArr2[2] = 12324;
        iArr2[3] = 8;
        iArr2[4] = 12323;
        iArr2[5] = 8;
        iArr2[6] = 12322;
        iArr2[7] = 8;
        iArr2[8] = 12321;
        iArr2[9] = 8;
        iArr2[10] = 12325;
        iArr2[11] = 0;
        iArr2[12] = 12326;
        iArr2[13] = 0;
        iArr2[14] = 12352;
        if (f2550a != 2) {
            i = 68;
        }
        iArr2[15] = i;
        iArr2[16] = 12610;
        iArr2[17] = 1;
        iArr2[18] = 12344;
        f2553l = iArr2;
    }
}
