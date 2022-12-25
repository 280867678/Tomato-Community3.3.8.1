package com.tencent.liteav.renderer;

import android.graphics.SurfaceTexture;
import android.opengl.GLUtils;
import com.tencent.liteav.basic.log.TXCLog;
import java.lang.ref.WeakReference;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

/* renamed from: com.tencent.liteav.renderer.b */
/* loaded from: classes3.dex */
class TXCGLRenderThread extends Thread {

    /* renamed from: c */
    private WeakReference<TXCGLRender> f5093c;

    /* renamed from: d */
    private EGL10 f5094d;

    /* renamed from: e */
    private EGLContext f5095e;

    /* renamed from: f */
    private EGLDisplay f5096f;

    /* renamed from: g */
    private EGLSurface f5097g;

    /* renamed from: h */
    private EGLConfig f5098h;

    /* renamed from: i */
    private WeakReference<SurfaceTexture> f5099i;

    /* renamed from: a */
    private int f5091a = 12440;

    /* renamed from: b */
    private int f5092b = 4;

    /* renamed from: j */
    private boolean f5100j = false;

    /* renamed from: k */
    private int f5101k = 1280;

    /* renamed from: l */
    private int f5102l = 720;

    /* JADX INFO: Access modifiers changed from: package-private */
    public TXCGLRenderThread(WeakReference<TXCGLRender> weakReference) {
        this.f5093c = weakReference;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        setName("VRender" + getId());
        try {
            this.f5100j = true;
            m926h();
            m931c();
            m928f();
            while (this.f5100j) {
                if (m929e() && this.f5094d != null && this.f5096f != null && this.f5097g != null) {
                    TXCGLRender tXCGLRender = this.f5093c == null ? null : this.f5093c.get();
                    if (tXCGLRender != null && tXCGLRender.m938f() != null) {
                        this.f5094d.eglSwapBuffers(this.f5096f, this.f5097g);
                    }
                }
            }
            m927g();
            m930d();
            m925i();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: a */
    public EGLContext m934a() {
        return this.f5095e;
    }

    /* renamed from: b */
    public void m932b() {
        this.f5100j = false;
    }

    /* renamed from: c */
    private void m931c() {
        try {
            TXCGLRender tXCGLRender = this.f5093c.get();
            if (tXCGLRender == null) {
                return;
            }
            tXCGLRender.m941c();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: d */
    private void m930d() {
        try {
            TXCGLRender tXCGLRender = this.f5093c.get();
            if (tXCGLRender == null) {
                return;
            }
            tXCGLRender.m940d();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: e */
    private boolean m929e() {
        TXCGLRender tXCGLRender;
        try {
            Thread.sleep(10L);
            if (this.f5093c != null && (tXCGLRender = this.f5093c.get()) != null) {
                return tXCGLRender.m939e();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* renamed from: f */
    private void m928f() {
        TXCGLRender tXCGLRender;
        WeakReference<TXCGLRender> weakReference = this.f5093c;
        if (weakReference == null || (tXCGLRender = weakReference.get()) == null) {
            return;
        }
        tXCGLRender.m865o();
    }

    /* renamed from: g */
    private void m927g() {
        TXCGLRender tXCGLRender;
        WeakReference<TXCGLRender> weakReference = this.f5093c;
        if (weakReference == null || (tXCGLRender = weakReference.get()) == null) {
            return;
        }
        tXCGLRender.m864p();
    }

    /* renamed from: h */
    private void m926h() {
        TXCGLRender tXCGLRender = this.f5093c.get();
        if (tXCGLRender == null) {
            return;
        }
        this.f5094d = (EGL10) EGLContext.getEGL();
        this.f5096f = this.f5094d.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        this.f5094d.eglInitialize(this.f5096f, new int[2]);
        this.f5098h = m924j();
        SurfaceTexture m938f = tXCGLRender.m938f();
        if (m938f != null) {
            this.f5099i = new WeakReference<>(m938f);
            this.f5097g = this.f5094d.eglCreateWindowSurface(this.f5096f, this.f5098h, m938f, null);
        } else {
            this.f5097g = this.f5094d.eglCreatePbufferSurface(this.f5096f, this.f5098h, new int[]{12375, this.f5101k, 12374, this.f5102l, 12344});
        }
        this.f5095e = m933a(this.f5094d, this.f5096f, this.f5098h, EGL10.EGL_NO_CONTEXT);
        TXCLog.m2911w("TXCVideoRenderThread", "vrender: init egl @context=" + this.f5095e + ",surface=" + this.f5097g);
        try {
            if (this.f5097g == null || this.f5097g == EGL10.EGL_NO_SURFACE) {
                throw new RuntimeException("GL error:" + GLUtils.getEGLErrorString(this.f5094d.eglGetError()));
            } else if (this.f5094d.eglMakeCurrent(this.f5096f, this.f5097g, this.f5097g, this.f5095e)) {
            } else {
                throw new RuntimeException("GL Make current Error" + GLUtils.getEGLErrorString(this.f5094d.eglGetError()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: i */
    private void m925i() {
        EGL10 egl10 = this.f5094d;
        EGLDisplay eGLDisplay = this.f5096f;
        EGLSurface eGLSurface = EGL10.EGL_NO_SURFACE;
        egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL10.EGL_NO_CONTEXT);
        this.f5094d.eglDestroyContext(this.f5096f, this.f5095e);
        this.f5094d.eglDestroySurface(this.f5096f, this.f5097g);
        this.f5094d.eglTerminate(this.f5096f);
        this.f5099i = null;
        TXCLog.m2911w("TXCVideoRenderThread", "vrender: uninit egl @context=" + this.f5095e + ",surface=" + this.f5097g);
    }

    /* renamed from: a */
    private EGLContext m933a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, EGLContext eGLContext) {
        return egl10.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, new int[]{this.f5091a, 2, 12344});
    }

    /* renamed from: j */
    private EGLConfig m924j() {
        int[] iArr = new int[1];
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        if (!this.f5094d.eglChooseConfig(this.f5096f, m923k(), eGLConfigArr, 1, iArr)) {
            throw new IllegalArgumentException("Failed to choose config:" + GLUtils.getEGLErrorString(this.f5094d.eglGetError()));
        } else if (iArr[0] <= 0) {
            return null;
        } else {
            return eGLConfigArr[0];
        }
    }

    /* renamed from: k */
    private int[] m923k() {
        return new int[]{12352, this.f5092b, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12326, 0, 12344};
    }
}
