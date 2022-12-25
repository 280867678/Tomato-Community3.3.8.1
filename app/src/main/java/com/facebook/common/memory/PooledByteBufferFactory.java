package com.facebook.common.memory;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public interface PooledByteBufferFactory {
    /* renamed from: newByteBuffer */
    PooledByteBuffer mo5949newByteBuffer(InputStream inputStream) throws IOException;

    /* renamed from: newByteBuffer */
    PooledByteBuffer mo5950newByteBuffer(InputStream inputStream, int i) throws IOException;

    /* renamed from: newByteBuffer */
    PooledByteBuffer mo5951newByteBuffer(byte[] bArr);

    /* renamed from: newOutputStream */
    PooledByteBufferOutputStream mo5952newOutputStream();

    /* renamed from: newOutputStream */
    PooledByteBufferOutputStream mo5953newOutputStream(int i);
}
