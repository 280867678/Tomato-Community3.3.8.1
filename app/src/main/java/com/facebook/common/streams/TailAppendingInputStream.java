package com.facebook.common.streams;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class TailAppendingInputStream extends FilterInputStream {
    private int mMarkedTailOffset;
    private final byte[] mTail;
    private int mTailOffset;

    public TailAppendingInputStream(InputStream inputStream, byte[] bArr) {
        super(inputStream);
        if (inputStream != null) {
            if (bArr == null) {
                throw new NullPointerException();
            }
            this.mTail = bArr;
            return;
        }
        throw new NullPointerException();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        int read = ((FilterInputStream) this).in.read();
        return read != -1 ? read : readNextTailByte();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int read = ((FilterInputStream) this).in.read(bArr, i, i2);
        if (read != -1) {
            return read;
        }
        int i3 = 0;
        if (i2 == 0) {
            return 0;
        }
        while (i3 < i2) {
            int readNextTailByte = readNextTailByte();
            if (readNextTailByte == -1) {
                break;
            }
            bArr[i + i3] = (byte) readNextTailByte;
            i3++;
        }
        if (i3 <= 0) {
            return -1;
        }
        return i3;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public void reset() throws IOException {
        if (((FilterInputStream) this).in.markSupported()) {
            ((FilterInputStream) this).in.reset();
            this.mTailOffset = this.mMarkedTailOffset;
            return;
        }
        throw new IOException("mark is not supported");
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public void mark(int i) {
        if (((FilterInputStream) this).in.markSupported()) {
            super.mark(i);
            this.mMarkedTailOffset = this.mTailOffset;
        }
    }

    private int readNextTailByte() {
        int i = this.mTailOffset;
        byte[] bArr = this.mTail;
        if (i >= bArr.length) {
            return -1;
        }
        this.mTailOffset = i + 1;
        return bArr[i] & 255;
    }
}
