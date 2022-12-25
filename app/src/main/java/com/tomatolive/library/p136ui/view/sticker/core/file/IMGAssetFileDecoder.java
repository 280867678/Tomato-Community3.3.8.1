package com.tomatolive.library.p136ui.view.sticker.core.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import java.io.IOException;

/* renamed from: com.tomatolive.library.ui.view.sticker.core.file.IMGAssetFileDecoder */
/* loaded from: classes3.dex */
public class IMGAssetFileDecoder extends IMGDecoder {
    private Context mContext;

    public IMGAssetFileDecoder(Context context, Uri uri) {
        super(uri);
        this.mContext = context;
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.file.IMGDecoder
    public Bitmap decode(BitmapFactory.Options options) {
        Uri uri = getUri();
        if (uri == null) {
            return null;
        }
        String path = uri.getPath();
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        try {
            return BitmapFactory.decodeStream(this.mContext.getAssets().open(path.substring(1)), null, options);
        } catch (IOException unused) {
            return null;
        }
    }
}
