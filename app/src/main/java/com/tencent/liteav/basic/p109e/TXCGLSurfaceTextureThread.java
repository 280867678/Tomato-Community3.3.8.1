package com.tencent.liteav.basic.p109e;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.os.HandlerThread;
import android.os.Message;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p109e.TXCGLThreadHandler;
import javax.microedition.khronos.egl.EGLContext;

/* renamed from: com.tencent.liteav.basic.e.e */
/* loaded from: classes3.dex */
public class TXCGLSurfaceTextureThread implements TXCGLThreadHandler.AbstractC3347a, TXICaptureGLThread {

    /* renamed from: d */
    private TXIGLSurfaceTextureListener f2592d;

    /* renamed from: b */
    private volatile HandlerThread f2590b = null;

    /* renamed from: c */
    private volatile TXCGLThreadHandler f2591c = null;

    /* renamed from: e */
    private int[] f2593e = null;

    /* renamed from: f */
    private SurfaceTexture f2594f = null;

    /* renamed from: g */
    private boolean f2595g = false;

    /* renamed from: a */
    public int f2589a = 25;

    /* renamed from: h */
    private long f2596h = 0;

    /* renamed from: i */
    private long f2597i = 0;

    /* renamed from: j */
    private float[] f2598j = new float[16];

    @Override // com.tencent.liteav.basic.p109e.TXICaptureGLThread
    /* renamed from: a */
    public void mo1023a(int i, boolean z, int i2, int i3, int i4) {
    }

    @Override // com.tencent.liteav.basic.p109e.TXICaptureGLThread
    /* renamed from: a */
    public void mo1025a(int i) {
        this.f2589a = i;
        m3054b();
    }

    @Override // com.tencent.liteav.basic.p109e.TXICaptureGLThread
    /* renamed from: a */
    public void mo1026a() {
        m3052f();
    }

    @Override // com.tencent.liteav.basic.p109e.TXICaptureGLThread
    public void setSurfaceTextureListener(TXIGLSurfaceTextureListener tXIGLSurfaceTextureListener) {
        this.f2592d = tXIGLSurfaceTextureListener;
    }

    @Override // com.tencent.liteav.basic.p109e.TXICaptureGLThread
    public SurfaceTexture getSurfaceTexture() {
        return this.f2594f;
    }

    @Override // com.tencent.liteav.basic.p109e.TXICaptureGLThread
    public EGLContext getGLContext() {
        EGLContext m3048a;
        synchronized (this) {
            m3048a = this.f2591c != null ? this.f2591c.m3048a() : null;
        }
        return m3048a;
    }

    @Override // com.tencent.liteav.basic.p109e.TXICaptureGLThread
    /* renamed from: a */
    public void mo1017a(Runnable runnable) {
        synchronized (this) {
            if (this.f2591c != null) {
                this.f2591c.post(runnable);
            }
        }
    }

    @Override // com.tencent.liteav.basic.p109e.TXICaptureGLThread
    /* renamed from: a */
    public void mo1015a(boolean z) {
        synchronized (this) {
            try {
                try {
                    if (this.f2591c != null) {
                        this.f2591c.removeCallbacksAndMessages(null);
                    }
                    this.f2595g = false;
                } catch (Exception unused) {
                }
                if (this.f2594f != null && this.f2593e != null) {
                    this.f2594f.updateTexImage();
                    this.f2594f.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() { // from class: com.tencent.liteav.basic.e.e.1
                        @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
                        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                            TXCGLSurfaceTextureThread.this.m3058a(103, new Runnable() { // from class: com.tencent.liteav.basic.e.e.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    TXCGLSurfaceTextureThread.this.f2595g = true;
                                    TXCGLSurfaceTextureThread.this.m3053b(102);
                                }
                            });
                            surfaceTexture.setOnFrameAvailableListener(null);
                        }
                    });
                }
            } finally {
            }
        }
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGLThreadHandler.AbstractC3347a
    /* renamed from: c */
    public void mo3038c() {
        m3050h();
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGLThreadHandler.AbstractC3347a
    /* renamed from: d */
    public void mo3037d() {
        SurfaceTexture surfaceTexture;
        m3059a(102, 5L);
        if (!m3049i() || (surfaceTexture = this.f2594f) == null || this.f2593e == null) {
            return;
        }
        try {
            surfaceTexture.updateTexImage();
            this.f2594f.getTransformMatrix(this.f2598j);
        } catch (Exception e) {
            TXCLog.m2914e("TXGLSurfaceTextureThread", "onMsgRend Exception " + e.getMessage());
            e.printStackTrace();
        }
        TXIGLSurfaceTextureListener tXIGLSurfaceTextureListener = this.f2592d;
        if (tXIGLSurfaceTextureListener == null) {
            return;
        }
        tXIGLSurfaceTextureListener.onTextureProcess(this.f2593e[0], this.f2598j);
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGLThreadHandler.AbstractC3347a
    /* renamed from: e */
    public void mo3036e() {
        m3051g();
    }

    /* renamed from: b */
    private void m3054b() {
        m3052f();
        synchronized (this) {
            this.f2590b = new HandlerThread("TXGLSurfaceTextureThread");
            this.f2590b.start();
            this.f2591c = new TXCGLThreadHandler(this.f2590b.getLooper());
            this.f2591c.m3045a(this);
            this.f2591c.f2603a = 1280;
            this.f2591c.f2604b = 720;
            TXCLog.m2911w("TXGLSurfaceTextureThread", "create gl thread " + this.f2590b.getName());
        }
        m3053b(100);
    }

    /* renamed from: f */
    private void m3052f() {
        synchronized (this) {
            if (this.f2591c != null) {
                TXCGLThreadHandler.m3047a(this.f2591c, this.f2590b);
                TXCLog.m2911w("TXGLSurfaceTextureThread", "destroy gl thread");
            }
            this.f2591c = null;
            this.f2590b = null;
        }
    }

    /* renamed from: a */
    private void m3059a(int i, long j) {
        synchronized (this) {
            if (this.f2591c != null) {
                this.f2591c.sendEmptyMessageDelayed(i, j);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public void m3053b(int i) {
        synchronized (this) {
            if (this.f2591c != null) {
                this.f2591c.sendEmptyMessage(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m3058a(int i, Runnable runnable) {
        synchronized (this) {
            if (this.f2591c != null) {
                Message message = new Message();
                message.what = i;
                message.obj = runnable;
                this.f2591c.sendMessage(message);
            }
        }
    }

    /* renamed from: g */
    private void m3051g() {
        TXCLog.m2911w("TXGLSurfaceTextureThread", "destroy surface texture ");
        TXIGLSurfaceTextureListener tXIGLSurfaceTextureListener = this.f2592d;
        if (tXIGLSurfaceTextureListener != null) {
            tXIGLSurfaceTextureListener.onSurfaceTextureDestroy(this.f2594f);
        }
        SurfaceTexture surfaceTexture = this.f2594f;
        if (surfaceTexture != null) {
            surfaceTexture.setOnFrameAvailableListener(null);
            this.f2594f.release();
            this.f2595g = false;
            this.f2594f = null;
        }
        int[] iArr = this.f2593e;
        if (iArr != null) {
            GLES20.glDeleteTextures(1, iArr, 0);
            this.f2593e = null;
        }
    }

    /* renamed from: h */
    private void m3050h() {
        TXCLog.m2911w("TXGLSurfaceTextureThread", "init surface texture ");
        this.f2593e = new int[1];
        this.f2593e[0] = TXCOpenGlUtils.m2994b();
        int[] iArr = this.f2593e;
        if (iArr[0] <= 0) {
            this.f2593e = null;
            return;
        }
        this.f2594f = new SurfaceTexture(iArr[0]);
        this.f2594f.setDefaultBufferSize(1280, 720);
        this.f2594f.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() { // from class: com.tencent.liteav.basic.e.e.2
            @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
            public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                TXCGLSurfaceTextureThread.this.m3058a(103, new Runnable() { // from class: com.tencent.liteav.basic.e.e.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TXCGLSurfaceTextureThread.this.f2595g = true;
                        TXCGLSurfaceTextureThread.this.m3053b(102);
                    }
                });
                surfaceTexture.setOnFrameAvailableListener(null);
            }
        });
        TXIGLSurfaceTextureListener tXIGLSurfaceTextureListener = this.f2592d;
        if (tXIGLSurfaceTextureListener == null) {
            return;
        }
        tXIGLSurfaceTextureListener.onSurfaceTextureAvailable(this.f2594f);
    }

    /* renamed from: i */
    private boolean m3049i() {
        if (!this.f2595g) {
            this.f2596h = 0L;
            this.f2597i = System.nanoTime();
            return false;
        }
        long nanoTime = System.nanoTime();
        long j = this.f2597i;
        if (nanoTime < ((((this.f2596h * 1000) * 1000) * 1000) / this.f2589a) + j) {
            return false;
        }
        if (j == 0) {
            this.f2597i = nanoTime;
        } else if (nanoTime > j + 1000000000) {
            this.f2596h = 0L;
            this.f2597i = nanoTime;
        }
        this.f2596h++;
        return true;
    }
}
