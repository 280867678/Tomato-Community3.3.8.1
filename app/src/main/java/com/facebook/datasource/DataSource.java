package com.facebook.datasource;

import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public interface DataSource<T> {
    boolean close();

    Throwable getFailureCause();

    float getProgress();

    /* renamed from: getResult */
    T mo5940getResult();

    boolean hasMultipleResults();

    boolean hasResult();

    boolean isFinished();

    void subscribe(DataSubscriber<T> dataSubscriber, Executor executor);
}
