package com.tencent.liteav.p122g;

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
import com.tencent.liteav.p120e.OnContextListener;
import com.tencent.liteav.renderer.TXCOesTextureRender;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tencent.liteav.g.n */
/* loaded from: classes3.dex */
public class VideoJoinGLGenerate {

    /* renamed from: e */
    private int f4161e;

    /* renamed from: f */
    private int f4162f;

    /* renamed from: g */
    private EGL14Helper f4163g;

    /* renamed from: h */
    private TXCOesTextureRender f4164h;

    /* renamed from: i */
    private OnContextListener f4165i;

    /* renamed from: j */
    private IVideoJoinRenderListener f4166j;

    /* renamed from: k */
    private boolean f4167k;

    /* renamed from: l */
    private VideoExtractListConfig f4168l;

    /* renamed from: a */
    private final String f4157a = "VideoJoinGLGenerate";

    /* renamed from: b */
    private ArrayList<Surface> f4158b = new ArrayList<>();

    /* renamed from: d */
    private HandlerThread f4160d = new HandlerThread("VideoJoinGLGenerate");

    /* renamed from: c */
    private Handler f4159c = new Handler(this.f4160d.getLooper());

    public VideoJoinGLGenerate() {
        this.f4160d.start();
    }

    /* renamed from: a */
    public void m1605a(Resolution resolution) {
        this.f4161e = resolution.f3467a;
        this.f4162f = resolution.f3468b;
    }

    /* renamed from: a */
    public void m1602a(VideoExtractListConfig videoExtractListConfig) {
        this.f4168l = videoExtractListConfig;
    }

    /* renamed from: a */
    public void m1603a(IVideoJoinRenderListener iVideoJoinRenderListener) {
        this.f4166j = iVideoJoinRenderListener;
    }

    /* renamed from: a */
    public void m1604a(OnContextListener onContextListener) {
        this.f4165i = onContextListener;
    }

    /* renamed from: a */
    public void m1607a() {
        TXCLog.m2915d("VideoJoinGLGenerate", C2516Ad.TYPE_START);
        Handler handler = this.f4159c;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.g.n.1
                @Override // java.lang.Runnable
                public void run() {
                    VideoJoinGLGenerate.this.m1592e();
                    VideoJoinGLGenerate.this.m1596c();
                }
            });
        }
    }

    /* renamed from: b */
    public void m1599b() {
        TXCLog.m2915d("VideoJoinGLGenerate", "stop");
        Handler handler = this.f4159c;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.g.n.2
                @Override // java.lang.Runnable
                public void run() {
                    VideoJoinGLGenerate.this.m1594d();
                    VideoJoinGLGenerate.this.m1591f();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: c */
    public void m1596c() {
        TXCLog.m2915d("VideoJoinGLGenerate", "initTextureRender");
        this.f4164h = new TXCOesTextureRender(false);
        this.f4164h.m915b();
        List<VideoExtractConfig> m1680a = this.f4168l.m1680a();
        for (int i = 0; i < m1680a.size(); i++) {
            final VideoExtractConfig videoExtractConfig = m1680a.get(i);
            final VideoGLTextureInfo videoGLTextureInfo = new VideoGLTextureInfo();
            videoGLTextureInfo.f4107e = new float[16];
            videoGLTextureInfo.f4103a = new TXCOesTextureRender(true);
            videoGLTextureInfo.f4103a.m915b();
            videoGLTextureInfo.f4104b = new SurfaceTexture(videoGLTextureInfo.f4103a.m922a());
            videoGLTextureInfo.f4105c = new Surface(videoGLTextureInfo.f4104b);
            videoGLTextureInfo.f4104b.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() { // from class: com.tencent.liteav.g.n.3
                @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
                public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                    VideoGLTextureInfo videoGLTextureInfo2 = videoGLTextureInfo;
                    videoGLTextureInfo2.f4106d = true;
                    Frame frame = videoGLTextureInfo2.f4108f;
                    if (frame != null) {
                        VideoJoinGLGenerate.this.m1598b(frame, videoExtractConfig);
                        videoGLTextureInfo.f4108f = null;
                    }
                }
            });
            videoExtractConfig.f4090b = videoGLTextureInfo;
            this.f4158b.add(videoGLTextureInfo.f4105c);
        }
        this.f4167k = true;
        IVideoJoinRenderListener iVideoJoinRenderListener = this.f4166j;
        if (iVideoJoinRenderListener != null) {
            iVideoJoinRenderListener.mo1490a(this.f4158b);
        }
        OnContextListener onContextListener = this.f4165i;
        if (onContextListener != null) {
            onContextListener.mo1532a(this.f4163g.m3072d());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: d */
    public void m1594d() {
        TXCLog.m2913i("VideoJoinGLGenerate", "destroyTextureRender mVideoExtractListConfig:" + this.f4168l);
        this.f4167k = false;
        VideoExtractListConfig videoExtractListConfig = this.f4168l;
        if (videoExtractListConfig != null) {
            List<VideoExtractConfig> m1680a = videoExtractListConfig.m1680a();
            for (int i = 0; i < m1680a.size(); i++) {
                VideoGLTextureInfo videoGLTextureInfo = m1680a.get(i).f4090b;
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
        }
        TXCOesTextureRender tXCOesTextureRender2 = this.f4164h;
        if (tXCOesTextureRender2 != null) {
            tXCOesTextureRender2.m913c();
        }
        this.f4164h = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: e */
    public void m1592e() {
        TXCLog.m2915d("VideoJoinGLGenerate", "initEGL");
        this.f4163g = EGL14Helper.m3075a(null, null, null, this.f4161e, this.f4162f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: f */
    public void m1591f() {
        TXCLog.m2915d("VideoJoinGLGenerate", "destroyEGL");
        IVideoJoinRenderListener iVideoJoinRenderListener = this.f4166j;
        if (iVideoJoinRenderListener != null) {
            iVideoJoinRenderListener.mo1489b(this.f4158b);
        }
        EGL14Helper eGL14Helper = this.f4163g;
        if (eGL14Helper != null) {
            eGL14Helper.m3074b();
            this.f4163g = null;
        }
    }

    /* renamed from: a */
    public synchronized void m1606a(final Frame frame, final VideoExtractConfig videoExtractConfig) {
        if (this.f4159c != null) {
            this.f4159c.post(new Runnable() { // from class: com.tencent.liteav.g.n.4
                @Override // java.lang.Runnable
                public void run() {
                    VideoJoinGLGenerate.this.m1598b(frame, videoExtractConfig);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public boolean m1598b(Frame frame, VideoExtractConfig videoExtractConfig) {
        if (!this.f4167k) {
            return false;
        }
        VideoGLTextureInfo videoGLTextureInfo = videoExtractConfig.f4090b;
        if (frame.m2309p()) {
            if (this.f4166j != null) {
                if (frame.m2300y() == 0) {
                    this.f4166j.mo1491a(frame.m2301x(), videoGLTextureInfo.f4107e, frame);
                } else {
                    this.f4166j.mo1491a(videoGLTextureInfo.f4103a.m922a(), videoGLTextureInfo.f4107e, frame);
                }
            }
            return false;
        }
        synchronized (this) {
            if (videoGLTextureInfo.f4106d) {
                boolean z = videoGLTextureInfo.f4106d;
                videoGLTextureInfo.f4106d = false;
                GLES20.glViewport(0, 0, this.f4161e, this.f4162f);
                if (!z) {
                    return true;
                }
                try {
                    if (videoGLTextureInfo.f4104b != null) {
                        videoGLTextureInfo.f4104b.updateTexImage();
                        videoGLTextureInfo.f4104b.getTransformMatrix(videoGLTextureInfo.f4107e);
                    }
                } catch (Exception unused) {
                }
                if (this.f4166j != null) {
                    if (frame.m2300y() == 0) {
                        this.f4166j.mo1491a(frame.m2301x(), videoGLTextureInfo.f4107e, frame);
                        return true;
                    }
                    this.f4166j.mo1491a(videoGLTextureInfo.f4103a.m922a(), videoGLTextureInfo.f4107e, frame);
                    return true;
                }
                TXCOesTextureRender tXCOesTextureRender = this.f4164h;
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
