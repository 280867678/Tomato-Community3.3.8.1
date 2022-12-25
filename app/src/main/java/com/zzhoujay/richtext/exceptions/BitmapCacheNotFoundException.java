package com.zzhoujay.richtext.exceptions;

import android.annotation.TargetApi;

/* loaded from: classes4.dex */
public class BitmapCacheNotFoundException extends Exception {
    private static final String MESSAGE = "Bitmap 缓存不存在";

    public BitmapCacheNotFoundException() {
        super(MESSAGE);
    }

    public BitmapCacheNotFoundException(Throwable th) {
        super(MESSAGE, th);
    }

    @TargetApi(24)
    public BitmapCacheNotFoundException(Throwable th, boolean z, boolean z2) {
        super(MESSAGE, th, z, z2);
    }
}
