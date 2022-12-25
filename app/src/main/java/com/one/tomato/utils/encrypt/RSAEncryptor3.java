package com.one.tomato.utils.encrypt;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes3.dex */
public class RSAEncryptor3 {
    private RSAPrivateKey privateKey;

    public RSAEncryptor3(String str, String str2) {
        try {
            loadPublicKey(str);
            loadPrivateKey(str2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String decryptWithBase64(String str) throws Exception {
        return new String(decrypt(getPrivateKey(), Base64Util.getDataBase64DecodedStr(str)));
    }

    public RSAPrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public void loadPublicKey(String str) throws Exception {
        try {
            RSAPublicKey rSAPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64Util.getDataBase64DecodedStr(str)));
        } catch (NullPointerException unused) {
            throw new Exception("公钥数据为空");
        } catch (NoSuchAlgorithmException unused2) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException unused3) {
            throw new Exception("公钥非法");
        }
    }

    public void loadPrivateKey(String str) throws Exception {
        try {
            this.privateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64Util.getDataBase64DecodedStr(str)));
        } catch (NullPointerException unused) {
            throw new Exception("私钥数据为空");
        } catch (NoSuchAlgorithmException unused2) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            throw new Exception("私钥非法");
        }
    }

    public byte[] decrypt(RSAPrivateKey rSAPrivateKey, byte[] bArr) throws Exception {
        if (rSAPrivateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(2, rSAPrivateKey);
            return cipher.doFinal(bArr);
        } catch (InvalidKeyException unused) {
            throw new Exception("解密私钥非法,请检查");
        } catch (NoSuchAlgorithmException unused2) {
            throw new Exception("无此解密算法");
        } catch (BadPaddingException unused3) {
            throw new Exception("密文数据已损坏");
        } catch (IllegalBlockSizeException unused4) {
            throw new Exception("密文长度非法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
