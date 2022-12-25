package com.youdao.sdk.app.other;

import java.security.MessageDigest;
import java.util.concurrent.atomic.AtomicLong;

/* renamed from: com.youdao.sdk.app.other.z */
/* loaded from: classes4.dex */
public class C5177z {
    static {
        new AtomicLong(1L);
    }

    /* renamed from: a */
    public static String m170a(String str) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = str.getBytes("UTF-8");
            messageDigest.update(bytes, 0, bytes.length);
            byte[] digest = messageDigest.digest();
            int length = digest.length;
            for (int i = 0; i < length; i++) {
                sb.append(String.format("%02X", Byte.valueOf(digest[i])));
            }
            return sb.toString().toLowerCase();
        } catch (Exception unused) {
            return "";
        }
    }
}
