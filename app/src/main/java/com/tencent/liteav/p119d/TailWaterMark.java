package com.tencent.liteav.p119d;

import android.graphics.Bitmap;
import com.tencent.liteav.p124i.TXCVideoEditConstants;

/* renamed from: com.tencent.liteav.d.i */
/* loaded from: classes3.dex */
public class TailWaterMark extends WaterMark {

    /* renamed from: a */
    private final int f3471a = 3000;

    /* renamed from: b */
    private int f3472b;

    public TailWaterMark(Bitmap bitmap, TXCVideoEditConstants.C3517g c3517g, int i) {
        super(bitmap, c3517g);
        this.f3472b = i;
    }

    /* renamed from: a */
    public int m2298a() {
        return this.f3472b;
    }

    @Override // com.tencent.liteav.p119d.WaterMark
    /* renamed from: b */
    public void mo2297b() {
        super.mo2297b();
        this.f3472b = 0;
    }
}
