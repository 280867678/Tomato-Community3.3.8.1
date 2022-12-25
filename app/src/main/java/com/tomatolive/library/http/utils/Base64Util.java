package com.tomatolive.library.http.utils;

import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.ByteArrayOutputStream;

/* loaded from: classes3.dex */
public class Base64Util {
    private static final char[] base64EncodeChars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static byte[] base64DecodeChars = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1};

    private Base64Util() {
    }

    public static String encode(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        int length = bArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            int i2 = i + 1;
            int i3 = bArr[i] & 255;
            if (i2 == length) {
                sb.append(base64EncodeChars[i3 >>> 2]);
                sb.append(base64EncodeChars[(i3 & 3) << 4]);
                sb.append("==");
                break;
            }
            int i4 = i2 + 1;
            int i5 = bArr[i2] & 255;
            if (i4 == length) {
                sb.append(base64EncodeChars[i3 >>> 2]);
                sb.append(base64EncodeChars[((i3 & 3) << 4) | ((i5 & 240) >>> 4)]);
                sb.append(base64EncodeChars[(i5 & 15) << 2]);
                sb.append(SimpleComparison.EQUAL_TO_OPERATION);
                break;
            }
            int i6 = i4 + 1;
            int i7 = bArr[i4] & 255;
            sb.append(base64EncodeChars[i3 >>> 2]);
            sb.append(base64EncodeChars[((i3 & 3) << 4) | ((i5 & 240) >>> 4)]);
            sb.append(base64EncodeChars[((i5 & 15) << 2) | ((i7 & 192) >>> 6)]);
            sb.append(base64EncodeChars[i7 & 63]);
            i = i6;
        }
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x007a, code lost:
        if (r1 != (-1)) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x007d, code lost:
        r0.write(r1 | ((r4 & 3) << 6));
        r1 = r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static byte[] decode(String str, String str2) throws Exception {
        int i;
        byte b;
        int i2;
        byte b2;
        int i3;
        byte b3;
        byte[] bytes = str.getBytes(str2);
        int length = bytes.length;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(length);
        int i4 = 0;
        while (i4 < length) {
            while (true) {
                i = i4 + 1;
                b = base64DecodeChars[bytes[i4]];
                if (i >= length || b != -1) {
                    break;
                }
                i4 = i;
            }
            if (b == -1) {
                break;
            }
            while (true) {
                i2 = i + 1;
                b2 = base64DecodeChars[bytes[i]];
                if (i2 >= length || b2 != -1) {
                    break;
                }
                i = i2;
            }
            if (b2 == -1) {
                break;
            }
            byteArrayOutputStream.write((b << 2) | ((b2 & 48) >>> 4));
            while (true) {
                i3 = i2 + 1;
                byte b4 = bytes[i2];
                if (b4 == 61) {
                    return byteArrayOutputStream.toByteArray();
                }
                b3 = base64DecodeChars[b4];
                if (i3 >= length || b3 != -1) {
                    break;
                }
                i2 = i3;
            }
            if (b3 == -1) {
                break;
            }
            byteArrayOutputStream.write(((b2 & 15) << 4) | ((b3 & 60) >>> 2));
            while (true) {
                int i5 = i3 + 1;
                byte b5 = bytes[i3];
                if (b5 == 61) {
                    return byteArrayOutputStream.toByteArray();
                }
                byte b6 = base64DecodeChars[b5];
                if (i5 >= length || b6 != -1) {
                    break;
                }
                i3 = i5;
            }
        }
        return byteArrayOutputStream.toByteArray();
    }
}
