package com.tencent.liteav.p122g;

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
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tencent.liteav.g.o */
/* loaded from: classes3.dex */
public class VideoJoinGLRender {

    /* renamed from: a */
    private Context f4177a;

    /* renamed from: c */
    private IVideoJoinRenderListener f4179c;

    /* renamed from: d */
    private FrameLayout f4180d;

    /* renamed from: e */
    private TextureView f4181e;

    /* renamed from: f */
    private int f4182f;

    /* renamed from: g */
    private int f4183g;

    /* renamed from: j */
    private TXCOesTextureRender f4186j;

    /* renamed from: k */
    private SurfaceTexture f4187k;

    /* renamed from: l */
    private boolean f4188l;

    /* renamed from: m */
    private boolean f4189m;

    /* renamed from: o */
    private TextureView.SurfaceTextureListener f4191o = new TextureView.SurfaceTextureListener() { // from class: com.tencent.liteav.g.o.1
        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
            TXCLog.m2915d("VideoJoinGLRender", "onSurfaceTextureAvailable surface:" + surfaceTexture + ",width:" + i + ",height:" + i2 + ", mSaveSurfaceTexture = " + VideoJoinGLRender.this.f4187k);
            VideoJoinGLRender.this.f4182f = i;
            VideoJoinGLRender.this.f4183g = i2;
            if (VideoJoinGLRender.this.f4187k == null) {
                VideoJoinGLRender.this.f4187k = surfaceTexture;
                VideoJoinGLRender.this.m1588a(surfaceTexture);
            } else if (Build.VERSION.SDK_INT < 16) {
            } else {
                VideoJoinGLRender.this.f4181e.setSurfaceTexture(VideoJoinGLRender.this.f4187k);
            }
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
            TXCLog.m2915d("VideoJoinGLRender", "onSurfaceTextureSizeChanged surface:" + surfaceTexture + ",width:" + i + ",height:" + i2);
            VideoJoinGLRender.this.f4182f = i;
            VideoJoinGLRender.this.f4183g = i2;
            if (VideoJoinGLRender.this.f4179c != null) {
                VideoJoinGLRender.this.f4179c.mo1492a(i, i2);
            }
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            TXCLog.m2915d("VideoJoinGLRender", "onSurfaceTextureDestroyed surface:" + surfaceTexture);
            if (!VideoJoinGLRender.this.f4188l) {
                VideoJoinGLRender.this.f4187k = null;
                VideoJoinGLRender.this.m1577a(false);
            }
            return false;
        }
    };

    /* renamed from: n */
    private ArrayList<Surface> f4190n = new ArrayList<>();

    /* renamed from: b */
    private C3469d f4178b = new C3469d();

    /* renamed from: i */
    private HandlerThread f4185i = new HandlerThread("VideoJoinGLRender");

    /* renamed from: h */
    private Handler f4184h = new Handler(this.f4185i.getLooper());

    public VideoJoinGLRender(Context context) {
        this.f4177a = context;
        this.f4185i.start();
    }

    /* renamed from: a */
    public void m1586a(IVideoJoinRenderListener iVideoJoinRenderListener) {
        this.f4179c = iVideoJoinRenderListener;
    }

    /* renamed from: a */
    public void m1578a(TXCVideoEditConstants.C3516f c3516f) {
        FrameLayout frameLayout = this.f4180d;
        if (frameLayout != null) {
            frameLayout.removeAllViews();
        }
        FrameLayout frameLayout2 = c3516f.f4378a;
        if (frameLayout2 == null) {
            TXCLog.m2911w("VideoJoinGLRender", "initWithPreview param.videoView is NULL!!!");
            return;
        }
        FrameLayout frameLayout3 = this.f4180d;
        if (frameLayout3 == null || !frameLayout2.equals(frameLayout3)) {
            this.f4181e = new TextureView(this.f4177a);
            this.f4181e.setSurfaceTextureListener(this.f4191o);
        }
        this.f4180d = frameLayout2;
        this.f4180d.addView(this.f4181e);
    }

    /* renamed from: a */
    public int m1590a() {
        return this.f4182f;
    }

    /* renamed from: b */
    public int m1576b() {
        return this.f4183g;
    }

    /* renamed from: c */
    public void m1571c() {
        this.f4188l = true;
    }

    /* renamed from: d */
    public void m1569d() {
        this.f4188l = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m1588a(final SurfaceTexture surfaceTexture) {
        Handler handler = this.f4184h;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.g.o.2
                @Override // java.lang.Runnable
                public void run() {
                    VideoJoinGLRender.this.f4178b.m1879a(surfaceTexture);
                    VideoJoinGLRender.this.m1567e();
                    if (VideoJoinGLRender.this.f4179c != null) {
                        VideoJoinGLRender.this.f4179c.mo1490a(VideoJoinGLRender.this.f4190n);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m1577a(final boolean z) {
        Handler handler = this.f4184h;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.g.o.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (VideoJoinGLRender.this.f4184h == null) {
                            return;
                        }
                        if (VideoJoinGLRender.this.f4179c != null) {
                            VideoJoinGLRender.this.f4179c.mo1489b(VideoJoinGLRender.this.f4190n);
                        }
                        VideoJoinGLRender.this.m1565f();
                        VideoJoinGLRender.this.f4178b.m1880a();
                        if (!z) {
                            return;
                        }
                        VideoJoinGLRender.this.f4184h = null;
                        if (VideoJoinGLRender.this.f4185i == null) {
                            return;
                        }
                        VideoJoinGLRender.this.f4185i.quit();
                        VideoJoinGLRender.this.f4185i = null;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: e */
    public void m1567e() {
        TXCLog.m2913i("VideoJoinGLRender", "initTextureRender");
        this.f4186j = new TXCOesTextureRender(false);
        this.f4186j.m915b();
        List<VideoExtractConfig> m1477c = VideoSourceListConfig.m1480a().m1477c();
        for (int i = 0; i < m1477c.size(); i++) {
            final VideoExtractConfig videoExtractConfig = m1477c.get(i);
            final VideoGLTextureInfo videoGLTextureInfo = new VideoGLTextureInfo();
            videoGLTextureInfo.f4107e = new float[16];
            videoGLTextureInfo.f4103a = new TXCOesTextureRender(true);
            videoGLTextureInfo.f4103a.m915b();
            videoGLTextureInfo.f4104b = new SurfaceTexture(videoGLTextureInfo.f4103a.m922a());
            videoGLTextureInfo.f4105c = new Surface(videoGLTextureInfo.f4104b);
            videoGLTextureInfo.f4104b.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() { // from class: com.tencent.liteav.g.o.4
                @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
                public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                    VideoGLTextureInfo videoGLTextureInfo2 = videoGLTextureInfo;
                    videoGLTextureInfo2.f4106d = true;
                    Frame frame = videoGLTextureInfo2.f4108f;
                    if (frame == null || !VideoJoinGLRender.this.m1575b(frame, videoExtractConfig)) {
                        return;
                    }
                    videoGLTextureInfo.f4108f = null;
                    VideoJoinGLRender.this.f4178b.m1877b();
                }
            });
            videoExtractConfig.f4090b = videoGLTextureInfo;
            this.f4190n.add(videoGLTextureInfo.f4105c);
        }
        this.f4189m = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: f */
    public void m1565f() {
        TXCLog.m2913i("VideoJoinGLRender", "destroyTextureRender");
        this.f4189m = false;
        List<VideoExtractConfig> m1477c = VideoSourceListConfig.m1480a().m1477c();
        for (int i = 0; i < m1477c.size(); i++) {
            VideoGLTextureInfo videoGLTextureInfo = m1477c.get(i).f4090b;
            TXCOesTextureRender tXCOesTextureRender = videoGLTextureInfo.f4103a;
            if (tXCOesTextureRender != null) {
                tXCOesTextureRender.m913c();
            }
            videoGLTextureInfo.f4103a = null;
            SurfaceTexture surfaceTexture = videoGLTextureInfo.f4104b;
            if (surfaceTexture != null) {
                surfaceTexture.setOnFrameAvailableListener(null);
                videoGLTextureInfo.f4104b.release();
            }
            videoGLTextureInfo.f4104b = null;
            Surface surface = videoGLTextureInfo.f4105c;
            if (surface != null) {
                surface.release();
            }
            videoGLTextureInfo.f4105c = null;
        }
        TXCOesTextureRender tXCOesTextureRender2 = this.f4186j;
        if (tXCOesTextureRender2 != null) {
            tXCOesTextureRender2.m913c();
        }
        this.f4186j = null;
    }

    /* renamed from: a */
    public void m1589a(int i, int i2, int i3) {
        GLES20.glViewport(0, 0, i2, i3);
        TXCOesTextureRender tXCOesTextureRender = this.f4186j;
        if (tXCOesTextureRender != null) {
            tXCOesTextureRender.m919a(i, false, 0);
        }
    }

    /* renamed from: a */
    public void m1587a(final Frame frame, final VideoExtractConfig videoExtractConfig) {
        Handler handler = this.f4184h;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.g.o.5
                @Override // java.lang.Runnable
                public void run() {
                    if (VideoJoinGLRender.this.m1575b(frame, videoExtractConfig)) {
                        VideoJoinGLRender.this.f4178b.m1877b();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public boolean m1575b(Frame frame, VideoExtractConfig videoExtractConfig) {
        if (!this.f4189m) {
            return false;
        }
        VideoGLTextureInfo videoGLTextureInfo = videoExtractConfig.f4090b;
        if (frame.m2309p()) {
            TXCLog.m2915d("VideoJoinGLRender", "onDrawFrame, frame isEndFrame");
            if (this.f4179c != null) {
                if (frame.m2300y() == 0) {
                    this.f4179c.mo1491a(frame.m2301x(), videoGLTextureInfo.f4107e, frame);
                } else {
                    this.f4179c.mo1491a(videoGLTextureInfo.f4103a.m922a(), videoGLTextureInfo.f4107e, frame);
                }
            }
            return false;
        }
        synchronized (this) {
            if (videoGLTextureInfo.f4106d) {
                boolean z = videoGLTextureInfo.f4106d;
                videoGLTextureInfo.f4106d = false;
                GLES20.glViewport(0, 0, this.f4182f, this.f4183g);
                if (!z) {
                    return true;
                }
                SurfaceTexture surfaceTexture = videoGLTextureInfo.f4104b;
                if (surfaceTexture != null) {
                    surfaceTexture.updateTexImage();
                    videoGLTextureInfo.f4104b.getTransformMatrix(videoGLTextureInfo.f4107e);
                }
                if (this.f4179c != null) {
                    if (frame.m2300y() == 0) {
                        this.f4179c.mo1491a(frame.m2301x(), videoGLTextureInfo.f4107e, frame);
                        return true;
                    }
                    this.f4179c.mo1491a(videoGLTextureInfo.f4103a.m922a(), videoGLTextureInfo.f4107e, frame);
                    return true;
                }
                TXCOesTextureRender tXCOesTextureRender = this.f4186j;
                if (tXCOesTextureRender == null) {
                    return true;
                }
                tXCOesTextureRender.m918a(videoGLTextureInfo.f4104b);
                return true;
            }
            videoGLTextureInfo.f4108f = frame;
            return false;
        }
    }
}
