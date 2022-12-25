package com.davemorrissey.labs.subscaleview.decoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

/* loaded from: classes2.dex */
public interface ImageDecoder {
    @NonNull
    Bitmap decode(Context context, @NonNull Uri uri) throws Exception;
}
