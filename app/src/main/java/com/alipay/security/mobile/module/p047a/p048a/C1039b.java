package com.alipay.security.mobile.module.p047a.p048a;

import com.alipay.security.mobile.module.p047a.C1037a;
import java.security.MessageDigest;

/* renamed from: com.alipay.security.mobile.module.a.a.b */
/* loaded from: classes2.dex */
public final class C1039b {
    /* renamed from: a */
    public static String m4291a(String str) {
        try {
            if (C1037a.m4303a(str)) {
                return null;
            }
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(str.getBytes("UTF-8"));
            byte[] digest = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                sb.append(String.format("%02x", Byte.valueOf(digest[i])));
            }
            return sb.toString();
        } catch (Exception unused) {
            return null;
        }
    }
}
