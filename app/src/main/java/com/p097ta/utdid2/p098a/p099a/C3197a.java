package com.p097ta.utdid2.p098a.p099a;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* renamed from: com.ta.utdid2.a.a.a */
/* loaded from: classes3.dex */
public class C3197a {
    /* renamed from: a */
    public static String m3668a(String str) {
        byte[] bArr;
        try {
            bArr = m3664a(m3669a(), str.getBytes());
        } catch (Exception unused) {
            bArr = null;
        }
        if (bArr != null) {
            return m3665a(bArr);
        }
        return null;
    }

    /* renamed from: b */
    public static String m3662b(String str) {
        try {
            return new String(m3661b(m3669a(), m3667a(str)));
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: a */
    private static byte[] m3669a() throws Exception {
        return C3205f.m3650a(new byte[]{33, 83, -50, -89, -84, -114, 80, 99, 10, 63, 22, -65, -11, 30, 101, -118});
    }

    /* renamed from: a */
    private static byte[] m3664a(byte[] bArr, byte[] bArr2) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, secretKeySpec, new IvParameterSpec(m3663b()));
        return cipher.doFinal(bArr2);
    }

    /* renamed from: b */
    private static byte[] m3661b(byte[] bArr, byte[] bArr2) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, secretKeySpec, new IvParameterSpec(m3663b()));
        return cipher.doFinal(bArr2);
    }

    /* renamed from: a */
    private static byte[] m3667a(String str) {
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = Integer.valueOf(str.substring(i2, i2 + 2), 16).byteValue();
        }
        return bArr;
    }

    /* renamed from: a */
    private static String m3665a(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte b : bArr) {
            m3666a(stringBuffer, b);
        }
        return stringBuffer.toString();
    }

    /* renamed from: a */
    private static void m3666a(StringBuffer stringBuffer, byte b) {
        stringBuffer.append("0123456789ABCDEF".charAt((b >> 4) & 15));
        stringBuffer.append("0123456789ABCDEF".charAt(b & 15));
    }

    /* renamed from: b */
    private static byte[] m3663b() {
        try {
            byte[] decode = C3198b.decode("IUQSvE6r1TfFPdPEjfklLw==".getBytes("UTF-8"), 2);
            if (decode != null) {
                return C3205f.m3650a(decode);
            }
        } catch (Exception unused) {
        }
        return new byte[16];
    }
}
