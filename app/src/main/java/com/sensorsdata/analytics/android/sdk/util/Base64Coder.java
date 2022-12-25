package com.sensorsdata.analytics.android.sdk.util;

import com.sensorsdata.analytics.android.sdk.SALog;
import java.io.UnsupportedEncodingException;

/* loaded from: classes3.dex */
public class Base64Coder {
    public static final String CHARSET_UTF8 = "UTF-8";
    private static char[] map1 = new char[64];
    private static byte[] map2 = new byte[128];

    static {
        char c = 'A';
        int i = 0;
        while (c <= 'Z') {
            map1[i] = c;
            c = (char) (c + 1);
            i++;
        }
        char c2 = 'a';
        while (c2 <= 'z') {
            map1[i] = c2;
            c2 = (char) (c2 + 1);
            i++;
        }
        char c3 = '0';
        while (c3 <= '9') {
            map1[i] = c3;
            c3 = (char) (c3 + 1);
            i++;
        }
        char[] cArr = map1;
        cArr[i] = '+';
        cArr[i + 1] = '/';
        int i2 = 0;
        while (true) {
            byte[] bArr = map2;
            if (i2 < bArr.length) {
                bArr[i2] = -1;
                i2++;
            }
        }
        for (int i3 = 0; i3 < 64; i3++) {
            map2[map1[i3]] = (byte) i3;
        }
    }

    public static String encodeString(String str) {
        try {
            return new String(encode(str.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    public static char[] encode(byte[] bArr) {
        return encode(bArr, bArr.length);
    }

    public static char[] encode(byte[] bArr, int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6 = ((i * 4) + 2) / 3;
        char[] cArr = new char[((i + 2) / 3) * 4];
        int i7 = 0;
        int i8 = 0;
        while (i7 < i) {
            int i9 = i7 + 1;
            int i10 = bArr[i7] & 255;
            if (i9 < i) {
                i2 = i9 + 1;
                i3 = bArr[i9] & 255;
            } else {
                i2 = i9;
                i3 = 0;
            }
            if (i2 < i) {
                i4 = i2 + 1;
                i5 = bArr[i2] & 255;
            } else {
                i4 = i2;
                i5 = 0;
            }
            int i11 = i10 >>> 2;
            int i12 = ((i10 & 3) << 4) | (i3 >>> 4);
            int i13 = ((i3 & 15) << 2) | (i5 >>> 6);
            int i14 = i5 & 63;
            int i15 = i8 + 1;
            char[] cArr2 = map1;
            cArr[i8] = cArr2[i11];
            int i16 = i15 + 1;
            cArr[i15] = cArr2[i12];
            char c = '=';
            cArr[i16] = i16 < i6 ? cArr2[i13] : '=';
            int i17 = i16 + 1;
            if (i17 < i6) {
                c = map1[i14];
            }
            cArr[i17] = c;
            i8 = i17 + 1;
            i7 = i4;
        }
        return cArr;
    }

    public static String decodeString(String str) {
        return new String(decode(str));
    }

    public static byte[] decode(String str) {
        return decode(str.toCharArray());
    }

    public static byte[] decode(char[] cArr) {
        int i;
        char c;
        int i2;
        int length = cArr.length;
        if (length % 4 != 0) {
            throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
        }
        while (length > 0 && cArr[length - 1] == '=') {
            length--;
        }
        int i3 = (length * 3) / 4;
        byte[] bArr = new byte[i3];
        int i4 = 0;
        for (int i5 = 0; i5 < length; i5 = i) {
            int i6 = i5 + 1;
            char c2 = cArr[i5];
            int i7 = i6 + 1;
            char c3 = cArr[i6];
            char c4 = 'A';
            if (i7 < length) {
                i = i7 + 1;
                c = cArr[i7];
            } else {
                i = i7;
                c = 'A';
            }
            if (i < length) {
                int i8 = i + 1;
                char c5 = cArr[i];
                i = i8;
                c4 = c5;
            }
            if (c2 > 127 || c3 > 127 || c > 127 || c4 > 127) {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            byte[] bArr2 = map2;
            byte b = bArr2[c2];
            byte b2 = bArr2[c3];
            byte b3 = bArr2[c];
            byte b4 = bArr2[c4];
            if (b < 0 || b2 < 0 || b3 < 0 || b4 < 0) {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            int i9 = (b << 2) | (b2 >>> 4);
            int i10 = ((b2 & 15) << 4) | (b3 >>> 2);
            int i11 = ((b3 & 3) << 6) | b4;
            int i12 = i4 + 1;
            bArr[i4] = (byte) i9;
            if (i12 < i3) {
                i2 = i12 + 1;
                bArr[i12] = (byte) i10;
            } else {
                i2 = i12;
            }
            if (i2 < i3) {
                i4 = i2 + 1;
                bArr[i2] = (byte) i11;
            } else {
                i4 = i2;
            }
        }
        return bArr;
    }
}
