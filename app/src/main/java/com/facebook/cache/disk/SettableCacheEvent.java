package com.facebook.cache.disk;

import com.facebook.cache.common.CacheEvent;
import com.facebook.cache.common.CacheEventListener;
import com.facebook.cache.common.CacheKey;
import java.io.IOException;

/* loaded from: classes2.dex */
public class SettableCacheEvent implements CacheEvent {
    private static final Object RECYCLER_LOCK = new Object();
    private static SettableCacheEvent sFirstRecycledEvent;
    private static int sRecycledCount;
    private SettableCacheEvent mNextRecycledEvent;

    private void reset() {
    }

    public SettableCacheEvent setCacheKey(CacheKey cacheKey) {
        return this;
    }

    public SettableCacheEvent setCacheLimit(long j) {
        return this;
    }

    public SettableCacheEvent setCacheSize(long j) {
        return this;
    }

    public SettableCacheEvent setEvictionReason(CacheEventListener.EvictionReason evictionReason) {
        return this;
    }

    public SettableCacheEvent setException(IOException iOException) {
        return this;
    }

    public SettableCacheEvent setItemSize(long j) {
        return this;
    }

    public SettableCacheEvent setResourceId(String str) {
        return this;
    }

    public static SettableCacheEvent obtain() {
        synchronized (RECYCLER_LOCK) {
            if (sFirstRecycledEvent != null) {
                SettableCacheEvent settableCacheEvent = sFirstRecycledEvent;
                sFirstRecycledEvent = settableCacheEvent.mNextRecycledEvent;
                settableCacheEvent.mNextRecycledEvent = null;
                sRecycledCount--;
                return settableCacheEvent;
            }
            return new SettableCacheEvent();
        }
    }

    private SettableCacheEvent() {
    }

    public void recycle() {
        synchronized (RECYCLER_LOCK) {
            if (sRecycledCount < 5) {
                reset();
                sRecycledCount++;
                if (sFirstRecycledEvent != null) {
                    this.mNextRecycledEvent = sFirstRecycledEvent;
                }
                sFirstRecycledEvent = this;
            }
        }
    }
}
