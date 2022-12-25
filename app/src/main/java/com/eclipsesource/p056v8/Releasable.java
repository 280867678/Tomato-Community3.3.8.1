package com.eclipsesource.p056v8;

import java.io.Closeable;

/* renamed from: com.eclipsesource.v8.Releasable */
/* loaded from: classes2.dex */
public interface Releasable extends Closeable {
    @Override // java.io.Closeable, java.lang.AutoCloseable
    void close();

    void release();
}
