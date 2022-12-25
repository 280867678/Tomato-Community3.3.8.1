package com.gen.p059mh.webapp_extensions.utils;

import android.util.Base64;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* renamed from: com.gen.mh.webapp_extensions.utils.CryptoHelper */
/* loaded from: classes2.dex */
public class CryptoHelper {
    private String appKey;

    /* renamed from: iv */
    private String f1293iv;
    private String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKzfcbQHLCXqVRrcBOj8H+4ccWd6rwEc+p1LDfMQCCz9CTguRQOh15/pMwqkQW8EWX9FmQkJOM6/vENcUcP7emMCAwEAAQ==";

    public CryptoHelper(long j) {
        this.appKey = "a70f318a787706d612b83336acd4f297";
        this.f1293iv = "a396202596eacb41";
        String valueOf = String.valueOf(j);
        String generateString = generateString(new Random(), "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", 12);
        this.appKey = valueOf.substring(valueOf.length() - 4) + generateString;
        this.f1293iv = this.appKey;
    }

    public String generateString(Random random, String str, int i) {
        char[] cArr = new char[i];
        for (int i2 = 0; i2 < i; i2++) {
            cArr[i2] = str.charAt(random.nextInt(str.length()));
        }
        return new String(cArr);
    }

    public String cryptoBody(String str) {
        return EncodeUtils.base64Encode2String(EncryptUtils.encryptAES(str.getBytes(), this.appKey.getBytes(), "AES/CBC/PKCS5Padding", this.f1293iv.getBytes()));
    }

    public String decryptBody(String str) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(this.f1293iv.getBytes("UTF-8"));
            SecretKeySpec secretKeySpec = new SecretKeySpec(this.appKey.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(2, secretKeySpec, ivParameterSpec);
            return new String(cipher.doFinal(Base64.decode(str, 2)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String rsaEncrypt() throws Exception {
        byte[] decode = Base64.decode(this.publicKey, 2);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(1, (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decode)));
        return Base64.encodeToString(cipher.doFinal(this.appKey.getBytes("UTF-8")), 2);
    }
}
