package com.gen.p059mh.webapp_extensions.matisse.engine.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.signature.ObjectKey;
import java.io.InputStream;

/* renamed from: com.gen.mh.webapp_extensions.matisse.engine.impl.OurModelLoader */
/* loaded from: classes2.dex */
class OurModelLoader implements ModelLoader<GlideUrl, InputStream> {
    @Override // com.bumptech.glide.load.model.ModelLoader
    public boolean handles(@NonNull GlideUrl glideUrl) {
        return true;
    }

    @Override // com.bumptech.glide.load.model.ModelLoader
    @Nullable
    public ModelLoader.LoadData<InputStream> buildLoadData(@NonNull GlideUrl glideUrl, int i, int i2, @NonNull Options options) {
        return new ModelLoader.LoadData<>(new ObjectKey(glideUrl), new OurDataFetcher(glideUrl));
    }
}
