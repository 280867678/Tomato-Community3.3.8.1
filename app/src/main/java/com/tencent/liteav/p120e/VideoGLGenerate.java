package com.tencent.liteav.p120e;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Surface;
import com.one.tomato.entity.C2516Ad;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p109e.EGL14Helper;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p119d.Resolution;
import com.tencent.liteav.renderer.TXCOesTextureRender;

/* renamed from: com.tencent.liteav.e.aa */
/* loaded from: classes3.dex */
public class VideoGLGenerate {

    /* renamed from: e */
    private int f3503e;

    /* renamed from: f */
    private int f3504f;

    /* renamed from: g */
    private EGL14Helper f3505g;

    /* renamed from: h */
    private TXCOesTextureRender f3506h;

    /* renamed from: i */
    private TXCOesTextureRender f3507i;

    /* renamed from: j */
    private OnContextListener f3508j;

    /* renamed from: k */
    private IVideoRenderListener f3509k;

    /* renamed from: l */
    private SurfaceTexture f3510l;

    /* renamed from: m */
    private Surface f3511m;

    /* renamed from: n */
    private boolean f3512n;

    /* renamed from: o */
    private boolean f3513o;

    /* renamed from: p */
    private Frame f3514p;

    /* renamed from: a */
    private final String f3499a = "VideoGLGenerate";

    /* renamed from: q */
    private SurfaceTexture.OnFrameAvailableListener f3515q = new SurfaceTexture.OnFrameAvailableListener() { // from class: com.tencent.liteav.e.aa.3
        @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
            VideoGLGenerate.this.f3512n = true;
            if (VideoGLGenerate.this.f3514p != null) {
                VideoGLGenerate videoGLGenerate = VideoGLGenerate.this;
                videoGLGenerate.m2253c(videoGLGenerate.f3514p);
                VideoGLGenerate.this.f3514p = null;
            }
        }
    };

    /* renamed from: b */
    private float[] f3500b = new float[16];

    /* renamed from: d */
    private HandlerThread f3502d = new HandlerThread("VideoGLGenerate");

    /* renamed from: c */
    private Handler f3501c = new Handler(this.f3502d.getLooper());

    public VideoGLGenerate() {
        this.f3502d.start();
    }

    /* renamed from: a */
    public void m2264a(Resolution resolution) {
        this.f3503e = resolution.f3467a;
        this.f3504f = resolution.f3468b;
    }

    /* renamed from: a */
    public void m2260a(IVideoRenderListener iVideoRenderListener) {
        this.f3509k = iVideoRenderListener;
    }

    /* renamed from: a */
    public void m2259a(OnContextListener onContextListener) {
        this.f3508j = onContextListener;
    }

    /* renamed from: a */
    public void m2266a() {
        TXCLog.m2915d("VideoGLGenerate", C2516Ad.TYPE_START);
        Handler handler = this.f3501c;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.e.aa.1
                @Override // java.lang.Runnable
                public void run() {
                    VideoGLGenerate.this.m2247f();
                    VideoGLGenerate.this.m2251d();
                }
            });
        }
    }

    /* renamed from: b */
    public void m2258b() {
        TXCLog.m2915d("VideoGLGenerate", "stop");
        Handler handler = this.f3501c;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.e.aa.2
                @Override // java.lang.Runnable
                public void run() {
                    VideoGLGenerate.this.m2249e();
                    VideoGLGenerate.this.m2246g();
                }
            });
        }
    }

    /* renamed from: c */
    public void m2254c() {
        if (this.f3501c != null) {
            HandlerThread handlerThread = this.f3502d;
            if (handlerThread != null) {
                if (Build.VERSION.SDK_INT >= 18) {
                    handlerThread.quitSafely();
                } else {
                    handlerThread.quit();
                }
                this.f3502d = null;
            }
            this.f3509k = null;
            this.f3508j = null;
            this.f3515q = null;
            this.f3501c = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: d */
    public void m2251d() {
        TXCLog.m2915d("VideoGLGenerate", "initTextureRender");
        this.f3506h = new TXCOesTextureRender(true);
        this.f3506h.m915b();
        this.f3507i = new TXCOesTextureRender(false);
        this.f3507i.m915b();
        this.f3510l = new SurfaceTexture(this.f3506h.m922a());
        this.f3511m = new Surface(this.f3510l);
        this.f3510l.setOnFrameAvailableListener(this.f3515q);
        this.f3513o = true;
        IVideoRenderListener iVideoRenderListener = this.f3509k;
        if (iVideoRenderListener != null) {
            iVideoRenderListener.mo1942a(this.f3511m);
        }
        OnContextListener onContextListener = this.f3508j;
        if (onContextListener != null) {
            onContextListener.mo1532a(this.f3505g.m3072d());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: e */
    public void m2249e() {
        TXCLog.m2915d("VideoGLGenerate", "destroyTextureRender");
        this.f3513o = false;
        TXCOesTextureRender tXCOesTextureRender = this.f3506h;
        if (tXCOesTextureRender != null) {
            tXCOesTextureRender.m913c();
        }
        this.f3506h = null;
        TXCOesTextureRender tXCOesTextureRender2 = this.f3507i;
        if (tXCOesTextureRender2 != null) {
            tXCOesTextureRender2.m913c();
        }
        this.f3507i = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: f */
    public void m2247f() {
        TXCLog.m2915d("VideoGLGenerate", "initEGL");
        this.f3505g = EGL14Helper.m3075a(null, null, null, this.f3503e, this.f3504f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: g */
    public void m2246g() {
        TXCLog.m2915d("VideoGLGenerate", "destroyEGL");
        IVideoRenderListener iVideoRenderListener = this.f3509k;
        if (iVideoRenderListener != null) {
            iVideoRenderListener.mo1940b(this.f3511m);
        }
        EGL14Helper eGL14Helper = this.f3505g;
        if (eGL14Helper != null) {
            eGL14Helper.m3074b();
            this.f3505g = null;
        }
    }

    /* renamed from: a */
    public synchronized void m2265a(final Frame frame) {
        if (this.f3501c != null) {
            this.f3501c.post(new Runnable() { // from class: com.tencent.liteav.e.aa.4
                @Override // java.lang.Runnable
                public void run() {
                    VideoGLGenerate.this.m2253c(frame);
                }
            });
        }
    }

    /* renamed from: b */
    public void m2257b(final Frame frame) {
        Handler handler = this.f3501c;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.e.aa.5
                @Override // java.lang.Runnable
                public void run() {
                    VideoGLGenerate.this.f3512n = true;
                    VideoGLGenerate.this.m2253c(frame);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: c */
    public boolean m2253c(Frame frame) {
        if (!this.f3513o) {
            return false;
        }
        if (frame.m2309p() || frame.m2307r()) {
            if (this.f3509k != null) {
                if (frame.m2300y() == 0) {
                    this.f3509k.mo1943a(frame.m2301x(), this.f3500b, frame);
                } else {
                    this.f3509k.mo1943a(this.f3506h.m922a(), this.f3500b, frame);
                }
            }
            return false;
        }
        synchronized (this) {
            if (this.f3512n) {
                boolean z = this.f3512n;
                this.f3512n = false;
                GLES20.glViewport(0, 0, this.f3503e, this.f3504f);
                if (!z) {
                    return true;
                }
                try {
                    if (this.f3510l != null) {
                        this.f3510l.updateTexImage();
                        this.f3510l.getTransformMatrix(this.f3500b);
                    }
                } catch (Exception unused) {
                }
                if (this.f3509k != null) {
                    if (frame.m2300y() == 0) {
                        this.f3509k.mo1943a(frame.m2301x(), this.f3500b, frame);
                        return true;
                    }
                    this.f3509k.mo1943a(this.f3506h.m922a(), this.f3500b, frame);
                    return true;
                }
                TXCOesTextureRender tXCOesTextureRender = this.f3507i;
                if (tXCOesTextureRender == null) {
                    return true;
                }
                tXCOesTextureRender.m918a(this.f3510l);
                return true;
            }
            this.f3514p = frame;
            return false;
        }
    }
}
