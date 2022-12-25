package com.tomatolive.library.http.utils;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/* loaded from: classes3.dex */
public class EncryptUtil {
    public static final String CHARSET = "utf-8";

    /* renamed from: iv */
    private static final String f5825iv = "01234567";

    private EncryptUtil() {
    }

    public static String DESEncrypt(String str, String str2) throws Exception {
        return encode(str, str2);
    }

    public static String DESDecrypt(String str, String str2) throws Exception {
        return decode(str, str2);
    }

    public static String RSAEncrypt(String str, String str2) throws Exception {
        return encodeToString(encrypt(str2.getBytes(CHARSET), getPublicKey(str), 2048, 11, "RSA/ECB/PKCS1Padding"));
    }

    public static String RSADecrypt(String str, String str2) throws Exception {
        return new String(decrypt(decode(str2), getPraivateKey(str), 2048, 11, "RSA/ECB/PKCS1Padding"), CHARSET);
    }

    private static String encode(String str, String str2) throws Exception {
        SecretKey generateSecret = SecretKeyFactory.getInstance("desede").generateSecret(new DESedeKeySpec(str.getBytes()));
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        cipher.init(1, generateSecret, new IvParameterSpec(f5825iv.getBytes()));
        return encodeToString(cipher.doFinal(str2.getBytes(CHARSET)));
    }

    private static String decode(String str, String str2) throws Exception {
        SecretKey generateSecret = SecretKeyFactory.getInstance("desede").generateSecret(new DESedeKeySpec(str.getBytes()));
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        cipher.init(2, generateSecret, new IvParameterSpec(f5825iv.getBytes()));
        return new String(cipher.doFinal(decode(str2)), CHARSET);
    }

    private static byte[] decrypt(byte[] bArr, PrivateKey privateKey, int i, int i2, String str) throws Exception {
        int i3 = i / 8;
        int i4 = i3 - i2;
        int length = bArr.length / i3;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            try {
                Cipher cipher = Cipher.getInstance(str);
                cipher.init(2, privateKey);
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream(length * i4);
                for (int i5 = 0; i5 < bArr.length; i5 += i3) {
                    try {
                        int length2 = bArr.length - i5;
                        if (length2 > i3) {
                            length2 = i3;
                        }
                        byteArrayOutputStream2.write(cipher.doFinal(bArr, i5, length2));
                    } catch (Exception e) {
                        e = e;
                        byteArrayOutputStream = byteArrayOutputStream2;
                        throw new Exception("DEENCRYPT ERROR:", e);
                    } catch (Throwable th) {
                        th = th;
                        byteArrayOutputStream = byteArrayOutputStream2;
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (Exception unused) {
                            }
                        }
                        throw th;
                    }
                }
                byteArrayOutputStream2.flush();
                byte[] byteArray = byteArrayOutputStream2.toByteArray();
                try {
                    byteArrayOutputStream2.close();
                } catch (Exception unused2) {
                }
                return byteArray;
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static byte[] encrypt(byte[] bArr, PublicKey publicKey, int i, int i2, String str) throws Exception {
        int i3 = i / 8;
        int i4 = i3 - i2;
        int length = bArr.length / i4;
        if (bArr.length % i4 != 0) {
            length++;
        }
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            try {
                Cipher cipher = Cipher.getInstance(str);
                cipher.init(1, publicKey);
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream(length * i3);
                for (int i5 = 0; i5 < bArr.length; i5 += i4) {
                    try {
                        int length2 = bArr.length - i5;
                        if (length2 > i4) {
                            length2 = i4;
                        }
                        byteArrayOutputStream2.write(cipher.doFinal(bArr, i5, length2));
                    } catch (Exception e) {
                        e = e;
                        byteArrayOutputStream = byteArrayOutputStream2;
                        throw new Exception("ENCRYPT ERROR:", e);
                    } catch (Throwable th) {
                        th = th;
                        byteArrayOutputStream = byteArrayOutputStream2;
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (Exception unused) {
                            }
                        }
                        throw th;
                    }
                }
                byteArrayOutputStream2.flush();
                byte[] byteArray = byteArrayOutputStream2.toByteArray();
                try {
                    byteArrayOutputStream2.close();
                } catch (Exception unused2) {
                }
                return byteArray;
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static PrivateKey getPraivateKey(String str) throws Exception {
        return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decode(str)));
    }

    private static PublicKey getPublicKey(String str) throws Exception {
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decode(str)));
    }

    private static String encodeToString(byte[] bArr) {
        return Base64Util.encode(bArr);
    }

    private static byte[] decode(String str) throws Exception {
        return Base64Util.decode(str, "GBK");
    }
}
