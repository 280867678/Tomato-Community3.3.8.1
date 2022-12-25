package com.tomatolive.library.p136ui.view.sticker.core.file;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

/* renamed from: com.tomatolive.library.ui.view.sticker.core.file.IMGDecoder */
/* loaded from: classes3.dex */
public abstract class IMGDecoder {
    private Uri uri;

    public abstract Bitmap decode(BitmapFactory.Options options);

    public IMGDecoder(Uri uri) {
        this.uri = uri;
    }

    public Uri getUri() {
        return this.uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Bitmap decode() {
        return decode(null);
    }
}
