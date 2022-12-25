package com.bumptech.glide.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class LruCache<T, Y> {
    private final Map<T, Y> cache = new LinkedHashMap(100, 0.75f, true);
    private long currentSize;
    private final long initialMaxSize;
    private long maxSize;

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSize(@Nullable Y y) {
        return 1;
    }

    protected void onItemEvicted(@NonNull T t, @Nullable Y y) {
    }

    public LruCache(long j) {
        this.initialMaxSize = j;
        this.maxSize = j;
    }

    public synchronized void setSizeMultiplier(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("Multiplier must be >= 0");
        }
        this.maxSize = Math.round(((float) this.initialMaxSize) * f);
        evict();
    }

    protected synchronized int getCount() {
        return this.cache.size();
    }

    public synchronized long getMaxSize() {
        return this.maxSize;
    }

    public synchronized long getCurrentSize() {
        return this.currentSize;
    }

    public synchronized boolean contains(@NonNull T t) {
        return this.cache.containsKey(t);
    }

    @Nullable
    public synchronized Y get(@NonNull T t) {
        return this.cache.get(t);
    }

    @Nullable
    public synchronized Y put(@NonNull T t, @Nullable Y y) {
        long size = getSize(y);
        if (size >= this.maxSize) {
            onItemEvicted(t, y);
            return null;
        }
        if (y != null) {
            this.currentSize += size;
        }
        Y put = this.cache.put(t, y);
        if (put != null) {
            this.currentSize -= getSize(put);
            if (!put.equals(y)) {
                onItemEvicted(t, put);
            }
        }
        evict();
        return put;
    }

    @Nullable
    public synchronized Y remove(@NonNull T t) {
        Y remove;
        remove = this.cache.remove(t);
        if (remove != null) {
            this.currentSize -= getSize(remove);
        }
        return remove;
    }

    public void clearMemory() {
        trimToSize(0L);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public synchronized void trimToSize(long j) {
        while (this.currentSize > j) {
            Iterator<Map.Entry<T, Y>> it2 = this.cache.entrySet().iterator();
            Map.Entry<T, Y> next = it2.next();
            Y value = next.getValue();
            this.currentSize -= getSize(value);
            T key = next.getKey();
            it2.remove();
            onItemEvicted(key, value);
        }
    }

    private void evict() {
        trimToSize(this.maxSize);
    }
}
