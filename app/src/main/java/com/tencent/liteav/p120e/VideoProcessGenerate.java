package com.tencent.liteav.p120e;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.muxer.TXCMP4Muxer;
import com.tencent.liteav.p118c.CutTimeConfig;
import com.tencent.liteav.p118c.ThumbnailConfig;
import com.tencent.liteav.p118c.VideoOutputConfig;
import com.tencent.liteav.p118c.VideoPreProcessConfig;
import com.tencent.liteav.p118c.VideoSourceConfig;
import com.tencent.liteav.p119d.Resolution;
import com.tencent.liteav.p124i.TXCVideoEditConstants;
import com.tencent.liteav.p124i.TXCVideoEditer;
import com.tencent.liteav.videoencoder.TXCVideoEncoder;

/* renamed from: com.tencent.liteav.e.ad */
/* loaded from: classes3.dex */
public class VideoProcessGenerate extends BasicVideoGenerate {

    /* renamed from: n */
    private TXCVideoEditer.AbstractC3526e f3561n;

    /* renamed from: o */
    private TXCVideoEditer.AbstractC3522a f3562o;

    /* renamed from: p */
    private Handler f3563p = new Handler(Looper.getMainLooper());

    /* renamed from: q */
    private TXIThumbnailListener f3564q = new TXIThumbnailListener() { // from class: com.tencent.liteav.e.ad.3
        @Override // com.tencent.liteav.p120e.TXIThumbnailListener
        /* renamed from: a */
        public void mo2046a(int i, long j, Bitmap bitmap) {
            if (VideoProcessGenerate.this.f3562o != null) {
                VideoProcessGenerate.this.f3562o.mo267a(i, j / 1000, bitmap);
            }
            if (VideoOutputConfig.m2457a().f3380r) {
                int m2466c = ThumbnailConfig.m2474a().m2466c();
                if (m2466c == 0) {
                    VideoProcessGenerate.this.mo1989b();
                    if (VideoProcessGenerate.this.f3561n == null) {
                        return;
                    }
                    VideoProcessGenerate.this.m2201g();
                    return;
                }
                final float f = ((i + 1) * 1.0f) / m2466c;
                TXCLog.m2913i("VideoProcessGenerate", "index:" + i + ",count= " + m2466c + ",progress:" + f);
                VideoProcessGenerate.this.f3563p.post(new Runnable() { // from class: com.tencent.liteav.e.ad.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (VideoProcessGenerate.this.f3561n != null) {
                            VideoProcessGenerate.this.f3561n.mo269a(f);
                            if (f < 1.0f) {
                                return;
                            }
                            VideoProcessGenerate.this.m2201g();
                            VideoProcessGenerate.this.mo1989b();
                        }
                    }
                });
            }
        }
    };

    @Override // com.tencent.liteav.p120e.BasicVideoGenerate
    /* renamed from: a */
    protected int mo1994a(int i, int i2, int i3, long j) {
        return i;
    }

    @Override // com.tencent.liteav.p120e.BasicVideoGenerate
    /* renamed from: e */
    protected void mo1987e() {
    }

    public VideoProcessGenerate(Context context) {
        super(context);
        this.f3649c = new VideoDecAndDemuxGenerate();
        this.f3652f.m1802a(this.f3564q);
    }

    /* renamed from: a */
    public void m2206a(TXCVideoEditer.AbstractC3526e abstractC3526e) {
        this.f3561n = abstractC3526e;
    }

    /* renamed from: a */
    public void m2207a(TXCVideoEditer.AbstractC3522a abstractC3522a) {
        this.f3562o = abstractC3522a;
    }

    @Override // com.tencent.liteav.p120e.BasicVideoGenerate
    /* renamed from: a */
    public void mo1995a() {
        m2124a(VideoSourceConfig.m2416a().f3394a);
        m2202f();
        super.mo1995a();
        Resolution resolution = this.f3658l.f3370h;
        int i = 1;
        int i2 = 0;
        this.f3648b = resolution.f3467a < 1280 && resolution.f3468b < 1280;
        if (this.f3648b) {
            i = 2;
        }
        this.f3654h = new TXCVideoEncoder(i);
        Context context = this.f3647a;
        if (!this.f3648b) {
            i2 = 2;
        }
        this.f3655i = new TXCMP4Muxer(context, i2);
        VideoOutputConfig videoOutputConfig = this.f3658l;
        if (!videoOutputConfig.f3380r) {
            videoOutputConfig.m2443f();
            this.f3655i.mo1231a(this.f3658l.f3377o);
        }
    }

    @Override // com.tencent.liteav.p120e.BasicVideoGenerate
    /* renamed from: d */
    protected void mo1988d() {
        VideoSourceConfig.m2416a().f3394a = VideoOutputConfig.m2457a().f3377o;
        VideoPreProcessConfig.m2427a().m2426a(0);
        this.f3563p.post(new Runnable() { // from class: com.tencent.liteav.e.ad.1
            @Override // java.lang.Runnable
            public void run() {
                if (VideoProcessGenerate.this.f3561n != null) {
                    TXCVideoEditConstants.C3513c c3513c = new TXCVideoEditConstants.C3513c();
                    c3513c.f4370a = 0;
                    c3513c.f4371b = "Generate Complete";
                    VideoProcessGenerate.this.f3561n.mo268a(c3513c);
                }
            }
        });
    }

    @Override // com.tencent.liteav.p120e.BasicVideoGenerate
    /* renamed from: a */
    protected void mo1993a(final long j) {
        this.f3563p.post(new Runnable() { // from class: com.tencent.liteav.e.ad.2
            @Override // java.lang.Runnable
            public void run() {
                if (VideoProcessGenerate.this.f3561n != null) {
                    long j2 = VideoProcessGenerate.this.f3658l.f3373k;
                    if (j2 <= 0) {
                        return;
                    }
                    VideoProcessGenerate.this.f3561n.mo269a((((float) (j - CutTimeConfig.m2501a().m2494f())) * 1.0f) / ((float) j2));
                }
            }
        });
    }

    /* renamed from: f */
    protected void m2202f() {
        long m2493g = CutTimeConfig.m2501a().m2493g() - CutTimeConfig.m2501a().m2494f();
        if (m2493g <= 0) {
            m2493g = this.f3649c.m2169c();
        }
        ThumbnailConfig.m2474a().m2472a(m2493g);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: g */
    public void m2201g() {
        TXCVideoEditConstants.C3513c c3513c = new TXCVideoEditConstants.C3513c();
        c3513c.f4370a = 0;
        c3513c.f4371b = "Generate Complete";
        this.f3561n.mo268a(c3513c);
        TXCLog.m2913i("VideoProcessGenerate", "===onProcessComplete===");
    }

    @Override // com.tencent.liteav.p120e.BasicVideoGenerate
    /* renamed from: c */
    public void mo2051c() {
        super.mo2051c();
        this.f3564q = null;
    }
}
