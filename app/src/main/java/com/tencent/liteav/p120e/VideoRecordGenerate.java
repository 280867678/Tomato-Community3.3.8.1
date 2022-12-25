package com.tencent.liteav.p120e;

import android.content.Context;
import com.tencent.liteav.p118c.ThumbnailConfig;

/* renamed from: com.tencent.liteav.e.ae */
/* loaded from: classes3.dex */
public class VideoRecordGenerate extends VideoProcessGenerate {
    public VideoRecordGenerate(Context context) {
        super(context);
        this.f3649c = new VideoDecAndDemuxGenerateGivenTimes();
    }

    @Override // com.tencent.liteav.p120e.VideoProcessGenerate, com.tencent.liteav.p120e.BasicVideoGenerate
    /* renamed from: a */
    public void mo1995a() {
        super.mo1995a();
        if (ThumbnailConfig.m2474a().m2464e()) {
            return;
        }
        this.f3649c.m2156a(ThumbnailConfig.m2474a().m2468b());
    }
}
