package com.zzhoujay.richtext.exceptions;

/* loaded from: classes4.dex */
public class ImageWrapperMultiSourceException extends IllegalArgumentException {
    private static final String MESSAGE = "GifDrawable和Bitmap有且只有一个为null";

    public ImageWrapperMultiSourceException() {
        super(MESSAGE);
    }

    public ImageWrapperMultiSourceException(Throwable th) {
        super(MESSAGE, th);
    }
}
