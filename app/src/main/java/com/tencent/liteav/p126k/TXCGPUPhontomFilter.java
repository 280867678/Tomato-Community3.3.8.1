package com.tencent.liteav.p126k;

import com.tencent.liteav.beauty.p115b.TXCZoomInOutFilter;
import com.tencent.liteav.p126k.TXCVideoEffect;

/* renamed from: com.tencent.liteav.k.i */
/* loaded from: classes3.dex */
public class TXCGPUPhontomFilter extends TXCGPUSpiritOutFilter {
    @Override // com.tencent.liteav.p126k.TXCGPUSpiritOutFilter
    /* renamed from: a */
    public int mo1330a(int i) {
        TXCVideoEffect.C3545l c3545l = this.f4532b;
        if (c3545l == null) {
            return i;
        }
        TXCVideoEffect.C3544k c3544k = (TXCVideoEffect.C3544k) c3545l;
        TXCZoomInOutFilter tXCZoomInOutFilter = this.f4531a;
        if (tXCZoomInOutFilter != null) {
            tXCZoomInOutFilter.m2721a(c3544k.f4617a, c3544k.f4618b, c3544k.f4619c);
        }
        return super.mo1330a(i);
    }
}
