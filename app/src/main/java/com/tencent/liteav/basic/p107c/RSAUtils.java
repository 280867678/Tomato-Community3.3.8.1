package com.tencent.liteav.basic.p107c;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import javax.crypto.Cipher;

/* renamed from: com.tencent.liteav.basic.c.g */
/* loaded from: classes3.dex */
public class RSAUtils {

    /* renamed from: a */
    public static final byte[] f2396a = "#PART#".getBytes();

    /* renamed from: a */
    public static byte[] m3086a(byte[] bArr, byte[] bArr2) throws Exception {
        PrivateKey generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(bArr2));
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
        cipher.init(2, generatePrivate);
        return cipher.doFinal(bArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0089 A[SYNTHETIC] */
    /* renamed from: b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static byte[] m3085b(byte[] bArr, byte[] bArr2) throws Exception {
        boolean z;
        int length = f2396a.length;
        if (length <= 0) {
            return m3086a(bArr, bArr2);
        }
        int length2 = bArr.length;
        ArrayList<Byte> arrayList = new ArrayList(1024);
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length2) {
            byte b = bArr[i2];
            if (i2 == length2 - 1) {
                byte[] bArr3 = new byte[length2 - i3];
                System.arraycopy(bArr, i3, bArr3, 0, bArr3.length);
                for (byte b2 : m3086a(bArr3, bArr2)) {
                    arrayList.add(Byte.valueOf(b2));
                }
                i3 = i2 + length;
                i2 = i3 - 1;
            } else if (b == f2396a[0]) {
                if (length <= 1) {
                    z = true;
                } else if (i2 + length < length2) {
                    z = false;
                    for (int i4 = 1; i4 < length && f2396a[i4] == bArr[i2 + i4]; i4++) {
                        if (i4 == length - 1) {
                            z = true;
                        }
                    }
                }
                if (!z) {
                    byte[] bArr4 = new byte[i2 - i3];
                    System.arraycopy(bArr, i3, bArr4, 0, bArr4.length);
                    for (byte b3 : m3086a(bArr4, bArr2)) {
                        arrayList.add(Byte.valueOf(b3));
                    }
                    int i5 = i2 + length;
                    i3 = i5;
                    i2 = i5 - 1;
                }
                i2++;
            }
            z = false;
            if (!z) {
            }
            i2++;
        }
        byte[] bArr5 = new byte[arrayList.size()];
        for (Byte b4 : arrayList) {
            bArr5[i] = b4.byteValue();
            i++;
        }
        return bArr5;
    }
}
