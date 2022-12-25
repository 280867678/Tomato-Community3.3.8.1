package com.zzhoujay.richtext.exceptions;

import android.annotation.TargetApi;

/* loaded from: classes4.dex */
public class ResetImageSourceException extends RuntimeException {
    private static final String MESSAGE = "ImageHolder的source只能在INIT阶段修改";

    public ResetImageSourceException() {
        super(MESSAGE);
    }

    public ResetImageSourceException(Throwable th) {
        super(MESSAGE, th);
    }

    @TargetApi(24)
    public ResetImageSourceException(Throwable th, boolean z, boolean z2) {
        super(MESSAGE, th, z, z2);
    }
}
