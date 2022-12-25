package com.alipay.sdk.encrypt;

/* renamed from: com.alipay.sdk.encrypt.a */
/* loaded from: classes2.dex */
public final class C0967a {

    /* renamed from: i */
    private static final byte[] f986i = new byte[128];

    /* renamed from: j */
    private static final char[] f987j = new char[64];

    /* renamed from: a */
    private static boolean m4558a(char c) {
        return c == ' ' || c == '\r' || c == '\n' || c == '\t';
    }

    /* renamed from: b */
    private static boolean m4554b(char c) {
        return c == '=';
    }

    static {
        int i;
        int i2;
        int i3 = 0;
        for (int i4 = 0; i4 < 128; i4++) {
            f986i[i4] = -1;
        }
        for (int i5 = 90; i5 >= 65; i5--) {
            f986i[i5] = (byte) (i5 - 65);
        }
        int i6 = 122;
        while (true) {
            i = 26;
            if (i6 < 97) {
                break;
            }
            f986i[i6] = (byte) ((i6 - 97) + 26);
            i6--;
        }
        int i7 = 57;
        while (true) {
            i2 = 52;
            if (i7 < 48) {
                break;
            }
            f986i[i7] = (byte) ((i7 - 48) + 52);
            i7--;
        }
        byte[] bArr = f986i;
        bArr[43] = 62;
        bArr[47] = 63;
        for (int i8 = 0; i8 <= 25; i8++) {
            f987j[i8] = (char) (i8 + 65);
        }
        int i9 = 0;
        while (i <= 51) {
            f987j[i] = (char) (i9 + 97);
            i++;
            i9++;
        }
        while (i2 <= 61) {
            f987j[i2] = (char) (i3 + 48);
            i2++;
            i3++;
        }
        char[] cArr = f987j;
        cArr[62] = '+';
        cArr[63] = '/';
    }

    /* renamed from: c */
    private static boolean m4553c(char c) {
        return c < 128 && f986i[c] != -1;
    }

    /* renamed from: a */
    public static String m4556a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length * 8;
        if (length == 0) {
            return "";
        }
        int i = length % 24;
        int i2 = length / 24;
        char[] cArr = new char[(i != 0 ? i2 + 1 : i2) * 4];
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i3 < i2) {
            int i6 = i4 + 1;
            byte b = bArr[i4];
            int i7 = i6 + 1;
            byte b2 = bArr[i6];
            int i8 = i7 + 1;
            byte b3 = bArr[i7];
            byte b4 = (byte) (b2 & 15);
            byte b5 = (byte) (b & 3);
            int i9 = b & Byte.MIN_VALUE;
            int i10 = b >> 2;
            if (i9 != 0) {
                i10 ^= 192;
            }
            byte b6 = (byte) i10;
            int i11 = b2 & Byte.MIN_VALUE;
            int i12 = b2 >> 4;
            if (i11 != 0) {
                i12 ^= 240;
            }
            byte b7 = (byte) i12;
            int i13 = (b3 & Byte.MIN_VALUE) == 0 ? b3 >> 6 : (b3 >> 6) ^ 252;
            int i14 = i5 + 1;
            char[] cArr2 = f987j;
            cArr[i5] = cArr2[b6];
            int i15 = i14 + 1;
            cArr[i14] = cArr2[(b5 << 4) | b7];
            int i16 = i15 + 1;
            cArr[i15] = cArr2[(b4 << 2) | ((byte) i13)];
            cArr[i16] = cArr2[b3 & 63];
            i3++;
            i5 = i16 + 1;
            i4 = i8;
        }
        if (i == 8) {
            byte b8 = bArr[i4];
            byte b9 = (byte) (b8 & 3);
            int i17 = b8 & Byte.MIN_VALUE;
            int i18 = b8 >> 2;
            if (i17 != 0) {
                i18 ^= 192;
            }
            int i19 = i5 + 1;
            char[] cArr3 = f987j;
            cArr[i5] = cArr3[(byte) i18];
            int i20 = i19 + 1;
            cArr[i19] = cArr3[b9 << 4];
            cArr[i20] = '=';
            cArr[i20 + 1] = '=';
        } else if (i == 16) {
            byte b10 = bArr[i4];
            byte b11 = bArr[i4 + 1];
            byte b12 = (byte) (b11 & 15);
            byte b13 = (byte) (b10 & 3);
            int i21 = b10 & Byte.MIN_VALUE;
            int i22 = b10 >> 2;
            if (i21 != 0) {
                i22 ^= 192;
            }
            byte b14 = (byte) i22;
            int i23 = b11 & Byte.MIN_VALUE;
            int i24 = b11 >> 4;
            if (i23 != 0) {
                i24 ^= 240;
            }
            int i25 = i5 + 1;
            char[] cArr4 = f987j;
            cArr[i5] = cArr4[b14];
            int i26 = i25 + 1;
            cArr[i25] = cArr4[((byte) i24) | (b13 << 4)];
            cArr[i26] = cArr4[b12 << 2];
            cArr[i26 + 1] = '=';
        }
        return new String(cArr);
    }

    /* renamed from: a */
    public static byte[] m4557a(String str) {
        if (str == null) {
            return null;
        }
        char[] charArray = str.toCharArray();
        int m4555a = m4555a(charArray);
        if (m4555a % 4 != 0) {
            return null;
        }
        int i = m4555a / 4;
        if (i == 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[i * 3];
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i2 < i - 1) {
            int i5 = i3 + 1;
            char c = charArray[i3];
            if (m4553c(c)) {
                int i6 = i5 + 1;
                char c2 = charArray[i5];
                if (m4553c(c2)) {
                    int i7 = i6 + 1;
                    char c3 = charArray[i6];
                    if (m4553c(c3)) {
                        int i8 = i7 + 1;
                        char c4 = charArray[i7];
                        if (m4553c(c4)) {
                            byte[] bArr2 = f986i;
                            byte b = bArr2[c];
                            byte b2 = bArr2[c2];
                            byte b3 = bArr2[c3];
                            byte b4 = bArr2[c4];
                            int i9 = i4 + 1;
                            bArr[i4] = (byte) ((b << 2) | (b2 >> 4));
                            int i10 = i9 + 1;
                            bArr[i9] = (byte) (((b2 & 15) << 4) | ((b3 >> 2) & 15));
                            i4 = i10 + 1;
                            bArr[i10] = (byte) ((b3 << 6) | b4);
                            i2++;
                            i3 = i8;
                        }
                    }
                }
            }
            return null;
        }
        int i11 = i3 + 1;
        char c5 = charArray[i3];
        if (!m4553c(c5)) {
            return null;
        }
        int i12 = i11 + 1;
        char c6 = charArray[i11];
        if (!m4553c(c6)) {
            return null;
        }
        byte[] bArr3 = f986i;
        byte b5 = bArr3[c5];
        byte b6 = bArr3[c6];
        int i13 = i12 + 1;
        char c7 = charArray[i12];
        char c8 = charArray[i13];
        if (!m4553c(c7) || !m4553c(c8)) {
            if (m4554b(c7) && m4554b(c8)) {
                if ((b6 & 15) != 0) {
                    return null;
                }
                int i14 = i2 * 3;
                byte[] bArr4 = new byte[i14 + 1];
                System.arraycopy(bArr, 0, bArr4, 0, i14);
                bArr4[i4] = (byte) ((b5 << 2) | (b6 >> 4));
                return bArr4;
            } else if (m4554b(c7) || !m4554b(c8)) {
                return null;
            } else {
                byte b7 = f986i[c7];
                if ((b7 & 3) != 0) {
                    return null;
                }
                int i15 = i2 * 3;
                byte[] bArr5 = new byte[i15 + 2];
                System.arraycopy(bArr, 0, bArr5, 0, i15);
                bArr5[i4] = (byte) ((b5 << 2) | (b6 >> 4));
                bArr5[i4 + 1] = (byte) (((b7 >> 2) & 15) | ((b6 & 15) << 4));
                return bArr5;
            }
        }
        byte[] bArr6 = f986i;
        byte b8 = bArr6[c7];
        byte b9 = bArr6[c8];
        int i16 = i4 + 1;
        bArr[i4] = (byte) ((b5 << 2) | (b6 >> 4));
        bArr[i16] = (byte) (((b6 & 15) << 4) | ((b8 >> 2) & 15));
        bArr[i16 + 1] = (byte) (b9 | (b8 << 6));
        return bArr;
    }

    /* renamed from: a */
    private static int m4555a(char[] cArr) {
        if (cArr == null) {
            return 0;
        }
        int length = cArr.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (!m4558a(cArr[i2])) {
                cArr[i] = cArr[i2];
                i++;
            }
        }
        return i;
    }
}
