package com.tencent.liteav.p120e;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.muxer.TXCMP4Muxer;
import com.tencent.liteav.p118c.CutTimeConfig;
import com.tencent.liteav.p118c.VideoSourceConfig;
import com.tencent.liteav.p119d.Resolution;
import com.tencent.liteav.p124i.TXCVideoEditConstants;
import com.tencent.liteav.p124i.TXCVideoEditer;
import com.tencent.liteav.videoencoder.TXCVideoEncoder;

/* renamed from: com.tencent.liteav.e.y */
/* loaded from: classes3.dex */
public class VideoEditGenerate extends BasicVideoGenerate {

    /* renamed from: n */
    private TXCVideoEditer.AbstractC3524c f3772n;

    /* renamed from: o */
    private TXCVideoEditer.AbstractC3523b f3773o;

    /* renamed from: p */
    private Handler f3774p = new Handler(Looper.getMainLooper());

    public VideoEditGenerate(Context context) {
        super(context);
    }

    /* renamed from: a */
    public void m1990a(TXCVideoEditer.AbstractC3524c abstractC3524c) {
        this.f3772n = abstractC3524c;
    }

    /* renamed from: a */
    public void m1991a(TXCVideoEditer.AbstractC3523b abstractC3523b) {
        this.f3773o = abstractC3523b;
    }

    @Override // com.tencent.liteav.p120e.BasicVideoGenerate
    /* renamed from: a */
    public void mo1995a() {
        int i = 0;
        int i2 = 1;
        if (VideoSourceConfig.m2416a().m2411d() == 1) {
            m2124a(VideoSourceConfig.m2416a().f3394a);
            if (VideoSourceConfig.m2416a().m2410e() != 0) {
                if (this.f3772n == null) {
                    return;
                }
                TXCVideoEditConstants.C3513c c3513c = new TXCVideoEditConstants.C3513c();
                c3513c.f4370a = 0;
                c3513c.f4371b = "Generate Fail,Cause: Video Source Path illegal : " + VideoSourceConfig.m2416a().f3394a;
                TXCLog.m2915d("VideoEditGenerate", "onGenerateComplete");
                this.f3772n.mo263a(c3513c);
                return;
            }
        } else if (VideoSourceConfig.m2416a().m2411d() == 2) {
            m2123a(VideoSourceConfig.m2416a().m2413b(), VideoSourceConfig.m2416a().m2412c());
        }
        if (this.f3658l.m2453b()) {
            return;
        }
        this.f3658l.m2441g();
        super.mo1995a();
        Resolution resolution = this.f3658l.f3370h;
        this.f3648b = resolution.f3467a < 1280 && resolution.f3468b < 1280;
        if (this.f3648b) {
            i2 = 2;
        }
        this.f3654h = new TXCVideoEncoder(i2);
        Context context = this.f3647a;
        if (!this.f3648b) {
            i = 2;
        }
        this.f3655i = new TXCMP4Muxer(context, i);
        this.f3655i.mo1231a(this.f3658l.f3371i);
        this.f3657k.m1820a(this.f3659m);
    }

    @Override // com.tencent.liteav.p120e.BasicVideoGenerate
    /* renamed from: b */
    public void mo1989b() {
        super.mo1989b();
        this.f3657k.m1820a((TailWaterMarkListener) null);
    }

    @Override // com.tencent.liteav.p120e.BasicVideoGenerate
    /* renamed from: d */
    protected void mo1988d() {
        MoovHeaderProcessor.m2096a().m2095b();
        this.f3774p.post(new Runnable() { // from class: com.tencent.liteav.e.y.1
            @Override // java.lang.Runnable
            public void run() {
                if (VideoEditGenerate.this.f3772n != null) {
                    TXCVideoEditConstants.C3513c c3513c = new TXCVideoEditConstants.C3513c();
                    c3513c.f4370a = 0;
                    c3513c.f4371b = "Generate Complete";
                    TXCLog.m2915d("VideoEditGenerate", "===onGenerateComplete===");
                    VideoEditGenerate.this.f3772n.mo263a(c3513c);
                }
            }
        });
    }

    @Override // com.tencent.liteav.p120e.BasicVideoGenerate
    /* renamed from: a */
    protected void mo1993a(final long j) {
        this.f3774p.post(new Runnable() { // from class: com.tencent.liteav.e.y.2
            @Override // java.lang.Runnable
            public void run() {
                if (VideoEditGenerate.this.f3772n != null) {
                    long j2 = VideoEditGenerate.this.f3658l.f3373k;
                    if (j2 <= 0) {
                        return;
                    }
                    float m2494f = (((float) (j - CutTimeConfig.m2501a().m2494f())) * 1.0f) / ((float) j2);
                    TXCLog.m2915d("VideoEditGenerate", "onGenerateProgress timestamp:" + j + ",progress:" + m2494f + ",duration:" + j2);
                    VideoEditGenerate.this.f3772n.mo264a(m2494f);
                }
            }
        });
    }

    @Override // com.tencent.liteav.p120e.BasicVideoGenerate
    /* renamed from: a */
    protected int mo1994a(int i, int i2, int i3, long j) {
        TXCVideoEditer.AbstractC3523b abstractC3523b = this.f3773o;
        return abstractC3523b != null ? abstractC3523b.mo270a(i, i2, i3, j) : i;
    }

    @Override // com.tencent.liteav.p120e.BasicVideoGenerate
    /* renamed from: e */
    protected void mo1987e() {
        TXCVideoEditer.AbstractC3523b abstractC3523b = this.f3773o;
        if (abstractC3523b != null) {
            abstractC3523b.mo271a();
        }
    }
}
