package com.alipay.security.mobile.module.p047a.p048a;

import com.alipay.security.mobile.module.p047a.C1037a;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/* renamed from: com.alipay.security.mobile.module.a.a.c */
/* loaded from: classes2.dex */
public final class C1040c {

    /* renamed from: a */
    private static String f1120a = "idnjfhncnsfuobcnt847y929o449u474w7j3h22aoddc98euk#%&&)*&^%#";

    /* renamed from: a */
    public static String m4290a() {
        String str = new String();
        for (int i = 0; i < f1120a.length() - 1; i += 4) {
            str = str + f1120a.charAt(i);
        }
        return str;
    }

    /* renamed from: a */
    public static String m4288a(String str, String str2) {
        try {
            PBEKeySpec m4289a = m4289a(str);
            byte[] bytes = str2.getBytes();
            byte[] m4286b = m4286b();
            SecretKeySpec secretKeySpec = new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(m4289a).getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(1, secretKeySpec, new IvParameterSpec(m4286b));
            byte[] salt = m4289a.getSalt();
            ByteBuffer allocate = ByteBuffer.allocate(salt.length + cipher.getOutputSize(bytes.length));
            allocate.put(salt);
            cipher.doFinal(ByteBuffer.wrap(bytes), allocate);
            return m4287a(allocate.array());
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: a */
    private static String m4287a(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte b : bArr) {
            stringBuffer.append("0123456789ABCDEF".charAt((b >> 4) & 15));
            stringBuffer.append("0123456789ABCDEF".charAt(b & 15));
        }
        return stringBuffer.toString();
    }

    /* renamed from: a */
    private static PBEKeySpec m4289a(String str) {
        Class<?> cls = Class.forName(new String(C1038a.m4292a("amF2YS5zZWN1cml0eS5TZWN1cmVSYW5kb20=")));
        Object newInstance = cls.newInstance();
        byte[] bArr = new byte[16];
        Method method = cls.getMethod("nextBytes", bArr.getClass());
        method.setAccessible(true);
        method.invoke(newInstance, bArr);
        return new PBEKeySpec(str.toCharArray(), bArr, 10, 128);
    }

    /* renamed from: b */
    public static String m4285b(String str, String str2) {
        byte[] doFinal;
        try {
            PBEKeySpec m4289a = m4289a(str);
            int length = str2.length() / 2;
            byte[] bArr = new byte[length];
            for (int i = 0; i < length; i++) {
                int i2 = i * 2;
                bArr[i] = Integer.valueOf(str2.substring(i2, i2 + 2), 16).byteValue();
            }
            byte[] m4286b = m4286b();
            if (bArr.length <= 16) {
                doFinal = null;
            } else {
                SecretKeySpec secretKeySpec = new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(m4289a.getPassword(), Arrays.copyOf(bArr, 16), 10, 128)).getEncoded(), "AES");
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(2, secretKeySpec, new IvParameterSpec(m4286b));
                doFinal = cipher.doFinal(bArr, 16, bArr.length - 16);
            }
        } catch (Exception unused) {
        }
        if (doFinal != null) {
            String str3 = new String(doFinal);
            if (!C1037a.m4297c(str3)) {
                return null;
            }
            return str3;
        }
        throw new Exception();
    }

    /* renamed from: b */
    private static byte[] m4286b() {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 48; i += 2) {
                sb.append("AsAgAtA5A6AdAgABABACADAfAsAdAfAsAgAaAgA3A5A6=8=0".charAt(i));
            }
            return C1038a.m4292a(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
