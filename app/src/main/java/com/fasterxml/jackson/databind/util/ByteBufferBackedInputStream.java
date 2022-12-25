package com.fasterxml.jackson.databind.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class ByteBufferBackedInputStream extends InputStream {

    /* renamed from: _b */
    protected final ByteBuffer f1283_b;

    public ByteBufferBackedInputStream(ByteBuffer byteBuffer) {
        this.f1283_b = byteBuffer;
    }

    @Override // java.io.InputStream
    public int available() {
        return this.f1283_b.remaining();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this.f1283_b.hasRemaining()) {
            return this.f1283_b.get() & 255;
        }
        return -1;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (!this.f1283_b.hasRemaining()) {
            return -1;
        }
        int min = Math.min(i2, this.f1283_b.remaining());
        this.f1283_b.get(bArr, i, min);
        return min;
    }
}
