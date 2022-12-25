package com.squareup.okhttp;

import java.io.Closeable;
import java.io.IOException;
import okio.BufferedSource;

/* loaded from: classes3.dex */
public abstract class ResponseBody implements Closeable {
    public abstract BufferedSource source();

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        source().close();
    }
}
