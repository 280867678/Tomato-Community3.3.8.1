package com.tomatolive.library.p136ui.view.sticker.core.file;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import java.io.File;

/* renamed from: com.tomatolive.library.ui.view.sticker.core.file.IMGFileDecoder */
/* loaded from: classes3.dex */
public class IMGFileDecoder extends IMGDecoder {
    public IMGFileDecoder(Uri uri) {
        super(uri);
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.file.IMGDecoder
    public Bitmap decode(BitmapFactory.Options options) {
        Uri uri = getUri();
        if (uri == null) {
            return null;
        }
        String path = uri.getPath();
        if (TextUtils.isEmpty(path) || !new File(path).exists()) {
            return null;
        }
        return BitmapFactory.decodeFile(path, options);
    }
}
