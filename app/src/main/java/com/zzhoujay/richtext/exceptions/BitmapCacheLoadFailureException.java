package com.zzhoujay.richtext.exceptions;

import android.annotation.TargetApi;

/* loaded from: classes4.dex */
public class BitmapCacheLoadFailureException extends Exception {
    private static final String MESSAGE = "Bitmap 缓存加载失败";

    public BitmapCacheLoadFailureException() {
        super(MESSAGE);
    }

    public BitmapCacheLoadFailureException(Throwable th) {
        super(MESSAGE, th);
    }

    @TargetApi(24)
    public BitmapCacheLoadFailureException(Throwable th, boolean z, boolean z2) {
        super(MESSAGE, th, z, z2);
    }
}
