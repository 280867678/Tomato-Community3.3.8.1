package com.facebook.common.memory;

import com.facebook.common.internal.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public class PooledByteStreams {
    private final ByteArrayPool mByteArrayPool;
    private final int mTempBufSize;

    public PooledByteStreams(ByteArrayPool byteArrayPool) {
        this(byteArrayPool, 16384);
    }

    public PooledByteStreams(ByteArrayPool byteArrayPool, int i) {
        Preconditions.checkArgument(i > 0);
        this.mTempBufSize = i;
        this.mByteArrayPool = byteArrayPool;
    }

    public long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] mo5947get = this.mByteArrayPool.mo5947get(this.mTempBufSize);
        long j = 0;
        while (true) {
            try {
                int read = inputStream.read(mo5947get, 0, this.mTempBufSize);
                if (read == -1) {
                    return j;
                }
                outputStream.write(mo5947get, 0, read);
                j += read;
            } finally {
                this.mByteArrayPool.release(mo5947get);
            }
        }
    }
}
