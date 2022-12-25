package com.facebook.imagepipeline.memory;

import android.support.annotation.Nullable;

/* loaded from: classes2.dex */
interface PoolBackend<T> {
    @Nullable
    /* renamed from: get */
    T mo5941get(int i);

    int getSize(T t);

    @Nullable
    T pop();

    void put(T t);
}
