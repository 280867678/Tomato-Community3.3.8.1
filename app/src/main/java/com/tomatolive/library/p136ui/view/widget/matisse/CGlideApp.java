package com.tomatolive.library.p136ui.view.widget.matisse;

import android.content.Context;
import android.support.annotation.NonNull;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
/* renamed from: com.tomatolive.library.ui.view.widget.matisse.CGlideApp */
/* loaded from: classes4.dex */
public class CGlideApp extends AppGlideModule {
    @Override // com.bumptech.glide.module.AppGlideModule
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override // com.bumptech.glide.module.AppGlideModule, com.bumptech.glide.module.AppliesOptions
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder glideBuilder) {
        GlideExecutor.UncaughtThrowableStrategy uncaughtThrowableStrategy = new GlideExecutor.UncaughtThrowableStrategy() { // from class: com.tomatolive.library.ui.view.widget.matisse.CGlideApp.1
            @Override // com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy
            public void handle(Throwable th) {
            }
        };
        long j = 20971520;
        glideBuilder.setMemoryCache(new LruResourceCache(j)).setDiskCache(new InternalCacheDiskCacheFactory(context, 104857600)).setBitmapPool(new LruBitmapPool(j)).setDiskCacheExecutor(GlideExecutor.newDiskCacheExecutor(uncaughtThrowableStrategy)).setSourceExecutor(GlideExecutor.newSourceExecutor(uncaughtThrowableStrategy));
    }
}
