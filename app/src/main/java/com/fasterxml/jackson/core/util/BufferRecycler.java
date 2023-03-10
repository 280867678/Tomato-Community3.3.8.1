package com.fasterxml.jackson.core.util;

import com.zzz.ipfssdk.callback.exception.CodeState;

/* loaded from: classes2.dex */
public class BufferRecycler {
    private static final int[] BYTE_BUFFER_LENGTHS = {8000, 8000, 2000, 2000};
    private static final int[] CHAR_BUFFER_LENGTHS = {CodeState.CODES.CODE_PLAYER_PLAY_ERROR, CodeState.CODES.CODE_PLAYER_PLAY_ERROR, 200, 200};
    protected final byte[][] _byteBuffers;
    protected final char[][] _charBuffers;

    public BufferRecycler() {
        this(4, 4);
    }

    protected BufferRecycler(int i, int i2) {
        this._byteBuffers = new byte[i];
        this._charBuffers = new char[i2];
    }

    public final byte[] allocByteBuffer(int i) {
        return allocByteBuffer(i, 0);
    }

    public byte[] allocByteBuffer(int i, int i2) {
        int byteBufferLength = byteBufferLength(i);
        if (i2 < byteBufferLength) {
            i2 = byteBufferLength;
        }
        byte[][] bArr = this._byteBuffers;
        byte[] bArr2 = bArr[i];
        if (bArr2 == null || bArr2.length < i2) {
            return balloc(i2);
        }
        bArr[i] = null;
        return bArr2;
    }

    public void releaseByteBuffer(int i, byte[] bArr) {
        this._byteBuffers[i] = bArr;
    }

    public final char[] allocCharBuffer(int i) {
        return allocCharBuffer(i, 0);
    }

    public char[] allocCharBuffer(int i, int i2) {
        int charBufferLength = charBufferLength(i);
        if (i2 < charBufferLength) {
            i2 = charBufferLength;
        }
        char[][] cArr = this._charBuffers;
        char[] cArr2 = cArr[i];
        if (cArr2 == null || cArr2.length < i2) {
            return calloc(i2);
        }
        cArr[i] = null;
        return cArr2;
    }

    public void releaseCharBuffer(int i, char[] cArr) {
        this._charBuffers[i] = cArr;
    }

    protected int byteBufferLength(int i) {
        return BYTE_BUFFER_LENGTHS[i];
    }

    protected int charBufferLength(int i) {
        return CHAR_BUFFER_LENGTHS[i];
    }

    protected byte[] balloc(int i) {
        return new byte[i];
    }

    protected char[] calloc(int i) {
        return new char[i];
    }
}
