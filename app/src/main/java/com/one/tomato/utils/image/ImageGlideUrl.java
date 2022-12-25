package com.one.tomato.utils.image;

import android.text.TextUtils;
import com.bumptech.glide.load.model.GlideUrl;

/* loaded from: classes3.dex */
public class ImageGlideUrl extends GlideUrl {
    private String path;

    public ImageGlideUrl(String str, String str2) {
        super(str + str2);
        this.path = str2;
    }

    @Override // com.bumptech.glide.load.model.GlideUrl
    public String getCacheKey() {
        if (TextUtils.isEmpty(this.path)) {
            return System.currentTimeMillis() + "";
        }
        return this.path;
    }
}
