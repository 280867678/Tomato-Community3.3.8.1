package com.facebook.imagepipeline.memory;

import android.graphics.Bitmap;

/* loaded from: classes2.dex */
public class DummyBitmapPool implements BitmapPool {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.facebook.common.memory.Pool
    /* renamed from: get */
    public Bitmap mo5947get(int i) {
        return Bitmap.createBitmap(1, (int) Math.ceil(i / 2.0d), Bitmap.Config.RGB_565);
    }

    @Override // com.facebook.common.memory.Pool, com.facebook.common.references.ResourceReleaser
    public void release(Bitmap bitmap) {
        bitmap.recycle();
    }
}
