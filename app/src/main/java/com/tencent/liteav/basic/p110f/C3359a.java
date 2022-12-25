package com.tencent.liteav.basic.p110f;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;

/* compiled from: RSAUtils.java */
/* renamed from: com.tencent.liteav.basic.f.a */
/* loaded from: classes3.dex */
public final class C3359a {

    /* renamed from: a */
    private static String f2686a = "RSA";

    /* renamed from: a */
    public static byte[] m2989a(byte[] bArr, PrivateKey privateKey) throws Exception {
        byte[] doFinal;
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(2, privateKey);
        int length = bArr.length;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i = 0;
        while (true) {
            int i2 = length - i;
            if (i2 > 0) {
                if (i2 >= cipher.getBlockSize()) {
                    doFinal = cipher.doFinal(bArr, i, cipher.getBlockSize());
                } else {
                    doFinal = cipher.doFinal(bArr, i, i2);
                }
                byteArrayOutputStream.write(doFinal);
                i += cipher.getBlockSize();
            } else {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return byteArray;
            }
        }
    }

    /* renamed from: a */
    public static PrivateKey m2990a(byte[] bArr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return KeyFactory.getInstance(f2686a).generatePrivate(new PKCS8EncodedKeySpec(bArr));
    }
}
