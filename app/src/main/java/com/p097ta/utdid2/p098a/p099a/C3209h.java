package com.p097ta.utdid2.p098a.p099a;

/* renamed from: com.ta.utdid2.a.a.h */
/* loaded from: classes3.dex */
public class C3209h {
    public static String get(String str, String str2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", String.class, String.class).invoke(cls, str, str2);
        } catch (Exception unused) {
            return str2;
        }
    }
}
