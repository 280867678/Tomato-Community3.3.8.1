package com.zzhoujay.richtext.exceptions;

import android.annotation.TargetApi;
import java.io.PrintStream;
import java.io.PrintWriter;

/* loaded from: classes4.dex */
public class ImageDecodeException extends Exception {
    private static final String IMAGE_DECODE_FAILURE = "Image Decode Failure";
    private OutOfMemoryError error;

    public ImageDecodeException() {
        super(IMAGE_DECODE_FAILURE);
    }

    public ImageDecodeException(Throwable th) {
        super(IMAGE_DECODE_FAILURE, th);
        if (th instanceof OutOfMemoryError) {
            this.error = (OutOfMemoryError) th;
        }
    }

    @TargetApi(24)
    public ImageDecodeException(Throwable th, boolean z, boolean z2) {
        super(IMAGE_DECODE_FAILURE, th, z, z2);
        if (th instanceof OutOfMemoryError) {
            this.error = (OutOfMemoryError) th;
        }
    }

    @Override // java.lang.Throwable
    public void printStackTrace() {
        OutOfMemoryError outOfMemoryError = this.error;
        if (outOfMemoryError != null) {
            outOfMemoryError.printStackTrace();
        } else {
            super.printStackTrace();
        }
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintStream printStream) {
        OutOfMemoryError outOfMemoryError = this.error;
        if (outOfMemoryError != null) {
            outOfMemoryError.printStackTrace(printStream);
        } else {
            super.printStackTrace(printStream);
        }
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintWriter printWriter) {
        OutOfMemoryError outOfMemoryError = this.error;
        if (outOfMemoryError != null) {
            outOfMemoryError.printStackTrace(printWriter);
        } else {
            super.printStackTrace(printWriter);
        }
    }
}
