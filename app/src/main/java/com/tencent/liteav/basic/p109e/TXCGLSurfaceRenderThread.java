package com.tencent.liteav.basic.p109e;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.opengl.GLES20;
import android.os.HandlerThread;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p109e.TXCGLThreadHandler;
import java.nio.ByteBuffer;
import javax.microedition.khronos.egl.EGLContext;

/* renamed from: com.tencent.liteav.basic.e.d */
/* loaded from: classes3.dex */
public class TXCGLSurfaceRenderThread implements TXCGLThreadHandler.AbstractC3347a {

    /* renamed from: a */
    private volatile HandlerThread f2562a = null;

    /* renamed from: b */
    private volatile TXCGLThreadHandler f2563b = null;

    /* renamed from: c */
    private TXCGPUFilter f2564c = null;

    /* renamed from: d */
    private int f2565d = 0;

    /* renamed from: e */
    private boolean f2566e = false;

    /* renamed from: f */
    private float f2567f = 1.0f;

    /* renamed from: g */
    private float f2568g = 1.0f;

    /* renamed from: h */
    private int f2569h = 0;

    /* renamed from: i */
    private int f2570i = 0;

    /* renamed from: j */
    private boolean f2571j = false;

    /* renamed from: k */
    private TXITakePhotoListener f2572k = null;

    /* renamed from: l */
    private boolean f2573l = false;

    @Override // com.tencent.liteav.basic.p109e.TXCGLThreadHandler.AbstractC3347a
    /* renamed from: d */
    public void mo3037d() {
    }

    /* renamed from: a */
    public void m3065a(EGLContext eGLContext, Surface surface) {
        TXCLog.m2913i("TXGLSurfaceRenderThread", "surface-render: surface render start " + surface);
        m3062b(eGLContext, surface);
    }

    /* renamed from: a */
    public void m3071a() {
        TXCLog.m2913i("TXGLSurfaceRenderThread", "surface-render: surface render stop ");
        m3061f();
    }

    /* renamed from: b */
    public Surface m3064b() {
        Surface m3044b;
        synchronized (this) {
            m3044b = this.f2563b != null ? this.f2563b.m3044b() : null;
        }
        return m3044b;
    }

    /* renamed from: a */
    public void m3066a(Runnable runnable) {
        synchronized (this) {
            if (this.f2563b != null) {
                this.f2563b.post(runnable);
            }
        }
    }

    /* renamed from: a */
    public void m3069a(final int i, final boolean z, final int i2, final int i3, final int i4, final int i5, final int i6, final boolean z2) {
        GLES20.glFinish();
        synchronized (this) {
            if (this.f2563b != null) {
                this.f2563b.post(new Runnable() { // from class: com.tencent.liteav.basic.e.d.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            TXCGLSurfaceRenderThread.this.m3063b(i, z, i2, i3, i4, i5, i6, z2);
                        } catch (Exception unused) {
                            TXCLog.m2914e("TXGLSurfaceRenderThread", "surface-render: render texture error occurred!");
                        }
                    }
                });
            }
        }
    }

    /* renamed from: a */
    public void m3067a(TXITakePhotoListener tXITakePhotoListener) {
        this.f2572k = tXITakePhotoListener;
        this.f2571j = true;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGLThreadHandler.AbstractC3347a
    /* renamed from: c */
    public void mo3038c() {
        this.f2564c = new TXCGPUFilter();
        if (!this.f2564c.mo2653c()) {
            return;
        }
        this.f2564c.m3026a(TXCTextureRotationUtil.f2684e, TXCTextureRotationUtil.m2991a(TXCRotation.NORMAL, false, false));
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGLThreadHandler.AbstractC3347a
    /* renamed from: e */
    public void mo3036e() {
        TXCGPUFilter tXCGPUFilter = this.f2564c;
        if (tXCGPUFilter != null) {
            tXCGPUFilter.mo1351e();
            this.f2564c = null;
        }
    }

    /* renamed from: b */
    private void m3062b(EGLContext eGLContext, Surface surface) {
        m3061f();
        synchronized (this) {
            this.f2562a = new HandlerThread("TXGLSurfaceRenderThread");
            this.f2562a.start();
            this.f2563b = new TXCGLThreadHandler(this.f2562a.getLooper());
            this.f2563b.m3045a(this);
            this.f2563b.f2606d = eGLContext;
            this.f2563b.f2605c = surface;
            TXCLog.m2911w("TXGLSurfaceRenderThread", "surface-render: create gl thread " + this.f2562a.getName());
        }
        m3070a(100);
    }

    /* renamed from: f */
    private void m3061f() {
        synchronized (this) {
            if (this.f2563b != null) {
                TXCGLThreadHandler.m3047a(this.f2563b, this.f2562a);
                TXCLog.m2911w("TXGLSurfaceRenderThread", "surface-render: destroy gl thread");
            }
            this.f2563b = null;
            this.f2562a = null;
        }
    }

    /* renamed from: a */
    private void m3070a(int i) {
        synchronized (this) {
            if (this.f2563b != null) {
                this.f2563b.sendEmptyMessage(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public void m3063b(int i, boolean z, int i2, int i3, int i4, int i5, int i6, boolean z2) {
        int i7 = i3;
        if (i4 == 0 || i7 == 0 || i5 == 0 || i6 == 0 || this.f2564c == null) {
            return;
        }
        if (this.f2573l) {
            this.f2573l = false;
            return;
        }
        if (z2) {
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GLES20.glClear(16640);
            GLES20.glBindFramebuffer(36160, 0);
            if (this.f2563b != null) {
                this.f2563b.m3042c();
            }
            this.f2573l = true;
        }
        this.f2569h = i7;
        this.f2570i = i4;
        GLES20.glViewport(0, 0, i7, i4);
        float f = i4 != 0 ? i7 / i4 : 1.0f;
        float f2 = i6 != 0 ? i5 / i6 : 1.0f;
        if (this.f2566e != z || this.f2565d != i2 || this.f2567f != f || this.f2568g != f2) {
            this.f2566e = z;
            this.f2565d = i2;
            this.f2567f = f;
            this.f2568g = f2;
            int i8 = (720 - this.f2565d) % 360;
            boolean z3 = i8 == 90 || i8 == 270;
            int i9 = z3 ? i4 : i7;
            if (!z3) {
                i7 = i4;
            }
            this.f2564c.m3034a(i5, i6, i8, TXCTextureRotationUtil.m2991a(TXCRotation.NORMAL, false, true), i9 / i7, z3 ? false : this.f2566e, z3 ? this.f2566e : false);
            if (z3) {
                this.f2564c.m3019g();
            } else {
                this.f2564c.m3018h();
            }
        }
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(16640);
        GLES20.glBindFramebuffer(36160, 0);
        this.f2564c.m3025b(i);
        m3060g();
        if (this.f2563b == null) {
            return;
        }
        this.f2563b.m3042c();
    }

    /* renamed from: g */
    private void m3060g() {
        int i;
        if (this.f2571j) {
            int i2 = this.f2569h;
            if (i2 != 0 && (i = this.f2570i) != 0) {
                boolean z = i2 <= i;
                int i3 = this.f2570i;
                int i4 = this.f2569h;
                if (i3 < i4) {
                    i3 = i4;
                }
                int i5 = this.f2570i;
                int i6 = this.f2569h;
                if (i5 >= i6) {
                    i5 = i6;
                }
                if (z) {
                    int i7 = i5;
                    i5 = i3;
                    i3 = i7;
                }
                final ByteBuffer allocate = ByteBuffer.allocate(i3 * i5 * 4);
                final Bitmap createBitmap = Bitmap.createBitmap(i3, i5, Bitmap.Config.ARGB_8888);
                allocate.position(0);
                GLES20.glReadPixels(0, 0, i3, i5, 6408, 5121, allocate);
                final TXITakePhotoListener tXITakePhotoListener = this.f2572k;
                if (tXITakePhotoListener != null) {
                    final int i8 = i3;
                    final int i9 = i5;
                    new Thread(new Runnable() { // from class: com.tencent.liteav.basic.e.d.2
                        @Override // java.lang.Runnable
                        public void run() {
                            allocate.position(0);
                            createBitmap.copyPixelsFromBuffer(allocate);
                            Matrix matrix = new Matrix();
                            matrix.setScale(1.0f, -1.0f);
                            tXITakePhotoListener.onTakePhotoComplete(Bitmap.createBitmap(createBitmap, 0, 0, i8, i9, matrix, false));
                            createBitmap.recycle();
                        }
                    }).start();
                }
            }
            this.f2572k = null;
            this.f2571j = false;
        }
    }
}
