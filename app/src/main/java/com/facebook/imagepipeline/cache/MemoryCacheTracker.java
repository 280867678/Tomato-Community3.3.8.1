package com.facebook.imagepipeline.cache;

/* loaded from: classes2.dex */
public interface MemoryCacheTracker<K> {
    void onCacheHit(K k);

    void onCacheMiss();

    void onCachePut();
}
