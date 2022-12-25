package com.amazonaws.util;

/* loaded from: classes2.dex */
class Base64Codec implements Codec {
    private final byte[] alpahbets = CodecUtils.toBytesDirect("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class LazyHolder {
        private static final byte[] DECODED = decodeTable();

        private static byte[] decodeTable() {
            byte[] bArr = new byte[123];
            for (int i = 0; i <= 122; i++) {
                if (i >= 65 && i <= 90) {
                    bArr[i] = (byte) (i - 65);
                } else if (i >= 48 && i <= 57) {
                    bArr[i] = (byte) (i + 4);
                } else if (i == 43) {
                    bArr[i] = (byte) (i + 19);
                } else if (i == 47) {
                    bArr[i] = (byte) (i + 16);
                } else if (i >= 97 && i <= 122) {
                    bArr[i] = (byte) (i - 71);
                } else {
                    bArr[i] = -1;
                }
            }
            return bArr;
        }
    }

    public byte[] encode(byte[] bArr) {
        int length = bArr.length / 3;
        int length2 = bArr.length % 3;
        int i = 0;
        if (length2 == 0) {
            byte[] bArr2 = new byte[length * 4];
            int i2 = 0;
            while (i < bArr.length) {
                encode3bytes(bArr, i, bArr2, i2);
                i += 3;
                i2 += 4;
            }
            return bArr2;
        }
        byte[] bArr3 = new byte[(length + 1) * 4];
        int i3 = 0;
        while (i < bArr.length - length2) {
            encode3bytes(bArr, i, bArr3, i3);
            i += 3;
            i3 += 4;
        }
        if (length2 == 1) {
            encode1byte(bArr, i, bArr3, i3);
        } else if (length2 == 2) {
            encode2bytes(bArr, i, bArr3, i3);
        }
        return bArr3;
    }

    void encode3bytes(byte[] bArr, int i, byte[] bArr2, int i2) {
        int i3 = i2 + 1;
        byte[] bArr3 = this.alpahbets;
        int i4 = i + 1;
        byte b = bArr[i];
        bArr2[i2] = bArr3[(b >>> 2) & 63];
        int i5 = i3 + 1;
        int i6 = i4 + 1;
        byte b2 = bArr[i4];
        bArr2[i3] = bArr3[((b & 3) << 4) | ((b2 >>> 4) & 15)];
        byte b3 = bArr[i6];
        bArr2[i5] = bArr3[((b2 & 15) << 2) | ((b3 >>> 6) & 3)];
        bArr2[i5 + 1] = bArr3[b3 & 63];
    }

    void encode2bytes(byte[] bArr, int i, byte[] bArr2, int i2) {
        int i3 = i2 + 1;
        byte[] bArr3 = this.alpahbets;
        int i4 = i + 1;
        byte b = bArr[i];
        bArr2[i2] = bArr3[(b >>> 2) & 63];
        int i5 = i3 + 1;
        byte b2 = bArr[i4];
        bArr2[i3] = bArr3[((b & 3) << 4) | ((b2 >>> 4) & 15)];
        bArr2[i5] = bArr3[(b2 & 15) << 2];
        bArr2[i5 + 1] = 61;
    }

    void encode1byte(byte[] bArr, int i, byte[] bArr2, int i2) {
        int i3 = i2 + 1;
        byte[] bArr3 = this.alpahbets;
        byte b = bArr[i];
        bArr2[i2] = bArr3[(b >>> 2) & 63];
        int i4 = i3 + 1;
        bArr2[i3] = bArr3[(b & 3) << 4];
        bArr2[i4] = 61;
        bArr2[i4 + 1] = 61;
    }

    void decode4bytes(byte[] bArr, int i, byte[] bArr2, int i2) {
        int i3 = i2 + 1;
        int i4 = i + 1;
        int i5 = i4 + 1;
        int pos = pos(bArr[i4]);
        bArr2[i2] = (byte) ((pos(bArr[i]) << 2) | ((pos >>> 4) & 3));
        int i6 = i5 + 1;
        int pos2 = pos(bArr[i5]);
        bArr2[i3] = (byte) (((pos & 15) << 4) | ((pos2 >>> 2) & 15));
        bArr2[i3 + 1] = (byte) (pos(bArr[i6]) | ((pos2 & 3) << 6));
    }

    void decode1to3bytes(int i, byte[] bArr, int i2, byte[] bArr2, int i3) {
        int i4 = i3 + 1;
        int i5 = i2 + 1;
        int i6 = i5 + 1;
        int pos = pos(bArr[i5]);
        bArr2[i3] = (byte) ((pos(bArr[i2]) << 2) | ((pos >>> 4) & 3));
        if (i == 1) {
            CodecUtils.sanityCheckLastPos(pos, 15);
            return;
        }
        int i7 = i4 + 1;
        int i8 = i6 + 1;
        int pos2 = pos(bArr[i6]);
        bArr2[i4] = (byte) ((15 & (pos2 >>> 2)) | ((pos & 15) << 4));
        if (i == 2) {
            CodecUtils.sanityCheckLastPos(pos2, 3);
        } else {
            bArr2[i7] = (byte) (((pos2 & 3) << 6) | pos(bArr[i8]));
        }
    }

    public byte[] decode(byte[] bArr, int i) {
        if (i % 4 != 0) {
            throw new IllegalArgumentException("Input is expected to be encoded in multiple of 4 bytes but found: " + i);
        }
        int i2 = i - 1;
        int i3 = 0;
        while (i3 < 2 && i2 > -1 && bArr[i2] == 61) {
            i2--;
            i3++;
        }
        int i4 = 1;
        if (i3 == 0) {
            i4 = 3;
        } else if (i3 == 1) {
            i4 = 2;
        } else if (i3 != 2) {
            throw new Error("Impossible");
        }
        byte[] bArr2 = new byte[((i / 4) * 3) - (3 - i4)];
        int i5 = 0;
        int i6 = 0;
        while (i6 < bArr2.length - (i4 % 3)) {
            decode4bytes(bArr, i5, bArr2, i6);
            i5 += 4;
            i6 += 3;
        }
        if (i4 < 3) {
            decode1to3bytes(i4, bArr, i5, bArr2, i6);
        }
        return bArr2;
    }

    protected int pos(byte b) {
        byte b2 = LazyHolder.DECODED[b];
        if (b2 > -1) {
            return b2;
        }
        throw new IllegalArgumentException("Invalid base 64 character: '" + ((char) b) + "'");
    }
}
