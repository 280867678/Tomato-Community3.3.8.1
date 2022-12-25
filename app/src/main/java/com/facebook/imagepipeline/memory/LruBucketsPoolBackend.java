package com.facebook.imagepipeline.memory;

import android.support.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes2.dex */
public abstract class LruBucketsPoolBackend<T> implements PoolBackend<T> {
    private final Set<T> mCurrentItems = new HashSet();
    private final BucketMap<T> mMap = new BucketMap<>();

    @Override // com.facebook.imagepipeline.memory.PoolBackend
    @Nullable
    /* renamed from: get */
    public T mo5941get(int i) {
        T acquire = this.mMap.acquire(i);
        maybeRemoveFromCurrentItems(acquire);
        return acquire;
    }

    @Override // com.facebook.imagepipeline.memory.PoolBackend
    public void put(T t) {
        boolean add;
        synchronized (this) {
            add = this.mCurrentItems.add(t);
        }
        if (add) {
            this.mMap.release(getSize(t), t);
        }
    }

    @Override // com.facebook.imagepipeline.memory.PoolBackend
    @Nullable
    public T pop() {
        T removeFromEnd = this.mMap.removeFromEnd();
        maybeRemoveFromCurrentItems(removeFromEnd);
        return removeFromEnd;
    }

    private T maybeRemoveFromCurrentItems(@Nullable T t) {
        if (t != null) {
            synchronized (this) {
                this.mCurrentItems.remove(t);
            }
        }
        return t;
    }
}
