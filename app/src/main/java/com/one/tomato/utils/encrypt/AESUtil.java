package com.one.tomato.utils.encrypt;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.tomatolive.library.http.utils.EncryptUtil;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes3.dex */
public class AESUtil {
    @NonNull
    public static String encryptAES(String str, String str2) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        byte[] bytes = str.getBytes("UTF-8");
        SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec("16-Bytes--String".getBytes());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, secretKeySpec, ivParameterSpec);
        return Base64Util.base64EncodeData(cipher.doFinal(bytes));
    }

    @NonNull
    public static String decryptAES(String str, String str2) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        byte[] dataBase64DecodedStr = Base64Util.getDataBase64DecodedStr(str);
        SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec("16-Bytes--String".getBytes());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, secretKeySpec, ivParameterSpec);
        return new String(cipher.doFinal(dataBase64DecodedStr), "UTF-8");
    }

    public static String WTdecode(String str, String str2) throws Exception {
        SecretKey generateSecret = SecretKeyFactory.getInstance("desede").generateSecret(new DESedeKeySpec(str2.getBytes()));
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        cipher.init(2, generateSecret, new IvParameterSpec("01234567".getBytes()));
        return new String(cipher.doFinal(Base64Util.getDataBase64DecodedStr(str)), EncryptUtil.CHARSET);
    }

    public static String WTencryptAES(String str, String str2) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        if (TextUtils.isEmpty(str2)) {
            return "";
        }
        byte[] bytes = str.getBytes("UTF-8");
        SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec("16-Bytes--String".getBytes());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, secretKeySpec, ivParameterSpec);
        return Base64Util.base64EncodeData(cipher.doFinal(bytes));
    }
}
