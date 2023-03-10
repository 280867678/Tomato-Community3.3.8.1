package com.google.android.exoplayer2.util;

import java.nio.charset.Charset;

/* loaded from: classes.dex */
public final class ParsableByteArray {
    public byte[] data;
    private int limit;
    private int position;

    public ParsableByteArray() {
    }

    public ParsableByteArray(int i) {
        this.data = new byte[i];
        this.limit = i;
    }

    public ParsableByteArray(byte[] bArr) {
        this.data = bArr;
        this.limit = bArr.length;
    }

    public ParsableByteArray(byte[] bArr, int i) {
        this.data = bArr;
        this.limit = i;
    }

    public void reset(int i) {
        reset(capacity() < i ? new byte[i] : this.data, i);
    }

    public void reset(byte[] bArr, int i) {
        this.data = bArr;
        this.limit = i;
        this.position = 0;
    }

    public void reset() {
        this.position = 0;
        this.limit = 0;
    }

    public int bytesLeft() {
        return this.limit - this.position;
    }

    public int limit() {
        return this.limit;
    }

    public void setLimit(int i) {
        Assertions.checkArgument(i >= 0 && i <= this.data.length);
        this.limit = i;
    }

    public int getPosition() {
        return this.position;
    }

    public int capacity() {
        byte[] bArr = this.data;
        if (bArr == null) {
            return 0;
        }
        return bArr.length;
    }

    public void setPosition(int i) {
        Assertions.checkArgument(i >= 0 && i <= this.limit);
        this.position = i;
    }

    public void skipBytes(int i) {
        setPosition(this.position + i);
    }

    public void readBytes(ParsableBitArray parsableBitArray, int i) {
        readBytes(parsableBitArray.data, 0, i);
        parsableBitArray.setPosition(0);
    }

    public void readBytes(byte[] bArr, int i, int i2) {
        System.arraycopy(this.data, this.position, bArr, i, i2);
        this.position += i2;
    }

    public char peekChar() {
        byte[] bArr = this.data;
        int i = this.position;
        return (char) ((bArr[i + 1] & 255) | ((bArr[i] & 255) << 8));
    }

    public int readUnsignedByte() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        return bArr[i] & 255;
    }

    public int readUnsignedShort() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        int i2 = this.position;
        this.position = i2 + 1;
        return (bArr[i2] & 255) | ((bArr[i] & 255) << 8);
    }

    public int readLittleEndianUnsignedShort() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        int i2 = this.position;
        this.position = i2 + 1;
        return ((bArr[i2] & 255) << 8) | (bArr[i] & 255);
    }

    public short readShort() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        int i2 = this.position;
        this.position = i2 + 1;
        return (short) ((bArr[i2] & 255) | ((bArr[i] & 255) << 8));
    }

    public int readUnsignedInt24() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        int i2 = this.position;
        this.position = i2 + 1;
        int i3 = ((bArr[i] & 255) << 16) | ((bArr[i2] & 255) << 8);
        int i4 = this.position;
        this.position = i4 + 1;
        return (bArr[i4] & 255) | i3;
    }

    public int readInt24() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        int i2 = this.position;
        this.position = i2 + 1;
        int i3 = (((bArr[i] & 255) << 24) >> 8) | ((bArr[i2] & 255) << 8);
        int i4 = this.position;
        this.position = i4 + 1;
        return (bArr[i4] & 255) | i3;
    }

    public long readUnsignedInt() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        int i2 = this.position;
        this.position = i2 + 1;
        long j = ((bArr[i] & 255) << 24) | ((bArr[i2] & 255) << 16);
        int i3 = this.position;
        this.position = i3 + 1;
        long j2 = j | ((bArr[i3] & 255) << 8);
        int i4 = this.position;
        this.position = i4 + 1;
        return j2 | (255 & bArr[i4]);
    }

    public long readLittleEndianUnsignedInt() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        int i2 = this.position;
        this.position = i2 + 1;
        long j = (bArr[i] & 255) | ((bArr[i2] & 255) << 8);
        int i3 = this.position;
        this.position = i3 + 1;
        long j2 = j | ((bArr[i3] & 255) << 16);
        int i4 = this.position;
        this.position = i4 + 1;
        return j2 | ((255 & bArr[i4]) << 24);
    }

    public int readInt() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        int i2 = this.position;
        this.position = i2 + 1;
        int i3 = ((bArr[i] & 255) << 24) | ((bArr[i2] & 255) << 16);
        int i4 = this.position;
        this.position = i4 + 1;
        int i5 = i3 | ((bArr[i4] & 255) << 8);
        int i6 = this.position;
        this.position = i6 + 1;
        return (bArr[i6] & 255) | i5;
    }

    public int readLittleEndianInt() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        int i2 = this.position;
        this.position = i2 + 1;
        int i3 = (bArr[i] & 255) | ((bArr[i2] & 255) << 8);
        int i4 = this.position;
        this.position = i4 + 1;
        int i5 = i3 | ((bArr[i4] & 255) << 16);
        int i6 = this.position;
        this.position = i6 + 1;
        return ((bArr[i6] & 255) << 24) | i5;
    }

    public long readLong() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        int i2 = this.position;
        this.position = i2 + 1;
        long j = ((bArr[i] & 255) << 56) | ((bArr[i2] & 255) << 48);
        int i3 = this.position;
        this.position = i3 + 1;
        long j2 = j | ((bArr[i3] & 255) << 40);
        int i4 = this.position;
        this.position = i4 + 1;
        long j3 = j2 | ((bArr[i4] & 255) << 32);
        int i5 = this.position;
        this.position = i5 + 1;
        long j4 = j3 | ((bArr[i5] & 255) << 24);
        int i6 = this.position;
        this.position = i6 + 1;
        long j5 = j4 | ((bArr[i6] & 255) << 16);
        int i7 = this.position;
        this.position = i7 + 1;
        long j6 = j5 | ((bArr[i7] & 255) << 8);
        int i8 = this.position;
        this.position = i8 + 1;
        return j6 | (255 & bArr[i8]);
    }

    public long readLittleEndianLong() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        int i2 = this.position;
        this.position = i2 + 1;
        long j = (bArr[i] & 255) | ((bArr[i2] & 255) << 8);
        int i3 = this.position;
        this.position = i3 + 1;
        long j2 = j | ((bArr[i3] & 255) << 16);
        int i4 = this.position;
        this.position = i4 + 1;
        long j3 = j2 | ((bArr[i4] & 255) << 24);
        int i5 = this.position;
        this.position = i5 + 1;
        long j4 = j3 | ((bArr[i5] & 255) << 32);
        int i6 = this.position;
        this.position = i6 + 1;
        long j5 = j4 | ((bArr[i6] & 255) << 40);
        int i7 = this.position;
        this.position = i7 + 1;
        long j6 = j5 | ((bArr[i7] & 255) << 48);
        int i8 = this.position;
        this.position = i8 + 1;
        return j6 | ((255 & bArr[i8]) << 56);
    }

    public int readUnsignedFixedPoint1616() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        int i2 = this.position;
        this.position = i2 + 1;
        int i3 = (bArr[i2] & 255) | ((bArr[i] & 255) << 8);
        this.position += 2;
        return i3;
    }

    public int readSynchSafeInt() {
        return (readUnsignedByte() << 21) | (readUnsignedByte() << 14) | (readUnsignedByte() << 7) | readUnsignedByte();
    }

    public int readUnsignedIntToInt() {
        int readInt = readInt();
        if (readInt >= 0) {
            return readInt;
        }
        throw new IllegalStateException("Top bit not zero: " + readInt);
    }

    public int readLittleEndianUnsignedIntToInt() {
        int readLittleEndianInt = readLittleEndianInt();
        if (readLittleEndianInt >= 0) {
            return readLittleEndianInt;
        }
        throw new IllegalStateException("Top bit not zero: " + readLittleEndianInt);
    }

    public long readUnsignedLongToLong() {
        long readLong = readLong();
        if (readLong >= 0) {
            return readLong;
        }
        throw new IllegalStateException("Top bit not zero: " + readLong);
    }

    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    public String readString(int i) {
        return readString(i, Charset.forName("UTF-8"));
    }

    public String readString(int i, Charset charset) {
        String str = new String(this.data, this.position, i, charset);
        this.position += i;
        return str;
    }

    public String readNullTerminatedString(int i) {
        if (i == 0) {
            return "";
        }
        int i2 = (this.position + i) - 1;
        String fromUtf8Bytes = Util.fromUtf8Bytes(this.data, this.position, (i2 >= this.limit || this.data[i2] != 0) ? i : i - 1);
        this.position += i;
        return fromUtf8Bytes;
    }

    public String readNullTerminatedString() {
        if (bytesLeft() == 0) {
            return null;
        }
        int i = this.position;
        while (i < this.limit && this.data[i] != 0) {
            i++;
        }
        byte[] bArr = this.data;
        int i2 = this.position;
        String fromUtf8Bytes = Util.fromUtf8Bytes(bArr, i2, i - i2);
        this.position = i;
        int i3 = this.position;
        if (i3 < this.limit) {
            this.position = i3 + 1;
        }
        return fromUtf8Bytes;
    }

    public String readLine() {
        if (bytesLeft() == 0) {
            return null;
        }
        int i = this.position;
        while (i < this.limit && !Util.isLinebreak(this.data[i])) {
            i++;
        }
        int i2 = this.position;
        if (i - i2 >= 3) {
            byte[] bArr = this.data;
            if (bArr[i2] == -17 && bArr[i2 + 1] == -69 && bArr[i2 + 2] == -65) {
                this.position = i2 + 3;
            }
        }
        byte[] bArr2 = this.data;
        int i3 = this.position;
        String fromUtf8Bytes = Util.fromUtf8Bytes(bArr2, i3, i - i3);
        this.position = i;
        int i4 = this.position;
        int i5 = this.limit;
        if (i4 == i5) {
            return fromUtf8Bytes;
        }
        if (this.data[i4] == 13) {
            this.position = i4 + 1;
            if (this.position == i5) {
                return fromUtf8Bytes;
            }
        }
        byte[] bArr3 = this.data;
        int i6 = this.position;
        if (bArr3[i6] == 10) {
            this.position = i6 + 1;
        }
        return fromUtf8Bytes;
    }

    public long readUtf8EncodedLong() {
        int i;
        int i2;
        byte b;
        int i3;
        long j = this.data[this.position];
        int i4 = 7;
        while (true) {
            if (i4 < 0) {
                break;
            }
            if (((1 << i4) & j) != 0) {
                i4--;
            } else if (i4 < 6) {
                j &= i3 - 1;
                i2 = 7 - i4;
            } else if (i4 == 7) {
                i2 = 1;
            }
        }
        i2 = 0;
        if (i2 == 0) {
            throw new NumberFormatException("Invalid UTF-8 sequence first byte: " + j);
        }
        for (i = 1; i < i2; i++) {
            if ((this.data[this.position + i] & 192) != 128) {
                throw new NumberFormatException("Invalid UTF-8 sequence continuation byte: " + j);
            }
            j = (j << 6) | (b & 63);
        }
        this.position += i2;
        return j;
    }
}
