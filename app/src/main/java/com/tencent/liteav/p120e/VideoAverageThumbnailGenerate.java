package com.tencent.liteav.p120e;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p118c.ThumbnailConfig;
import com.tencent.liteav.p118c.VideoOutputConfig;
import com.tencent.liteav.p118c.VideoSourceConfig;
import com.tencent.liteav.p121f.VideoPreprocessChain;
import com.tencent.liteav.p124i.TXCVideoEditConstants;
import com.tencent.liteav.p124i.TXCVideoEditer;

/* renamed from: com.tencent.liteav.e.u */
/* loaded from: classes3.dex */
public class VideoAverageThumbnailGenerate extends BasicVideoGenerate {

    /* renamed from: n */
    private TXCVideoEditer.AbstractC3526e f3727n;

    /* renamed from: o */
    private TXCVideoEditer.AbstractC3522a f3728o;

    /* renamed from: p */
    private Handler f3729p = new Handler(Looper.getMainLooper());

    /* renamed from: q */
    private TXIThumbnailListener f3730q = new TXIThumbnailListener() { // from class: com.tencent.liteav.e.u.1
        @Override // com.tencent.liteav.p120e.TXIThumbnailListener
        /* renamed from: a */
        public void mo2046a(int i, long j, Bitmap bitmap) {
            VideoAverageThumbnailGenerate.this.f3649c.mo2042p();
            if (VideoAverageThumbnailGenerate.this.f3728o != null) {
                VideoAverageThumbnailGenerate.this.f3728o.mo267a(i, j / 1000, bitmap);
            }
            int m2466c = ThumbnailConfig.m2474a().m2466c();
            if (m2466c == 0) {
                VideoAverageThumbnailGenerate.this.mo1989b();
                if (VideoAverageThumbnailGenerate.this.f3727n == null) {
                    return;
                }
                VideoAverageThumbnailGenerate.this.m2047g();
                return;
            }
            final float f = ((i + 1) * 1.0f) / m2466c;
            TXCLog.m2913i("VideoAverageThumbnailGenerate", "index:" + i + ",count= " + m2466c + ",progress:" + f);
            VideoAverageThumbnailGenerate.this.f3729p.post(new Runnable() { // from class: com.tencent.liteav.e.u.1.1
                @Override // java.lang.Runnable
                public void run() {
                    if (f >= 1.0f) {
                        VideoAverageThumbnailGenerate.this.mo1989b();
                    }
                    if (VideoAverageThumbnailGenerate.this.f3727n != null) {
                        VideoAverageThumbnailGenerate.this.f3727n.mo269a(f);
                        if (f < 1.0f) {
                            return;
                        }
                        VideoAverageThumbnailGenerate.this.m2047g();
                        TXCLog.m2913i("VideoAverageThumbnailGenerate", "===onProcessComplete===");
                    }
                }
            });
        }
    };

    @Override // com.tencent.liteav.p120e.BasicVideoGenerate
    /* renamed from: a */
    protected int mo1994a(int i, int i2, int i3, long j) {
        return i;
    }

    @Override // com.tencent.liteav.p120e.BasicVideoGenerate
    /* renamed from: a */
    protected void mo1993a(long j) {
    }

    @Override // com.tencent.liteav.p120e.BasicVideoGenerate
    /* renamed from: d */
    protected void mo1988d() {
    }

    @Override // com.tencent.liteav.p120e.BasicVideoGenerate
    /* renamed from: e */
    protected void mo1987e() {
    }

    public VideoAverageThumbnailGenerate(Context context) {
        super(context);
        this.f3649c = new VideoThumbnailGenerate();
        this.f3652f.m1802a(this.f3730q);
    }

    /* renamed from: a */
    public void m2054a(TXCVideoEditer.AbstractC3526e abstractC3526e) {
        this.f3727n = abstractC3526e;
    }

    /* renamed from: a */
    public void m2055a(TXCVideoEditer.AbstractC3522a abstractC3522a) {
        this.f3728o = abstractC3522a;
    }

    @Override // com.tencent.liteav.p120e.BasicVideoGenerate
    /* renamed from: a */
    public void mo1995a() {
        m2124a(VideoSourceConfig.m2416a().f3394a);
        m2048f();
        super.mo1995a();
    }

    /* renamed from: f */
    protected void m2048f() {
        ThumbnailConfig.m2474a().m2472a(this.f3649c.m2169c());
        this.f3649c.m2156a(ThumbnailConfig.m2474a().m2468b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: g */
    public void m2047g() {
        VideoOutputConfig.m2457a().f3376n = true;
        TXCVideoEditConstants.C3513c c3513c = new TXCVideoEditConstants.C3513c();
        c3513c.f4370a = 0;
        c3513c.f4371b = "Generate Complete";
        this.f3727n.mo268a(c3513c);
    }

    @Override // com.tencent.liteav.p120e.BasicVideoGenerate
    /* renamed from: c */
    public void mo2051c() {
        VideoPreprocessChain videoPreprocessChain = this.f3652f;
        if (videoPreprocessChain != null) {
            videoPreprocessChain.m1802a((TXIThumbnailListener) null);
        }
        this.f3730q = null;
        super.mo2051c();
    }

    /* renamed from: b */
    public void m2052b(boolean z) {
        BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater = this.f3649c;
        if (basicVideoDecDemuxGenerater != null) {
            basicVideoDecDemuxGenerater.mo2045a(z);
        }
    }
}
