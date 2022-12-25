package com.alipay.sdk.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* renamed from: com.alipay.sdk.encrypt.f */
/* loaded from: classes2.dex */
public class C0972f {

    /* renamed from: a */
    private static String f988a = "DESede/CBC/PKCS5Padding";

    /* renamed from: a */
    public static byte[] m4543a(String str, byte[] bArr, String str2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(), "DESede");
            Cipher cipher = Cipher.getInstance(f988a);
            cipher.init(1, secretKeySpec, new IvParameterSpec(C0970d.m4547a(cipher, str2)));
            return cipher.doFinal(bArr);
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: b */
    public static byte[] m4541b(String str, byte[] bArr, String str2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(), "DESede");
            Cipher cipher = Cipher.getInstance(f988a);
            cipher.init(2, secretKeySpec, new IvParameterSpec(C0970d.m4547a(cipher, str2)));
            return cipher.doFinal(bArr);
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: a */
    public static String m4544a(String str, String str2, String str3) {
        try {
            return C0967a.m4556a(m4543a(str, str2.getBytes(), str3));
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: b */
    public static String m4542b(String str, String str2, String str3) {
        try {
            return new String(m4541b(str, C0967a.m4557a(str2), str3));
        } catch (Exception unused) {
            return null;
        }
    }
}
