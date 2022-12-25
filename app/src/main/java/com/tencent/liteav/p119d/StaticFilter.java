package com.tencent.liteav.p119d;

import android.graphics.Bitmap;

/* renamed from: com.tencent.liteav.d.h */
/* loaded from: classes3.dex */
public class StaticFilter {

    /* renamed from: a */
    private Bitmap f3470a;

    /* renamed from: a */
    public void m2299a() {
        Bitmap bitmap = this.f3470a;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        this.f3470a.recycle();
        this.f3470a = null;
    }
}
