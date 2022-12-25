package com.tencent.liteav.p119d;

import android.graphics.Bitmap;
import com.tencent.liteav.p124i.TXCVideoEditConstants;

/* renamed from: com.tencent.liteav.d.j */
/* loaded from: classes3.dex */
public class WaterMark {

    /* renamed from: a */
    private Bitmap f3473a;

    /* renamed from: b */
    private TXCVideoEditConstants.C3517g f3474b;

    public WaterMark(Bitmap bitmap, TXCVideoEditConstants.C3517g c3517g) {
        this.f3473a = bitmap;
        this.f3474b = c3517g;
    }

    /* renamed from: c */
    public Bitmap m2296c() {
        return this.f3473a;
    }

    /* renamed from: d */
    public TXCVideoEditConstants.C3517g m2295d() {
        return this.f3474b;
    }

    /* renamed from: b */
    public void mo2297b() {
        Bitmap bitmap = this.f3473a;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.f3473a.recycle();
            this.f3473a = null;
        }
        this.f3474b = null;
    }
}
