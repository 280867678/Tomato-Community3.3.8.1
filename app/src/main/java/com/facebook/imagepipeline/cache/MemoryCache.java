package com.facebook.imagepipeline.cache;

import com.facebook.common.internal.Predicate;
import com.facebook.common.references.CloseableReference;

/* loaded from: classes2.dex */
public interface MemoryCache<K, V> {
    CloseableReference<V> cache(K k, CloseableReference<V> closeableReference);

    boolean contains(Predicate<K> predicate);

    CloseableReference<V> get(K k);
}
