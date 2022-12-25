package com.zzhoujay.richtext.exceptions;

/* loaded from: classes4.dex */
public class BitmapCacheException extends RuntimeException {
    private static final String MESSAGE = "Bitmap缓存过程异常";

    public BitmapCacheException() {
        super(MESSAGE);
    }

    public BitmapCacheException(Throwable th) {
        super(MESSAGE, th);
    }
}
