package com.gen.p059mh.webapp_extensions.matisse.engine.impl;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import java.io.InputStream;

/* renamed from: com.gen.mh.webapp_extensions.matisse.engine.impl.OurLoadFactory */
/* loaded from: classes2.dex */
class OurLoadFactory implements ModelLoaderFactory<GlideUrl, InputStream> {
    @Override // com.bumptech.glide.load.model.ModelLoaderFactory
    public void teardown() {
    }

    @Override // com.bumptech.glide.load.model.ModelLoaderFactory
    @NonNull
    public ModelLoader<GlideUrl, InputStream> build(@NonNull MultiModelLoaderFactory multiModelLoaderFactory) {
        return new OurModelLoader();
    }
}
