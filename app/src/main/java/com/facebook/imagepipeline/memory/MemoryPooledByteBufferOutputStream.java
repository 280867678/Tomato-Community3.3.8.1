package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.PooledByteBufferOutputStream;
import com.facebook.common.references.CloseableReference;
import java.io.IOException;

/* loaded from: classes2.dex */
public class MemoryPooledByteBufferOutputStream extends PooledByteBufferOutputStream {
    private CloseableReference<MemoryChunk> mBufRef;
    private int mCount;
    private final MemoryChunkPool mPool;

    public MemoryPooledByteBufferOutputStream(MemoryChunkPool memoryChunkPool) {
        this(memoryChunkPool, memoryChunkPool.getMinBufferSize());
    }

    public MemoryPooledByteBufferOutputStream(MemoryChunkPool memoryChunkPool, int i) {
        Preconditions.checkArgument(i > 0);
        Preconditions.checkNotNull(memoryChunkPool);
        this.mPool = memoryChunkPool;
        this.mCount = 0;
        this.mBufRef = CloseableReference.m4129of(this.mPool.mo5947get(i), this.mPool);
    }

    @Override // com.facebook.common.memory.PooledByteBufferOutputStream
    /* renamed from: toByteBuffer  reason: collision with other method in class */
    public MemoryPooledByteBuffer mo5954toByteBuffer() {
        ensureValid();
        return new MemoryPooledByteBuffer(this.mBufRef, this.mCount);
    }

    @Override // com.facebook.common.memory.PooledByteBufferOutputStream
    public int size() {
        return this.mCount;
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        write(new byte[]{(byte) i});
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        if (i < 0 || i2 < 0 || i + i2 > bArr.length) {
            throw new ArrayIndexOutOfBoundsException("length=" + bArr.length + "; regionStart=" + i + "; regionLength=" + i2);
        }
        ensureValid();
        realloc(this.mCount + i2);
        this.mBufRef.get().write(this.mCount, bArr, i, i2);
        this.mCount += i2;
    }

    @Override // com.facebook.common.memory.PooledByteBufferOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        CloseableReference.closeSafely(this.mBufRef);
        this.mBufRef = null;
        this.mCount = -1;
        super.close();
    }

    void realloc(int i) {
        ensureValid();
        if (i <= this.mBufRef.get().getSize()) {
            return;
        }
        MemoryChunk mo5947get = this.mPool.mo5947get(i);
        this.mBufRef.get().copy(0, mo5947get, 0, this.mCount);
        this.mBufRef.close();
        this.mBufRef = CloseableReference.m4129of(mo5947get, this.mPool);
    }

    private void ensureValid() {
        if (CloseableReference.isValid(this.mBufRef)) {
            return;
        }
        throw new InvalidStreamException();
    }

    /* loaded from: classes2.dex */
    public static class InvalidStreamException extends RuntimeException {
        public InvalidStreamException() {
            super("OutputStream no longer valid");
        }
    }
}
