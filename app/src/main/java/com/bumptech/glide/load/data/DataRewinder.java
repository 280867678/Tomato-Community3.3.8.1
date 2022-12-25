package com.bumptech.glide.load.data;

import android.support.annotation.NonNull;
import java.io.IOException;

/* loaded from: classes2.dex */
public interface DataRewinder<T> {

    /* loaded from: classes2.dex */
    public interface Factory<T> {
        @NonNull
        DataRewinder<T> build(@NonNull T t);

        @NonNull
        Class<T> getDataClass();
    }

    void cleanup();

    @NonNull
    /* renamed from: rewindAndGet */
    T mo5900rewindAndGet() throws IOException;
}