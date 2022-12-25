package com.tencent.liteav.p120e;

import android.content.Context;
import android.graphics.Bitmap;
import com.tencent.liteav.p118c.ThumbnailConfig;
import com.tencent.liteav.p118c.VideoSourceConfig;
import com.tencent.liteav.p124i.TXCVideoEditer;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tencent.liteav.e.ag */
/* loaded from: classes3.dex */
public class VideoTimelistThumbnailGenerate extends BasicVideoGenerate {

    /* renamed from: n */
    private TXCVideoEditer.AbstractC3522a f3573n;

    /* renamed from: p */
    private TXIThumbnailListener f3575p = new TXIThumbnailListener() { // from class: com.tencent.liteav.e.ag.1
        @Override // com.tencent.liteav.p120e.TXIThumbnailListener
        /* renamed from: a */
        public void mo2046a(int i, long j, Bitmap bitmap) {
            if (VideoTimelistThumbnailGenerate.this.f3573n != null) {
                VideoTimelistThumbnailGenerate.this.f3573n.mo267a(i, j / 1000, bitmap);
            }
        }
    };

    /* renamed from: o */
    private ArrayList<Long> f3574o = new ArrayList<>();

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

    public VideoTimelistThumbnailGenerate(Context context) {
        super(context);
        this.f3649c = new VideoThumbnailGenerate();
        this.f3652f.m1802a(this.f3575p);
    }

    /* renamed from: a */
    public void m2198a(TXCVideoEditer.AbstractC3522a abstractC3522a) {
        this.f3573n = abstractC3522a;
    }

    @Override // com.tencent.liteav.p120e.BasicVideoGenerate
    /* renamed from: a */
    public void mo1995a() {
        m2124a(VideoSourceConfig.m2416a().f3394a);
        m2194f();
        super.mo1995a();
    }

    /* renamed from: f */
    protected void m2194f() {
        ThumbnailConfig.m2474a().m2470a(this.f3574o);
        this.f3649c.m2156a(ThumbnailConfig.m2474a().m2468b());
    }

    /* renamed from: a */
    public void m2200a(int i) {
        ThumbnailConfig.m2474a().m2473a(i);
    }

    /* renamed from: b */
    public void m2196b(int i) {
        ThumbnailConfig.m2474a().m2467b(i);
    }

    /* renamed from: a */
    public void m2197a(List<Long> list) {
        this.f3574o.clear();
        for (int i = 0; i < list.size(); i++) {
            this.f3574o.add(Long.valueOf(list.get(i).longValue() * 1000));
        }
    }

    @Override // com.tencent.liteav.p120e.BasicVideoGenerate
    /* renamed from: c */
    public void mo2051c() {
        super.mo2051c();
        this.f3574o.clear();
        this.f3575p = null;
    }

    /* renamed from: b */
    public void m2195b(boolean z) {
        BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater = this.f3649c;
        if (basicVideoDecDemuxGenerater != null) {
            basicVideoDecDemuxGenerater.mo2045a(z);
        }
    }
}
