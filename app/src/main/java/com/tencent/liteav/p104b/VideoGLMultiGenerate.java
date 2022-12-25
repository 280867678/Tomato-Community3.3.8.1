package com.tencent.liteav.p104b;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Surface;
import com.one.tomato.entity.C2516Ad;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p109e.EGL14Helper;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p119d.Resolution;
import com.tencent.liteav.p120e.IVideoRenderListener;
import com.tencent.liteav.p120e.OnContextListener;
import com.tencent.liteav.renderer.TXCOesTextureRender;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tencent.liteav.b.i */
/* loaded from: classes3.dex */
public class VideoGLMultiGenerate {

    /* renamed from: c */
    private Handler f2288c;

    /* renamed from: d */
    private HandlerThread f2289d;

    /* renamed from: e */
    private int f2290e;

    /* renamed from: f */
    private int f2291f;

    /* renamed from: g */
    private EGL14Helper f2292g;

    /* renamed from: h */
    private TXCOesTextureRender f2293h;

    /* renamed from: i */
    private OnContextListener f2294i;

    /* renamed from: j */
    private boolean f2295j;

    /* renamed from: b */
    private final String f2287b = "VideoGLMultiGenerate";

    /* renamed from: a */
    public List<C3331a> f2286a = new ArrayList();

    /* compiled from: VideoGLMultiGenerate.java */
    /* renamed from: com.tencent.liteav.b.i$a */
    /* loaded from: classes3.dex */
    public class C3331a {

        /* renamed from: b */
        private int f2302b;

        /* renamed from: c */
        private float[] f2303c;

        /* renamed from: d */
        private int f2304d;

        /* renamed from: e */
        private int f2305e;

        /* renamed from: f */
        private TXCOesTextureRender f2306f;

        /* renamed from: g */
        private IVideoRenderListener f2307g;

        /* renamed from: h */
        private SurfaceTexture f2308h;

        /* renamed from: i */
        private Surface f2309i;

        /* renamed from: j */
        private boolean f2310j;

        /* renamed from: k */
        private Frame f2311k;

        /* renamed from: l */
        private SurfaceTexture.OnFrameAvailableListener f2312l = new SurfaceTexture.OnFrameAvailableListener() { // from class: com.tencent.liteav.b.i.a.1
            @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
            public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                TXCLog.m2915d("VideoGLMultiGenerate", "onFrameAvailable, index = " + C3331a.this.f2302b + ", mFrame = " + C3331a.this.f2311k);
                C3331a.this.f2310j = true;
                if (C3331a.this.f2311k != null) {
                    C3331a c3331a = C3331a.this;
                    VideoGLMultiGenerate.this.m3202b(c3331a.f2311k, C3331a.this.f2302b);
                    C3331a.this.f2311k = null;
                }
            }
        };

        public C3331a() {
        }
    }

    public VideoGLMultiGenerate(int i) {
        for (int i2 = 0; i2 < i; i2++) {
            C3331a c3331a = new C3331a();
            c3331a.f2302b = i2;
            c3331a.f2303c = new float[16];
            this.f2286a.add(c3331a);
        }
        this.f2289d = new HandlerThread("VideoGLMultiGenerate");
        this.f2289d.start();
        this.f2288c = new Handler(this.f2289d.getLooper());
    }

    /* renamed from: a */
    public void m3207a(Resolution resolution, int i) {
        List<C3331a> list = this.f2286a;
        if (list == null || list.size() == 0 || i >= this.f2286a.size()) {
            TXCLog.m2914e("VideoGLMultiGenerate", "setRenderResolution, mVideoGLInfoList is empty or mIndex is larger than size");
            return;
        }
        C3331a c3331a = this.f2286a.get(i);
        c3331a.f2304d = resolution.f3467a;
        c3331a.f2305e = resolution.f3468b;
        int i2 = resolution.f3467a;
        int i3 = this.f2290e;
        if (i2 <= i3) {
            i2 = i3;
        }
        this.f2290e = i2;
        int i4 = resolution.f3468b;
        int i5 = this.f2291f;
        if (i4 <= i5) {
            i4 = i5;
        }
        this.f2291f = i4;
        TXCLog.m2913i("VideoGLMultiGenerate", "setRenderResolution, mSurfaceWidth = " + this.f2290e + ", mSurfaceHeight = " + this.f2291f);
    }

    /* renamed from: a */
    public void m3206a(IVideoRenderListener iVideoRenderListener, int i) {
        List<C3331a> list = this.f2286a;
        if (list != null && list.size() != 0 && i < this.f2286a.size()) {
            this.f2286a.get(i).f2307g = iVideoRenderListener;
        } else {
            TXCLog.m2914e("VideoGLMultiGenerate", "setListener, mVideoGLInfoList is empty or mIndex is larger than size");
        }
    }

    /* renamed from: a */
    public void m3205a(OnContextListener onContextListener) {
        this.f2294i = onContextListener;
    }

    /* renamed from: a */
    public void m3211a() {
        TXCLog.m2915d("VideoGLMultiGenerate", C2516Ad.TYPE_START);
        Handler handler = this.f2288c;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.b.i.1
                @Override // java.lang.Runnable
                public void run() {
                    VideoGLMultiGenerate.this.m3197e();
                    VideoGLMultiGenerate.this.m3201c();
                }
            });
        }
    }

    /* renamed from: b */
    public void m3204b() {
        TXCLog.m2915d("VideoGLMultiGenerate", "stop");
        Handler handler = this.f2288c;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.b.i.2
                @Override // java.lang.Runnable
                public void run() {
                    VideoGLMultiGenerate.this.m3199d();
                    VideoGLMultiGenerate.this.m3196f();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: c */
    public void m3201c() {
        TXCLog.m2915d("VideoGLMultiGenerate", "initTextureRender");
        this.f2293h = new TXCOesTextureRender(false);
        this.f2293h.m915b();
        for (int i = 0; i < this.f2286a.size(); i++) {
            C3331a c3331a = this.f2286a.get(i);
            c3331a.f2306f = new TXCOesTextureRender(true);
            c3331a.f2306f.m915b();
            c3331a.f2308h = new SurfaceTexture(c3331a.f2306f.m922a());
            c3331a.f2309i = new Surface(c3331a.f2308h);
            c3331a.f2308h.setOnFrameAvailableListener(c3331a.f2312l);
            if (c3331a.f2307g != null) {
                c3331a.f2307g.mo1942a(c3331a.f2309i);
            }
            if (i == this.f2286a.size() - 1) {
                this.f2295j = true;
            }
        }
        OnContextListener onContextListener = this.f2294i;
        if (onContextListener != null) {
            onContextListener.mo1532a(this.f2292g.m3072d());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: d */
    public void m3199d() {
        TXCLog.m2915d("VideoGLMultiGenerate", "destroyTextureRender");
        this.f2295j = false;
        for (int i = 0; i < this.f2286a.size(); i++) {
            C3331a c3331a = this.f2286a.get(i);
            if (c3331a.f2306f != null) {
                c3331a.f2306f.m913c();
                c3331a.f2306f = null;
                if (c3331a.f2308h != null) {
                    c3331a.f2308h.setOnFrameAvailableListener(null);
                    c3331a.f2308h.release();
                    c3331a.f2308h = null;
                }
                if (c3331a.f2309i != null) {
                    c3331a.f2309i.release();
                    c3331a.f2309i = null;
                }
                c3331a.f2308h = null;
                c3331a.f2311k = null;
                c3331a.f2310j = false;
                c3331a.f2303c = new float[16];
            }
        }
        TXCOesTextureRender tXCOesTextureRender = this.f2293h;
        if (tXCOesTextureRender != null) {
            tXCOesTextureRender.m913c();
        }
        this.f2293h = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: e */
    public void m3197e() {
        TXCLog.m2915d("VideoGLMultiGenerate", "initEGL");
        this.f2292g = EGL14Helper.m3075a(null, null, null, this.f2290e, this.f2291f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: f */
    public void m3196f() {
        TXCLog.m2915d("VideoGLMultiGenerate", "destroyEGL");
        for (int i = 0; i < this.f2286a.size(); i++) {
            C3331a c3331a = this.f2286a.get(i);
            if (c3331a.f2307g != null) {
                c3331a.f2307g.mo1940b(c3331a.f2309i);
            }
        }
        EGL14Helper eGL14Helper = this.f2292g;
        if (eGL14Helper != null) {
            eGL14Helper.m3074b();
            this.f2292g = null;
        }
    }

    /* renamed from: a */
    public synchronized void m3208a(final Frame frame, final int i) {
        if (this.f2286a != null && this.f2286a.size() != 0 && i < this.f2286a.size()) {
            if (this.f2288c != null) {
                this.f2288c.post(new Runnable() { // from class: com.tencent.liteav.b.i.3
                    @Override // java.lang.Runnable
                    public void run() {
                        VideoGLMultiGenerate.this.m3202b(frame, i);
                    }
                });
            }
            return;
        }
        TXCLog.m2914e("VideoGLMultiGenerate", "setListener, mVideoGLInfoList is empty or mIndex is larger than size");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public boolean m3202b(Frame frame, int i) {
        if (!this.f2295j) {
            return false;
        }
        C3331a c3331a = this.f2286a.get(i);
        TXCLog.m2915d("VideoGLMultiGenerate", "onDrawFrame, mTextureAvailable = " + c3331a.f2310j + ", mIndex = " + i);
        if (frame.m2309p() || frame.m2307r()) {
            if (c3331a.f2307g != null) {
                if (frame.m2300y() == 0) {
                    c3331a.f2307g.mo1943a(frame.m2301x(), c3331a.f2303c, frame);
                } else {
                    c3331a.f2307g.mo1943a(c3331a.f2306f.m922a(), c3331a.f2303c, frame);
                }
            }
            return false;
        }
        synchronized (this) {
            if (c3331a.f2310j) {
                boolean z = c3331a.f2310j;
                c3331a.f2310j = false;
                GLES20.glViewport(0, 0, c3331a.f2304d, c3331a.f2305e);
                if (!z) {
                    return true;
                }
                try {
                    if (c3331a.f2308h != null) {
                        c3331a.f2308h.updateTexImage();
                        c3331a.f2308h.getTransformMatrix(c3331a.f2303c);
                    }
                } catch (Exception unused) {
                }
                if (c3331a.f2307g != null) {
                    if (frame.m2300y() == 0) {
                        c3331a.f2307g.mo1943a(frame.m2301x(), c3331a.f2303c, frame);
                        return true;
                    }
                    c3331a.f2307g.mo1943a(c3331a.f2306f.m922a(), c3331a.f2303c, frame);
                    return true;
                }
                TXCOesTextureRender tXCOesTextureRender = this.f2293h;
                if (tXCOesTextureRender == null) {
                    return true;
                }
                tXCOesTextureRender.m918a(c3331a.f2308h);
                return true;
            }
            c3331a.f2311k = frame;
            return false;
        }
    }
}
