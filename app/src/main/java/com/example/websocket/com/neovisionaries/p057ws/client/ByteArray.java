package com.example.websocket.com.neovisionaries.p057ws.client;

import java.nio.ByteBuffer;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.ByteArray */
/* loaded from: classes2.dex */
class ByteArray {
    private static final int ADDITIONAL_BUFFER_SIZE = 1024;
    private ByteBuffer mBuffer;
    private int mLength;

    public ByteArray(int i) {
        this.mBuffer = ByteBuffer.allocate(i);
        this.mLength = 0;
    }

    public ByteArray(byte[] bArr) {
        this.mBuffer = ByteBuffer.wrap(bArr);
        this.mLength = bArr.length;
    }

    public int length() {
        return this.mLength;
    }

    public byte get(int i) throws IndexOutOfBoundsException {
        if (i < 0 || this.mLength <= i) {
            throw new IndexOutOfBoundsException(String.format("Bad index: index=%d, length=%d", Integer.valueOf(i), Integer.valueOf(this.mLength)));
        }
        return this.mBuffer.get(i);
    }

    private void expandBuffer(int i) {
        ByteBuffer allocate = ByteBuffer.allocate(i);
        int position = this.mBuffer.position();
        this.mBuffer.position(0);
        allocate.put(this.mBuffer);
        allocate.position(position);
        this.mBuffer = allocate;
    }

    public void put(int i) {
        int capacity = this.mBuffer.capacity();
        int i2 = this.mLength;
        if (capacity < i2 + 1) {
            expandBuffer(i2 + 1024);
        }
        this.mBuffer.put((byte) i);
        this.mLength++;
    }

    public void put(byte[] bArr) {
        int capacity = this.mBuffer.capacity();
        int i = this.mLength;
        if (capacity < bArr.length + i) {
            expandBuffer(i + bArr.length + 1024);
        }
        this.mBuffer.put(bArr);
        this.mLength += bArr.length;
    }

    public void put(byte[] bArr, int i, int i2) {
        int capacity = this.mBuffer.capacity();
        int i3 = this.mLength;
        if (capacity < i3 + i2) {
            expandBuffer(i3 + i2 + 1024);
        }
        this.mBuffer.put(bArr, i, i2);
        this.mLength += i2;
    }

    public void put(ByteArray byteArray, int i, int i2) {
        put(byteArray.mBuffer.array(), i, i2);
    }

    public byte[] toBytes() {
        return toBytes(0);
    }

    public byte[] toBytes(int i) {
        return toBytes(i, length());
    }

    public byte[] toBytes(int i, int i2) {
        int i3 = i2 - i;
        if (i3 < 0 || i < 0 || this.mLength < i2) {
            throw new IllegalArgumentException(String.format("Bad range: beginIndex=%d, endIndex=%d, length=%d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(this.mLength)));
        }
        byte[] bArr = new byte[i3];
        if (i3 != 0) {
            System.arraycopy(this.mBuffer.array(), i, bArr, 0, i3);
        }
        return bArr;
    }

    public void clear() {
        this.mBuffer.clear();
        this.mBuffer.position(0);
        this.mLength = 0;
    }

    public void shrink(int i) {
        if (this.mBuffer.capacity() <= i) {
            return;
        }
        int i2 = this.mLength;
        byte[] bytes = toBytes(i2 - i, i2);
        this.mBuffer = ByteBuffer.wrap(bytes);
        this.mBuffer.position(bytes.length);
        this.mLength = bytes.length;
    }

    public boolean getBit(int i) {
        return ((1 << (i % 8)) & get(i / 8)) != 0;
    }

    public int getBits(int i, int i2) {
        int i3 = 0;
        int i4 = 0;
        int i5 = 1;
        while (i3 < i2) {
            if (getBit(i + i3)) {
                i4 += i5;
            }
            i3++;
            i5 *= 2;
        }
        return i4;
    }

    public int getHuffmanBits(int i, int i2) {
        int i3 = 1;
        int i4 = i2 - 1;
        int i5 = 0;
        while (i4 >= 0) {
            if (getBit(i + i4)) {
                i5 += i3;
            }
            i4--;
            i3 *= 2;
        }
        return i5;
    }

    public boolean readBit(int[] iArr) {
        boolean bit = getBit(iArr[0]);
        iArr[0] = iArr[0] + 1;
        return bit;
    }

    public int readBits(int[] iArr, int i) {
        int bits = getBits(iArr[0], i);
        iArr[0] = iArr[0] + i;
        return bits;
    }

    public void setBit(int i, boolean z) {
        int i2 = i / 8;
        int i3 = i % 8;
        byte b = get(i2);
        this.mBuffer.put(i2, (byte) (z ? (1 << i3) | b : (~(1 << i3)) & b));
    }

    public void clearBit(int i) {
        setBit(i, false);
    }
}
