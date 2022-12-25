package com.tencent.liteav.beauty.p115b;

import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.os.Handler;
import android.os.Looper;

/* renamed from: com.tencent.liteav.beauty.b.af */
/* loaded from: classes3.dex */
public class TXCGPUVideoPlayerFilter {

    /* renamed from: b */
    private static final String f2964b = "af";

    /* renamed from: a */
    SurfaceTexture.OnFrameAvailableListener f2965a;

    /* renamed from: c */
    private SurfaceTexture f2966c;

    /* renamed from: f */
    private MediaExtractor f2969f;

    /* renamed from: g */
    private AssetFileDescriptor f2970g;

    /* renamed from: l */
    private long f2975l;

    /* renamed from: m */
    private MediaCodec f2976m;

    /* renamed from: o */
    private boolean f2978o;

    /* renamed from: p */
    private Handler f2979p;

    /* renamed from: d */
    private int f2967d = -1;

    /* renamed from: e */
    private boolean f2968e = false;

    /* renamed from: h */
    private int f2971h = -1;

    /* renamed from: i */
    private int f2972i = -1;

    /* renamed from: j */
    private int f2973j = -1;

    /* renamed from: k */
    private int f2974k = -1;

    /* renamed from: n */
    private boolean f2977n = false;

    /* renamed from: q */
    private Object f2980q = new Object();

    TXCGPUVideoPlayerFilter() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public synchronized void m2738a() {
        synchronized (this.f2980q) {
            if (this.f2979p != null) {
                if (Looper.myLooper() == this.f2979p.getLooper()) {
                    m2734c();
                } else {
                    Runnable runnable = new Runnable() { // from class: com.tencent.liteav.beauty.b.af.1
                        @Override // java.lang.Runnable
                        public void run() {
                            synchronized (TXCGPUVideoPlayerFilter.this.f2980q) {
                                TXCGPUVideoPlayerFilter.this.m2734c();
                                TXCGPUVideoPlayerFilter.this.f2980q.notify();
                            }
                        }
                    };
                    this.f2979p.removeCallbacksAndMessages(null);
                    this.f2979p.post(runnable);
                    this.f2979p.getLooper().quitSafely();
                    while (true) {
                        try {
                            this.f2980q.wait();
                            break;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /* renamed from: b */
    private void m2736b() {
        if (this.f2968e) {
            this.f2968e = false;
            MediaExtractor mediaExtractor = this.f2969f;
            if (mediaExtractor != null) {
                mediaExtractor.release();
                this.f2969f = null;
            }
            try {
                try {
                    this.f2976m.stop();
                } catch (Exception e) {
                    try {
                        e.printStackTrace();
                        try {
                            this.f2976m.release();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    } finally {
                    }
                }
                try {
                    try {
                        this.f2976m.release();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                } finally {
                }
            } catch (Throwable th) {
                try {
                    try {
                        this.f2976m.release();
                    } finally {
                    }
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: c */
    public void m2734c() {
        m2736b();
        this.f2965a = null;
        this.f2975l = 0L;
        this.f2978o = false;
        SurfaceTexture surfaceTexture = this.f2966c;
        if (surfaceTexture != null) {
            surfaceTexture.release();
            this.f2966c = null;
        }
        synchronized (this.f2980q) {
            if (this.f2979p != null) {
                this.f2979p.removeCallbacksAndMessages(null);
                this.f2979p.getLooper().quit();
                this.f2979p = null;
                this.f2980q.notify();
            }
        }
        AssetFileDescriptor assetFileDescriptor = this.f2970g;
        if (assetFileDescriptor != null) {
            try {
                assetFileDescriptor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.f2970g = null;
        }
    }
}
