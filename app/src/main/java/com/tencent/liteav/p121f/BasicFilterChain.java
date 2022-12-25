package com.tencent.liteav.p121f;

import com.tencent.liteav.p118c.VideoOutputConfig;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p119d.Resolution;
import com.tencent.liteav.p124i.TXCVideoEditConstants;

/* renamed from: com.tencent.liteav.f.c */
/* loaded from: classes3.dex */
public class BasicFilterChain {

    /* renamed from: a */
    protected int f3842a;

    /* renamed from: b */
    protected int f3843b;

    /* renamed from: c */
    protected Frame f3844c;

    /* renamed from: a */
    public void m1884a(Resolution resolution) {
        this.f3842a = resolution.f3467a;
        this.f3843b = resolution.f3468b;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: b */
    public Resolution m1882b(Frame frame) {
        Resolution resolution = new Resolution();
        float m2313m = (this.f3842a * 1.0f) / frame.m2313m();
        float m2311n = (this.f3843b * 1.0f) / frame.m2311n();
        if (VideoOutputConfig.m2457a().f3381s != 2 ? m2313m < m2311n : m2313m > m2311n) {
            m2313m = m2311n;
        }
        resolution.f3467a = (int) (frame.m2313m() * m2313m);
        resolution.f3468b = (int) (frame.m2311n() * m2313m);
        return resolution;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public TXCVideoEditConstants.C3517g m1883a(TXCVideoEditConstants.C3517g c3517g, Resolution resolution) {
        TXCVideoEditConstants.C3517g c3517g2 = new TXCVideoEditConstants.C3517g();
        float f = c3517g.f4380a;
        int i = this.f3842a;
        int i2 = resolution.f3467a;
        c3517g2.f4380a = (f - ((i - i2) / 2)) / i2;
        float f2 = c3517g.f4381b;
        int i3 = this.f3843b;
        int i4 = resolution.f3468b;
        c3517g2.f4381b = (f2 - ((i3 - i4) / 2)) / i4;
        c3517g2.f4382c = c3517g.f4382c / i2;
        return c3517g2;
    }

    /* renamed from: c */
    public void m1881c(Frame frame) {
        this.f3844c = frame;
    }
}
