package com.tencent.liteav.p122g;

import android.content.Context;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p119d.Resolution;
import com.tencent.liteav.p120e.AudioTrackRender;
import com.tencent.liteav.p120e.IAudioPreprocessListener;
import com.tencent.liteav.p120e.IVideoProcessorListener;
import com.tencent.liteav.p121f.AudioPreprocessChain;
import com.tencent.liteav.p124i.TXCVideoEditConstants;
import com.tencent.liteav.p124i.TXCVideoJoiner;
import java.util.List;

/* renamed from: com.tencent.liteav.g.r */
/* loaded from: classes3.dex */
public class VideoJoinPreview {

    /* renamed from: b */
    private VideoJoinGLRender f4247b;

    /* renamed from: d */
    private VideoJoinPreprocessChain f4249d;

    /* renamed from: f */
    private AudioPreprocessChain f4251f;

    /* renamed from: g */
    private boolean f4252g;

    /* renamed from: h */
    private List<Surface> f4253h;

    /* renamed from: i */
    private TXCVideoJoiner.AbstractC3528b f4254i;

    /* renamed from: a */
    private final String f4246a = "VideoJoinPreview";

    /* renamed from: j */
    private IVideoJoinDecoderListener f4255j = new IVideoJoinDecoderListener() { // from class: com.tencent.liteav.g.r.1
        @Override // com.tencent.liteav.p122g.IVideoJoinDecoderListener
        /* renamed from: a */
        public void mo1494a(Frame frame, VideoExtractConfig videoExtractConfig) {
            if (VideoJoinPreview.this.f4247b != null) {
                VideoJoinPreview.this.f4247b.m1587a(frame, videoExtractConfig);
            }
        }
    };

    /* renamed from: k */
    private IAudioJoinDecoderListener f4256k = new IAudioJoinDecoderListener() { // from class: com.tencent.liteav.g.r.2
        @Override // com.tencent.liteav.p122g.IAudioJoinDecoderListener
        /* renamed from: a */
        public void mo1493a(Frame frame, VideoExtractConfig videoExtractConfig) {
            if (VideoJoinPreview.this.f4251f != null) {
                VideoJoinPreview.this.f4251f.m1921a(frame);
            }
        }
    };

    /* renamed from: l */
    private IVideoJoinRenderListener f4257l = new IVideoJoinRenderListener() { // from class: com.tencent.liteav.g.r.3
        @Override // com.tencent.liteav.p122g.IVideoJoinRenderListener
        /* renamed from: a */
        public void mo1490a(List<Surface> list) {
            TXCLog.m2913i("VideoJoinPreview", "onSurfaceTextureAvailable, mStartPlay = " + VideoJoinPreview.this.f4252g);
            VideoJoinPreview.this.f4253h = list;
            if (VideoJoinPreview.this.f4249d != null) {
                VideoJoinPreview.this.f4249d.m1526a();
                VideoJoinPreview.this.f4249d.m1520b();
                VideoJoinPreview.this.f4249d.m1522a(VideoJoinPreview.this.f4258m);
            }
            if (VideoJoinPreview.this.f4252g) {
                VideoJoinPreview.this.m1502e();
            }
        }

        @Override // com.tencent.liteav.p122g.IVideoJoinRenderListener
        /* renamed from: a */
        public void mo1492a(int i, int i2) {
            if (VideoJoinPreview.this.f4249d != null) {
                Resolution resolution = new Resolution();
                resolution.f3467a = i;
                resolution.f3468b = i2;
                VideoJoinPreview.this.f4249d.m1523a(resolution);
            }
        }

        @Override // com.tencent.liteav.p122g.IVideoJoinRenderListener
        /* renamed from: b */
        public void mo1489b(List<Surface> list) {
            TXCLog.m2913i("VideoJoinPreview", "onSurfaceTextureDestroy");
            VideoJoinPreview.this.f4253h = null;
            if (VideoJoinPreview.this.f4249d != null) {
                VideoJoinPreview.this.f4249d.m1518c();
                VideoJoinPreview.this.f4249d.m1516d();
                VideoJoinPreview.this.f4249d.m1522a((IVideoProcessorListener) null);
            }
        }

        @Override // com.tencent.liteav.p122g.IVideoJoinRenderListener
        /* renamed from: a */
        public int mo1491a(int i, float[] fArr, Frame frame) {
            if (frame.m2309p()) {
                VideoJoinPreview.this.m1500f();
                return 0;
            }
            if (VideoJoinPreview.this.f4249d != null) {
                VideoJoinPreview.this.f4249d.m1521a(fArr);
                VideoJoinPreview.this.f4249d.m1525a(i, frame);
                VideoJoinPreview.this.m1514a(frame.m2329e());
            }
            return 0;
        }
    };

    /* renamed from: m */
    private IVideoProcessorListener f4258m = new IVideoProcessorListener() { // from class: com.tencent.liteav.g.r.4
        @Override // com.tencent.liteav.p120e.IVideoProcessorListener
        /* renamed from: b */
        public int mo1487b(int i, Frame frame) {
            return i;
        }

        @Override // com.tencent.liteav.p120e.IVideoProcessorListener
        /* renamed from: a */
        public void mo1488a(int i, Frame frame) {
            if (VideoJoinPreview.this.f4247b != null) {
                VideoJoinPreview.this.f4247b.m1589a(i, VideoJoinPreview.this.f4247b.m1590a(), VideoJoinPreview.this.f4247b.m1576b());
            }
        }
    };

    /* renamed from: n */
    private IAudioPreprocessListener f4259n = new IAudioPreprocessListener() { // from class: com.tencent.liteav.g.r.5
        @Override // com.tencent.liteav.p120e.IAudioPreprocessListener
        /* renamed from: a */
        public void mo1486a(Frame frame) {
            if (frame == null || frame.m2338b() == null) {
                return;
            }
            if (frame.m2309p()) {
                VideoJoinPreview.this.m1500f();
                return;
            }
            if (VideoJoinPreview.this.f4250e != null) {
                VideoJoinPreview.this.f4250e.m2190a(frame);
            }
            if (VideoJoinPreview.this.f4251f == null) {
                return;
            }
            VideoJoinPreview.this.f4251f.m1890i();
        }
    };

    /* renamed from: o */
    private AudioTrackRender.AbstractC3436a f4260o = new AudioTrackRender.AbstractC3436a() { // from class: com.tencent.liteav.g.r.6
        @Override // com.tencent.liteav.p120e.AudioTrackRender.AbstractC3436a
        /* renamed from: a */
        public void mo1485a(int i) {
            VideoJoinPreview.this.f4248c.m1635a(i <= 5);
        }
    };

    /* renamed from: p */
    private Handler f4261p = new Handler(Looper.getMainLooper());

    /* renamed from: c */
    private VideoJoinDecAndDemuxPreview f4248c = new VideoJoinDecAndDemuxPreview();

    /* renamed from: e */
    private AudioTrackRender f4250e = new AudioTrackRender();

    public VideoJoinPreview(Context context) {
        this.f4247b = new VideoJoinGLRender(context);
        this.f4247b.m1586a(this.f4257l);
        this.f4249d = new VideoJoinPreprocessChain(context);
    }

    /* renamed from: a */
    public void m1509a(TXCVideoJoiner.AbstractC3528b abstractC3528b) {
        this.f4254i = abstractC3528b;
    }

    /* renamed from: a */
    public void m1510a(TXCVideoEditConstants.C3516f c3516f) {
        this.f4247b.m1578a(c3516f);
    }

    /* renamed from: a */
    public void m1515a() {
        this.f4252g = true;
        TXCLog.m2913i("VideoJoinPreview", "startPlay mStartPlay:" + this.f4252g);
        if (this.f4253h != null) {
            m1502e();
        }
    }

    /* renamed from: b */
    public void m1508b() {
        this.f4252g = false;
        TXCLog.m2913i("VideoJoinPreview", "stopPlay mStartPlay false");
        VideoJoinDecAndDemuxPreview videoJoinDecAndDemuxPreview = this.f4248c;
        if (videoJoinDecAndDemuxPreview != null) {
            videoJoinDecAndDemuxPreview.m1634b();
            this.f4248c.m1640a((IVideoJoinDecoderListener) null);
            this.f4248c.m1641a((IAudioJoinDecoderListener) null);
        }
        AudioPreprocessChain audioPreprocessChain = this.f4251f;
        if (audioPreprocessChain != null) {
            audioPreprocessChain.m1899d();
            this.f4251f.m1920a((IAudioPreprocessListener) null);
            this.f4251f.m1911b();
            this.f4251f = null;
        }
        AudioTrackRender audioTrackRender = this.f4250e;
        if (audioTrackRender != null) {
            audioTrackRender.m2189a((AudioTrackRender.AbstractC3436a) null);
            this.f4250e.m2183d();
        }
        VideoJoinGLRender videoJoinGLRender = this.f4247b;
        if (videoJoinGLRender != null) {
            videoJoinGLRender.m1569d();
        }
    }

    /* renamed from: c */
    public void m1506c() {
        this.f4252g = false;
        VideoJoinDecAndDemuxPreview videoJoinDecAndDemuxPreview = this.f4248c;
        if (videoJoinDecAndDemuxPreview != null) {
            videoJoinDecAndDemuxPreview.m1629c();
        }
        AudioTrackRender audioTrackRender = this.f4250e;
        if (audioTrackRender != null) {
            audioTrackRender.m2193a();
        }
    }

    /* renamed from: d */
    public void m1504d() {
        this.f4252g = true;
        VideoJoinDecAndDemuxPreview videoJoinDecAndDemuxPreview = this.f4248c;
        if (videoJoinDecAndDemuxPreview != null) {
            videoJoinDecAndDemuxPreview.m1626d();
        }
        AudioTrackRender audioTrackRender = this.f4250e;
        if (audioTrackRender != null) {
            audioTrackRender.m2186b();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: e */
    public void m1502e() {
        TXCLog.m2913i("VideoJoinPreview", "startPlayInternal");
        Resolution resolution = new Resolution();
        resolution.f3467a = this.f4247b.m1590a();
        resolution.f3468b = this.f4247b.m1576b();
        this.f4249d.m1523a(resolution);
        this.f4251f = new AudioPreprocessChain();
        this.f4251f.m1927a();
        this.f4251f.m1920a(this.f4259n);
        MediaFormat m1473g = VideoSourceListConfig.m1480a().m1473g();
        this.f4251f.m1922a(m1473g);
        this.f4250e.m2191a(m1473g);
        this.f4250e.m2189a(this.f4260o);
        this.f4250e.m2184c();
        this.f4248c.m1636a(VideoSourceListConfig.m1480a().m1477c());
        this.f4248c.m1640a(this.f4255j);
        this.f4248c.m1641a(this.f4256k);
        this.f4248c.m1644a();
        this.f4247b.m1571c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: f */
    public void m1500f() {
        this.f4261p.post(new Runnable() { // from class: com.tencent.liteav.g.r.7
            @Override // java.lang.Runnable
            public void run() {
                if (VideoJoinPreview.this.f4254i != null) {
                    VideoJoinPreview.this.f4254i.mo260a();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m1514a(final long j) {
        this.f4261p.post(new Runnable() { // from class: com.tencent.liteav.g.r.8
            @Override // java.lang.Runnable
            public void run() {
                if (VideoJoinPreview.this.f4254i != null) {
                    VideoJoinPreview.this.f4254i.mo259a((int) j);
                }
            }
        });
    }
}
