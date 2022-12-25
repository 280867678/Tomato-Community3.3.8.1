package com.tencent.liteav.p120e;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Surface;
import android.view.TextureView;
import android.widget.FrameLayout;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p121f.C3469d;
import com.tencent.liteav.p124i.TXCVideoEditConstants;
import com.tencent.liteav.renderer.TXCOesTextureRender;

/* renamed from: com.tencent.liteav.e.ab */
/* loaded from: classes3.dex */
public class VideoGLRender {

    /* renamed from: a */
    private final Context f3523a;

    /* renamed from: d */
    private IVideoRenderListener f3526d;

    /* renamed from: e */
    private FrameLayout f3527e;

    /* renamed from: f */
    private TextureView f3528f;

    /* renamed from: g */
    private int f3529g;

    /* renamed from: h */
    private int f3530h;

    /* renamed from: k */
    private TXCOesTextureRender f3533k;

    /* renamed from: l */
    private TXCOesTextureRender f3534l;

    /* renamed from: m */
    private SurfaceTexture f3535m;

    /* renamed from: n */
    private SurfaceTexture f3536n;

    /* renamed from: o */
    private Surface f3537o;

    /* renamed from: p */
    private boolean f3538p;

    /* renamed from: q */
    private Frame f3539q;

    /* renamed from: r */
    private boolean f3540r;

    /* renamed from: s */
    private boolean f3541s;

    /* renamed from: t */
    private TextureView.SurfaceTextureListener f3542t = new TextureView.SurfaceTextureListener() { // from class: com.tencent.liteav.e.ab.1
        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
            TXCLog.m2915d("VideoGLRender", "onSurfaceTextureAvailable surface:" + surfaceTexture + ",width:" + i + ",height:" + i2 + ", mSaveSurfaceTexture = " + VideoGLRender.this.f3536n);
            VideoGLRender.this.f3529g = i;
            VideoGLRender.this.f3530h = i2;
            if (VideoGLRender.this.f3536n == null) {
                VideoGLRender.this.f3536n = surfaceTexture;
                VideoGLRender.this.m2243a(surfaceTexture);
            } else if (Build.VERSION.SDK_INT < 16) {
            } else {
                VideoGLRender.this.f3528f.setSurfaceTexture(VideoGLRender.this.f3536n);
            }
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
            TXCLog.m2915d("VideoGLRender", "wonSurfaceTextureSizeChanged surface:" + surfaceTexture + ",width:" + i + ",height:" + i2);
            VideoGLRender.this.f3529g = i;
            VideoGLRender.this.f3530h = i2;
            if (VideoGLRender.this.f3526d != null) {
                VideoGLRender.this.f3526d.mo1944a(i, i2);
            }
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            TXCLog.m2915d("VideoGLRender", "onSurfaceTextureDestroyed surface:" + surfaceTexture);
            if (!VideoGLRender.this.f3540r) {
                VideoGLRender.this.f3536n = null;
                VideoGLRender.this.m2225b(false);
            }
            return false;
        }
    };

    /* renamed from: u */
    private SurfaceTexture.OnFrameAvailableListener f3543u = new SurfaceTexture.OnFrameAvailableListener() { // from class: com.tencent.liteav.e.ab.4
        @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
            synchronized (this) {
                VideoGLRender.this.f3538p = true;
            }
        }
    };

    /* renamed from: b */
    private float[] f3524b = new float[16];

    /* renamed from: c */
    private C3469d f3525c = new C3469d();

    /* renamed from: j */
    private HandlerThread f3532j = new HandlerThread("VideoGLRender");

    /* renamed from: i */
    private Handler f3531i = new Handler(this.f3532j.getLooper());

    public VideoGLRender(Context context) {
        this.f3523a = context;
        this.f3532j.start();
    }

    /* renamed from: a */
    public void m2234a(IVideoRenderListener iVideoRenderListener) {
        this.f3526d = iVideoRenderListener;
    }

    /* renamed from: a */
    public void m2233a(TXCVideoEditConstants.C3516f c3516f) {
        FrameLayout frameLayout = this.f3527e;
        if (frameLayout != null) {
            frameLayout.removeAllViews();
        }
        FrameLayout frameLayout2 = c3516f.f4378a;
        if (frameLayout2 == null) {
            TXCLog.m2911w("VideoGLRender", "initWithPreview param.videoView is NULL!!!");
            return;
        }
        FrameLayout frameLayout3 = this.f3527e;
        if (frameLayout3 == null || !frameLayout2.equals(frameLayout3)) {
            this.f3528f = new TextureView(this.f3523a);
            this.f3528f.setSurfaceTextureListener(this.f3542t);
        }
        this.f3527e = frameLayout2;
        this.f3527e.addView(this.f3528f);
    }

    /* renamed from: a */
    public int m2245a() {
        return this.f3529g;
    }

    /* renamed from: b */
    public int m2231b() {
        return this.f3530h;
    }

    /* renamed from: c */
    public void m2224c() {
        this.f3540r = true;
    }

    /* renamed from: d */
    public void m2221d() {
        this.f3540r = false;
    }

    /* renamed from: e */
    public void m2219e() {
        this.f3540r = false;
        m2225b(true);
        TextureView textureView = this.f3528f;
        if (textureView != null) {
            textureView.setSurfaceTextureListener(null);
            this.f3528f = null;
        }
        FrameLayout frameLayout = this.f3527e;
        if (frameLayout != null) {
            frameLayout.removeAllViews();
            this.f3527e = null;
        }
        if (this.f3526d != null) {
            this.f3526d = null;
        }
        this.f3542t = null;
        this.f3543u = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m2243a(final SurfaceTexture surfaceTexture) {
        Handler handler = this.f3531i;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.e.ab.2
                @Override // java.lang.Runnable
                public void run() {
                    VideoGLRender.this.f3525c.m1879a(surfaceTexture);
                    VideoGLRender.this.m2217f();
                    if (VideoGLRender.this.f3526d != null) {
                        VideoGLRender.this.f3526d.mo1942a(VideoGLRender.this.f3537o);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public void m2225b(final boolean z) {
        Handler handler = this.f3531i;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.e.ab.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (VideoGLRender.this.f3531i == null) {
                            return;
                        }
                        if (VideoGLRender.this.f3526d != null) {
                            VideoGLRender.this.f3526d.mo1940b(VideoGLRender.this.f3537o);
                        }
                        VideoGLRender.this.m2215g();
                        VideoGLRender.this.f3525c.m1880a();
                        if (!z) {
                            return;
                        }
                        VideoGLRender.this.f3531i = null;
                        if (VideoGLRender.this.f3532j == null) {
                            return;
                        }
                        VideoGLRender.this.f3532j.quit();
                        VideoGLRender.this.f3532j = null;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: f */
    public void m2217f() {
        this.f3533k = new TXCOesTextureRender(true);
        this.f3533k.m915b();
        this.f3534l = new TXCOesTextureRender(false);
        this.f3534l.m915b();
        this.f3535m = new SurfaceTexture(this.f3533k.m922a());
        this.f3537o = new Surface(this.f3535m);
        this.f3535m.setOnFrameAvailableListener(this.f3543u);
        this.f3541s = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: g */
    public void m2215g() {
        this.f3541s = false;
        TXCOesTextureRender tXCOesTextureRender = this.f3533k;
        if (tXCOesTextureRender != null) {
            tXCOesTextureRender.m913c();
        }
        this.f3533k = null;
        TXCOesTextureRender tXCOesTextureRender2 = this.f3534l;
        if (tXCOesTextureRender2 != null) {
            tXCOesTextureRender2.m913c();
        }
        this.f3534l = null;
        SurfaceTexture surfaceTexture = this.f3535m;
        if (surfaceTexture != null) {
            surfaceTexture.setOnFrameAvailableListener(null);
            this.f3535m.release();
            this.f3535m = null;
        }
        Surface surface = this.f3537o;
        if (surface != null) {
            surface.release();
            this.f3537o = null;
        }
    }

    /* renamed from: a */
    public void m2244a(int i, int i2, int i3) {
        GLES20.glViewport(0, 0, i2, i3);
        TXCOesTextureRender tXCOesTextureRender = this.f3534l;
        if (tXCOesTextureRender != null) {
            tXCOesTextureRender.m919a(i, false, 0);
        }
    }

    /* renamed from: a */
    public void m2242a(final Frame frame) {
        Handler handler = this.f3531i;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.e.ab.5
                @Override // java.lang.Runnable
                public void run() {
                    if (VideoGLRender.this.m2223c(frame)) {
                        VideoGLRender.this.f3525c.m1877b();
                    }
                }
            });
        }
    }

    /* renamed from: a */
    public void m2232a(final boolean z) {
        Handler handler = this.f3531i;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.e.ab.6
                @Override // java.lang.Runnable
                public void run() {
                    if (VideoGLRender.this.f3526d != null) {
                        VideoGLRender.this.f3526d.mo1941a(z);
                        VideoGLRender.this.f3525c.m1877b();
                    }
                }
            });
        }
    }

    /* renamed from: b */
    public void m2230b(final Frame frame) {
        Handler handler = this.f3531i;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.e.ab.7
                @Override // java.lang.Runnable
                public void run() {
                    VideoGLRender.this.f3538p = true;
                    VideoGLRender.this.m2223c(frame);
                    VideoGLRender.this.f3525c.m1877b();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: c */
    public boolean m2223c(Frame frame) {
        if (!this.f3541s) {
            return false;
        }
        if (frame.m2309p()) {
            TXCLog.m2915d("VideoGLRender", "onDrawFrame, frame isEndFrame");
            if (this.f3526d != null) {
                if (frame.m2300y() == 0) {
                    this.f3526d.mo1943a(frame.m2301x(), this.f3524b, frame);
                } else {
                    this.f3526d.mo1943a(this.f3533k.m922a(), this.f3524b, frame);
                }
            }
            return false;
        }
        this.f3539q = frame;
        synchronized (this) {
            if (!this.f3538p) {
                return false;
            }
            boolean z = this.f3538p;
            this.f3538p = false;
            GLES20.glViewport(0, 0, this.f3529g, this.f3530h);
            if (!z) {
                return true;
            }
            SurfaceTexture surfaceTexture = this.f3535m;
            if (surfaceTexture != null) {
                surfaceTexture.updateTexImage();
                this.f3535m.getTransformMatrix(this.f3524b);
            }
            if (this.f3526d != null) {
                if (frame.m2300y() == 0) {
                    this.f3526d.mo1943a(frame.m2301x(), this.f3524b, frame);
                    return true;
                }
                this.f3526d.mo1943a(this.f3533k.m922a(), this.f3524b, frame);
                return true;
            }
            TXCOesTextureRender tXCOesTextureRender = this.f3534l;
            if (tXCOesTextureRender == null) {
                return true;
            }
            tXCOesTextureRender.m918a(this.f3535m);
            return true;
        }
    }
}
