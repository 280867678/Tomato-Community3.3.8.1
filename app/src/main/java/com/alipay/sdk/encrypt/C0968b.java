package com.alipay.sdk.encrypt;

import com.alipay.sdk.util.C0996c;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/* renamed from: com.alipay.sdk.encrypt.b */
/* loaded from: classes2.dex */
public class C0968b {
    /* renamed from: a */
    public static String m4551a(String str, String str2) {
        return m4552a(1, str, str2);
    }

    /* renamed from: b */
    public static String m4550b(String str, String str2) {
        return m4552a(2, str, str2);
    }

    /* renamed from: a */
    public static String m4552a(int i, String str, String str2) {
        byte[] bytes;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(i, secretKeySpec);
            if (i == 2) {
                bytes = C0967a.m4557a(str);
            } else {
                bytes = str.getBytes("UTF-8");
            }
            byte[] doFinal = cipher.doFinal(bytes);
            if (i == 2) {
                return new String(doFinal);
            }
            return C0967a.m4556a(doFinal);
        } catch (Exception e) {
            C0996c.m4436a(e);
            return null;
        }
    }
}
