package com.youdao.sdk.app.other;

import android.util.Base64;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/* renamed from: com.youdao.sdk.app.other.i */
/* loaded from: classes4.dex */
public class C5167i {
    /* renamed from: a */
    public static byte[] m186a(String str, String str2) {
        return m185a(str, str2.getBytes());
    }

    /* renamed from: a */
    public static byte[] m185a(String str, byte[] bArr) {
        try {
            SecretKey generateSecret = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(str.getBytes()));
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(1, generateSecret, new IvParameterSpec(str.getBytes()));
            return cipher.doFinal(bArr);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /* renamed from: b */
    public static byte[] m183b(String str, byte[] bArr) {
        try {
            new SecureRandom();
            SecretKey generateSecret = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(str.getBytes()));
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(2, generateSecret, new IvParameterSpec(str.getBytes()));
            return cipher.doFinal(bArr);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /* renamed from: b */
    public static String m184b(String str, String str2) {
        try {
            return new String(m183b(str, Base64.decode(str2.getBytes(), 0)));
        } catch (Exception unused) {
            return "";
        }
    }
}
