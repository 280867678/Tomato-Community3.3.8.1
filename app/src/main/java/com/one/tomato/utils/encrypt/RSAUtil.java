package com.one.tomato.utils.encrypt;

/* loaded from: classes3.dex */
public class RSAUtil {
    public static String RSAdecrypt(String str, String str2, String str3) {
        try {
            return new RSAEncryptor3(str, str2).decryptWithBase64(str3);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
