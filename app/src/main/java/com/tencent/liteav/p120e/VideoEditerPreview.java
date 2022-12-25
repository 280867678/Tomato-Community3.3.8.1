package com.tencent.liteav.p120e;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p118c.CutTimeConfig;
import com.tencent.liteav.p118c.PicConfig;
import com.tencent.liteav.p118c.VideoOutputConfig;
import com.tencent.liteav.p118c.VideoSourceConfig;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p119d.Resolution;
import com.tencent.liteav.p120e.AudioTrackRender;
import com.tencent.liteav.p121f.AudioPreprocessChain;
import com.tencent.liteav.p121f.SpeedFilterChain;
import com.tencent.liteav.p121f.VideoPreprocessChain;
import com.tencent.liteav.p124i.TXCVideoEditConstants;
import com.tencent.liteav.p124i.TXCVideoEditer;
import com.tencent.liteav.p125j.FrameCounter;
import java.io.IOException;
import java.util.List;

/* renamed from: com.tencent.liteav.e.z */
/* loaded from: classes3.dex */
public class VideoEditerPreview {

    /* renamed from: b */
    private Context f3779b;

    /* renamed from: c */
    private VideoDecAndDemuxPreview f3780c;

    /* renamed from: d */
    private VideoGLRender f3781d;

    /* renamed from: g */
    private VideoOutputConfig f3784g;

    /* renamed from: h */
    private VideoPreprocessChain f3785h;

    /* renamed from: i */
    private AudioPreprocessChain f3786i;

    /* renamed from: j */
    private Surface f3787j;

    /* renamed from: k */
    private boolean f3788k;

    /* renamed from: l */
    private TXCVideoEditer.AbstractC3525d f3789l;

    /* renamed from: m */
    private TXCVideoEditer.AbstractC3523b f3790m;

    /* renamed from: n */
    private PicDec f3791n;

    /* renamed from: o */
    private BitmapCombineRender f3792o;

    /* renamed from: a */
    private final String f3778a = "VideoEditerPreview";

    /* renamed from: p */
    private IVideoRenderListener f3793p = new IVideoRenderListener() { // from class: com.tencent.liteav.e.z.1
        @Override // com.tencent.liteav.p120e.IVideoRenderListener
        /* renamed from: a */
        public void mo1942a(Surface surface) {
            TXCLog.m2913i("VideoEditerPreview", "onSurfaceTextureAvailable surface:" + surface + ", mStartPlay = " + VideoEditerPreview.this.f3788k);
            synchronized (this) {
                VideoEditerPreview.this.f3787j = surface;
            }
            if (VideoEditerPreview.this.f3785h != null) {
                VideoEditerPreview.this.f3785h.m1808a();
                VideoEditerPreview.this.f3785h.m1799b();
                VideoEditerPreview.this.f3785h.m1803a(VideoEditerPreview.this.f3794q);
            }
            if (VideoEditerPreview.this.f3788k) {
                VideoEditerPreview.this.m1954g();
            }
        }

        @Override // com.tencent.liteav.p120e.IVideoRenderListener
        /* renamed from: a */
        public void mo1944a(int i, int i2) {
            if (VideoEditerPreview.this.f3785h != null) {
                Resolution resolution = new Resolution();
                resolution.f3467a = i;
                resolution.f3468b = i2;
                VideoEditerPreview.this.f3785h.m1804a(resolution);
            }
        }

        @Override // com.tencent.liteav.p120e.IVideoRenderListener
        /* renamed from: b */
        public void mo1940b(Surface surface) {
            TXCLog.m2913i("VideoEditerPreview", "onSurfaceTextureDestroy surface:" + surface);
            synchronized (this) {
                if (VideoEditerPreview.this.f3787j == surface) {
                    VideoEditerPreview.this.f3787j = null;
                }
            }
            if (VideoEditerPreview.this.f3785h != null) {
                VideoEditerPreview.this.f3785h.m1795c();
                VideoEditerPreview.this.f3785h.m1792d();
                VideoEditerPreview.this.f3785h.m1803a((IVideoProcessorListener) null);
            }
            if (VideoEditerPreview.this.f3792o != null) {
                VideoEditerPreview.this.f3792o.m2105a();
            }
        }

        @Override // com.tencent.liteav.p120e.IVideoRenderListener
        /* renamed from: a */
        public int mo1943a(int i, float[] fArr, Frame frame) {
            if (frame.m2309p()) {
                VideoEditerPreview.this.m1950i();
                return 0;
            }
            if (VideoEditerPreview.this.f3792o != null) {
                i = VideoEditerPreview.this.f3792o.m2101a(frame, PicConfig.m2485a().m2483b(), false);
                frame.m2314l(i);
                frame.m2312m(0);
            }
            if (VideoEditerPreview.this.f3785h != null) {
                VideoEditerPreview.this.f3785h.m1800a(fArr);
                VideoEditerPreview.this.f3785h.m1807a(i, frame);
                VideoEditerPreview.this.m1963c(frame.m2329e());
            }
            return 0;
        }

        @Override // com.tencent.liteav.p120e.IVideoRenderListener
        /* renamed from: a */
        public void mo1941a(boolean z) {
            if (VideoEditerPreview.this.f3785h != null) {
                VideoEditerPreview.this.f3785h.m1801a(z);
            }
        }
    };

    /* renamed from: q */
    private IVideoProcessorListener f3794q = new IVideoProcessorListener() { // from class: com.tencent.liteav.e.z.2
        @Override // com.tencent.liteav.p120e.IVideoProcessorListener
        /* renamed from: a */
        public void mo1488a(int i, Frame frame) {
            FrameCounter.m1437c();
            if (VideoEditerPreview.this.f3781d != null) {
                VideoEditerPreview.this.f3781d.m2244a(i, VideoEditerPreview.this.f3781d.m2245a(), VideoEditerPreview.this.f3781d.m2231b());
            }
        }

        @Override // com.tencent.liteav.p120e.IVideoProcessorListener
        /* renamed from: b */
        public int mo1487b(int i, Frame frame) {
            return VideoEditerPreview.this.m1983a(i, frame.m2313m(), frame.m2311n(), frame.m2329e());
        }
    };

    /* renamed from: r */
    private IAudioPreprocessListener f3795r = new IAudioPreprocessListener() { // from class: com.tencent.liteav.e.z.3
        @Override // com.tencent.liteav.p120e.IAudioPreprocessListener
        /* renamed from: a */
        public void mo1486a(Frame frame) {
            if (frame == null || frame.m2338b() == null) {
                return;
            }
            FrameCounter.m1436d();
            if (!frame.m2309p() || ((VideoSourceConfig.m2416a().m2411d() != 2 || !VideoEditerPreview.this.f3791n.m2077c()) && (VideoSourceConfig.m2416a().m2411d() != 1 || !VideoEditerPreview.this.f3780c.m2005q()))) {
                if (VideoEditerPreview.this.f3782e != null) {
                    VideoEditerPreview.this.f3782e.m2190a(frame);
                }
                if (VideoEditerPreview.this.f3786i == null) {
                    return;
                }
                VideoEditerPreview.this.f3786i.m1890i();
                return;
            }
            VideoEditerPreview.this.m1950i();
        }
    };

    /* renamed from: s */
    private IVideoEditDecoderListener f3796s = new IVideoEditDecoderListener() { // from class: com.tencent.liteav.e.z.4
        @Override // com.tencent.liteav.p120e.IVideoEditDecoderListener
        /* renamed from: a */
        public void mo1939a(Frame frame) {
            FrameCounter.m1439a();
            if (VideoEditerPreview.this.f3781d != null) {
                VideoEditerPreview.this.f3781d.m2242a(frame);
            }
        }
    };

    /* renamed from: t */
    private IAudioEditDecoderListener f3797t = new IAudioEditDecoderListener() { // from class: com.tencent.liteav.e.z.5
        @Override // com.tencent.liteav.p120e.IAudioEditDecoderListener
        /* renamed from: a */
        public void mo1938a(Frame frame) {
            FrameCounter.m1438b();
            if (VideoEditerPreview.this.f3786i != null) {
                VideoEditerPreview.this.f3786i.m1921a(frame);
            }
        }
    };

    /* renamed from: u */
    private IPictureDecderListener f3798u = new IPictureDecderListener() { // from class: com.tencent.liteav.e.z.6
        @Override // com.tencent.liteav.p120e.IPictureDecderListener
        /* renamed from: a */
        public void mo1937a(Frame frame) {
            if (VideoEditerPreview.this.f3781d != null) {
                VideoEditerPreview.this.f3781d.m2230b(frame);
            }
        }
    };

    /* renamed from: v */
    private Handler f3799v = new Handler(Looper.getMainLooper());

    /* renamed from: w */
    private AudioTrackRender.AbstractC3436a f3800w = new AudioTrackRender.AbstractC3436a() { // from class: com.tencent.liteav.e.z.9
        @Override // com.tencent.liteav.p120e.AudioTrackRender.AbstractC3436a
        /* renamed from: a */
        public void mo1485a(int i) {
            boolean z = false;
            if (VideoSourceConfig.m2416a().m2411d() != 1 || !VideoEditerPreview.this.f3780c.m2164h()) {
                if (VideoEditerPreview.this.f3786i == null) {
                    return;
                }
                AudioPreprocessChain audioPreprocessChain = VideoEditerPreview.this.f3786i;
                if (i <= 5) {
                    z = true;
                }
                audioPreprocessChain.m1900c(z);
                return;
            }
            VideoDecAndDemuxPreview videoDecAndDemuxPreview = VideoEditerPreview.this.f3780c;
            if (i <= 5) {
                z = true;
            }
            videoDecAndDemuxPreview.m2036a(z);
        }
    };

    /* renamed from: e */
    private AudioTrackRender f3782e = new AudioTrackRender();

    /* renamed from: f */
    private VideoSourceConfig f3783f = VideoSourceConfig.m2416a();

    public VideoEditerPreview(Context context) {
        this.f3779b = context;
        this.f3781d = new VideoGLRender(context);
        this.f3781d.m2234a(this.f3793p);
        this.f3784g = VideoOutputConfig.m2457a();
        this.f3785h = new VideoPreprocessChain(context);
        this.f3784g = VideoOutputConfig.m2457a();
    }

    /* renamed from: a */
    public void m1973a(String str) {
        TXCLog.m2913i("VideoEditerPreview", "setVideoPath videoPath:" + str);
        if (this.f3780c == null) {
            this.f3780c = new VideoDecAndDemuxPreview();
        }
        try {
            this.f3780c.m2171a(str);
            if (!this.f3780c.m2164h()) {
                return;
            }
            this.f3784g.m2455a(this.f3780c.m2166f());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* renamed from: a */
    public void m1972a(List<Bitmap> list, int i) {
        this.f3791n = new PicDec();
        this.f3791n.m2083a(true);
        this.f3791n.m2084a(list, i);
        this.f3792o = new BitmapCombineRender(this.f3779b, this.f3791n.m2094a(), this.f3791n.m2082b());
    }

    /* renamed from: a */
    public long m1984a(int i) {
        PicConfig.m2485a().m2484a(i);
        PicDec picDec = this.f3791n;
        if (picDec != null) {
            return picDec.m2093a(i);
        }
        return 0L;
    }

    /* renamed from: b */
    public void m1965b(String str) {
        if (this.f3786i == null) {
            this.f3786i = new AudioPreprocessChain();
            this.f3786i.m1927a();
        }
        this.f3786i.m1915a(str);
        this.f3784g.m2449c(this.f3786i.m1891h());
        this.f3786i.m1922a(this.f3784g.m2431n());
        boolean z = false;
        if (VideoSourceConfig.m2416a().m2411d() == 1) {
            z = this.f3780c.m2164h();
        }
        if (!z) {
            this.f3786i.m1904b(z);
            this.f3786i.m1903c();
        }
    }

    /* renamed from: a */
    public void m1981a(long j, long j2) {
        TXCLog.m2913i("VideoEditerPreview", "setBGMStartTime startTime:" + j + ",endTime:" + j2);
        AudioPreprocessChain audioPreprocessChain = this.f3786i;
        if (audioPreprocessChain != null) {
            audioPreprocessChain.m1923a(j, j2);
        }
    }

    /* renamed from: a */
    public void m1985a(float f) {
        TXCLog.m2913i("VideoEditerPreview", "setVideoVolume volume:" + f);
        AudioPreprocessChain audioPreprocessChain = this.f3786i;
        if (audioPreprocessChain != null) {
            audioPreprocessChain.m1926a(f);
        }
    }

    /* renamed from: b */
    public void m1969b(float f) {
        TXCLog.m2913i("VideoEditerPreview", "setBGMVolume volume:" + f);
        AudioPreprocessChain audioPreprocessChain = this.f3786i;
        if (audioPreprocessChain != null) {
            audioPreprocessChain.m1910b(f);
        }
    }

    /* renamed from: a */
    public void m1971a(boolean z) {
        TXCLog.m2913i("VideoEditerPreview", "setBGMLoop looping:" + z);
        AudioPreprocessChain audioPreprocessChain = this.f3786i;
        if (audioPreprocessChain != null) {
            audioPreprocessChain.m1912a(z);
        }
    }

    /* renamed from: a */
    public void m1982a(long j) {
        TXCLog.m2913i("VideoEditerPreview", "setBGMAtVideoTime videoStartTime:" + j);
        AudioPreprocessChain audioPreprocessChain = this.f3786i;
        if (audioPreprocessChain != null) {
            audioPreprocessChain.m1924a(j);
        }
    }

    /* renamed from: a */
    public void m1986a() {
        VideoGLRender videoGLRender = this.f3781d;
        if (videoGLRender != null) {
            videoGLRender.m2232a(true);
        }
    }

    /* renamed from: b */
    public void m1967b(long j, long j2) {
        PicDec picDec;
        VideoDecAndDemuxPreview videoDecAndDemuxPreview;
        if (this.f3783f.m2411d() == 1 && (videoDecAndDemuxPreview = this.f3780c) != null) {
            videoDecAndDemuxPreview.m2040a(j * 1000, j2 * 1000);
        } else if (this.f3783f.m2411d() != 2 || (picDec = this.f3791n) == null) {
        } else {
            picDec.m2091a(j, j2);
        }
    }

    /* renamed from: c */
    public void m1962c(long j, long j2) {
        if (this.f3783f.m2411d() == 2) {
            TXCLog.m2914e("VideoEditerPreview", "setRepeateFromTimeToTime, source is picture, not support yet!");
        } else {
            this.f3780c.m2034b(j, j2);
        }
    }

    /* renamed from: a */
    public void m1974a(TXCVideoEditer.AbstractC3525d abstractC3525d) {
        this.f3789l = abstractC3525d;
    }

    /* renamed from: a */
    public void m1975a(TXCVideoEditer.AbstractC3523b abstractC3523b) {
        this.f3790m = abstractC3523b;
    }

    /* renamed from: a */
    public void m1976a(TXCVideoEditConstants.C3516f c3516f) {
        synchronized (this) {
            this.f3787j = null;
        }
        if (this.f3783f.m2411d() == 1) {
            m1973a(this.f3783f.f3394a);
            if (VideoSourceConfig.m2416a().m2410e() != 0) {
                TXCLog.m2914e("VideoEditerPreview", "initWithPreview Video Source illegal : " + this.f3783f.f3394a);
                return;
            }
        }
        VideoGLRender videoGLRender = this.f3781d;
        if (videoGLRender != null) {
            videoGLRender.m2233a(c3516f);
        }
    }

    /* renamed from: b */
    public void m1968b(long j) {
        PicDec picDec;
        VideoDecAndDemuxPreview videoDecAndDemuxPreview;
        if (this.f3783f.m2411d() == 1 && (videoDecAndDemuxPreview = this.f3780c) != null) {
            videoDecAndDemuxPreview.m2041a(j);
        } else if (this.f3783f.m2411d() != 2 || (picDec = this.f3791n) == null) {
        } else {
            picDec.m2092a(j);
        }
    }

    /* renamed from: b */
    public void m1970b() {
        this.f3788k = true;
        TXCLog.m2913i("VideoEditerPreview", "startPlay mStartPlay true,mSurface:" + this.f3787j);
        if (this.f3787j != null) {
            m1954g();
        }
    }

    /* renamed from: c */
    public void m1964c() {
        PicDec picDec;
        VideoDecAndDemuxPreview videoDecAndDemuxPreview;
        this.f3788k = false;
        TXCLog.m2913i("VideoEditerPreview", "stopPlay mStartPlay false");
        if (this.f3783f.m2411d() == 1 && (videoDecAndDemuxPreview = this.f3780c) != null) {
            videoDecAndDemuxPreview.m2172a((IVideoEditDecoderListener) null);
            this.f3780c.m2173a((IAudioEditDecoderListener) null);
            this.f3780c.m2013m();
        } else if (this.f3783f.m2411d() == 2 && (picDec = this.f3791n) != null) {
            picDec.m2072e();
            this.f3791n.m2088a((IPictureDecderListener) null);
        }
        AudioTrackRender audioTrackRender = this.f3782e;
        if (audioTrackRender != null) {
            audioTrackRender.m2189a((AudioTrackRender.AbstractC3436a) null);
            this.f3782e.m2183d();
        }
        AudioPreprocessChain audioPreprocessChain = this.f3786i;
        if (audioPreprocessChain != null) {
            audioPreprocessChain.m1899d();
            this.f3786i.m1920a((IAudioPreprocessListener) null);
            this.f3786i.m1911b();
            this.f3786i = null;
        }
        VideoGLRender videoGLRender = this.f3781d;
        if (videoGLRender != null) {
            videoGLRender.m2221d();
        }
    }

    /* renamed from: d */
    public void m1960d() {
        PicDec picDec;
        AudioPreprocessChain audioPreprocessChain;
        VideoDecAndDemuxPreview videoDecAndDemuxPreview;
        this.f3788k = true;
        VideoGLRender videoGLRender = this.f3781d;
        if (videoGLRender != null) {
            videoGLRender.m2232a(false);
        }
        synchronized (this) {
            if (this.f3787j == null) {
                TXCLog.m2913i("VideoEditerPreview", "resumePlay, mSurface is null, ignore!");
                return;
            }
            if (this.f3783f.m2411d() == 1 && (videoDecAndDemuxPreview = this.f3780c) != null) {
                videoDecAndDemuxPreview.m2009o();
            } else if (this.f3783f.m2411d() == 2 && (picDec = this.f3791n) != null) {
                picDec.m2068g();
            }
            AudioTrackRender audioTrackRender = this.f3782e;
            if (audioTrackRender != null) {
                audioTrackRender.m2186b();
            }
            VideoDecAndDemuxPreview videoDecAndDemuxPreview2 = this.f3780c;
            if (videoDecAndDemuxPreview2 == null || videoDecAndDemuxPreview2.m2164h() || (audioPreprocessChain = this.f3786i) == null) {
                return;
            }
            audioPreprocessChain.m1893g();
        }
    }

    /* renamed from: e */
    public void m1958e() {
        PicDec picDec;
        AudioPreprocessChain audioPreprocessChain;
        VideoDecAndDemuxPreview videoDecAndDemuxPreview;
        this.f3788k = false;
        if (this.f3783f.m2411d() == 1 && (videoDecAndDemuxPreview = this.f3780c) != null) {
            videoDecAndDemuxPreview.m2011n();
        } else if (this.f3783f.m2411d() == 2 && (picDec = this.f3791n) != null) {
            picDec.m2070f();
        }
        AudioTrackRender audioTrackRender = this.f3782e;
        if (audioTrackRender != null) {
            audioTrackRender.m2193a();
        }
        VideoDecAndDemuxPreview videoDecAndDemuxPreview2 = this.f3780c;
        if (videoDecAndDemuxPreview2 == null || videoDecAndDemuxPreview2.m2164h() || (audioPreprocessChain = this.f3786i) == null) {
            return;
        }
        audioPreprocessChain.m1895f();
    }

    /* renamed from: f */
    public void m1956f() {
        TXCLog.m2913i("VideoEditerPreview", "release");
        VideoDecAndDemuxPreview videoDecAndDemuxPreview = this.f3780c;
        if (videoDecAndDemuxPreview != null) {
            videoDecAndDemuxPreview.mo2017k();
        }
        PicDec picDec = this.f3791n;
        if (picDec != null) {
            picDec.m2065i();
        }
        VideoGLRender videoGLRender = this.f3781d;
        if (videoGLRender != null) {
            videoGLRender.m2234a((IVideoRenderListener) null);
            this.f3781d.m2219e();
        }
        this.f3781d = null;
        this.f3785h = null;
        this.f3793p = null;
        this.f3794q = null;
        this.f3795r = null;
        this.f3796s = null;
        this.f3797t = null;
        this.f3800w = null;
        this.f3787j = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: g */
    public void m1954g() {
        PicDec picDec;
        TXCLog.m2913i("VideoEditerPreview", "startPlayInternal");
        if (this.f3786i == null) {
            this.f3786i = new AudioPreprocessChain();
            this.f3786i.m1927a();
        }
        this.f3786i.m1920a(this.f3795r);
        this.f3786i.m1908b(m1952h());
        if (this.f3784g.m2433l()) {
            MediaFormat m2431n = this.f3784g.m2431n();
            this.f3786i.m1922a(m2431n);
            if (this.f3783f.m2411d() == 1) {
                this.f3786i.m1904b(this.f3780c.m2164h());
            } else {
                this.f3786i.m1904b(false);
            }
            this.f3786i.m1903c();
            this.f3786i.m1897e();
            this.f3782e.m2191a(m2431n);
        }
        Resolution resolution = new Resolution();
        VideoGLRender videoGLRender = this.f3781d;
        if (videoGLRender != null) {
            resolution.f3467a = videoGLRender.m2245a();
            resolution.f3468b = this.f3781d.m2231b();
        }
        this.f3785h.m1804a(resolution);
        if (this.f3783f.m2411d() == 1 && this.f3780c != null) {
            synchronized (this) {
                this.f3780c.m2175a(this.f3787j);
            }
            this.f3780c.m2172a(this.f3796s);
            this.f3780c.m2173a(this.f3797t);
            this.f3780c.m2015l();
        } else if (this.f3783f.m2411d() == 2 && (picDec = this.f3791n) != null) {
            picDec.m2088a(this.f3798u);
            this.f3791n.m2074d();
        }
        this.f3782e.m2189a(this.f3800w);
        this.f3782e.m2184c();
        VideoGLRender videoGLRender2 = this.f3781d;
        if (videoGLRender2 != null) {
            videoGLRender2.m2232a(false);
            this.f3781d.m2224c();
        }
        FrameCounter.m1432h();
    }

    /* renamed from: h */
    private long m1952h() {
        CutTimeConfig m2501a = CutTimeConfig.m2501a();
        long m2495e = m2501a.m2495e() - m2501a.m2496d();
        TXCLog.m2915d("VideoEditerPreview", "calculatePlayDuration playDurationUs : " + m2495e);
        if (SpeedFilterChain.m1849a().m1843c()) {
            long m1844b = SpeedFilterChain.m1849a().m1844b(m2495e);
            TXCLog.m2915d("VideoEditerPreview", "calculatePlayDuration after Speed playDurationUs : " + m1844b);
            return m1844b;
        }
        return m2495e;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public int m1983a(int i, int i2, int i3, long j) {
        TXCVideoEditer.AbstractC3523b abstractC3523b = this.f3790m;
        return abstractC3523b != null ? abstractC3523b.mo270a(i, i2, i3, j) : i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: i */
    public void m1950i() {
        this.f3799v.post(new Runnable() { // from class: com.tencent.liteav.e.z.7
            @Override // java.lang.Runnable
            public void run() {
                if (VideoEditerPreview.this.f3789l != null) {
                    VideoEditerPreview.this.f3789l.mo266a();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: c */
    public void m1963c(final long j) {
        this.f3799v.post(new Runnable() { // from class: com.tencent.liteav.e.z.8
            @Override // java.lang.Runnable
            public void run() {
                if (VideoEditerPreview.this.f3789l != null) {
                    VideoEditerPreview.this.f3789l.mo265a((int) j);
                }
            }
        });
    }
}
