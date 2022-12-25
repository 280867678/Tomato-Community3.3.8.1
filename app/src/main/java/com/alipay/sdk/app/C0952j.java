package com.alipay.sdk.app;

/* renamed from: com.alipay.sdk.app.j */
/* loaded from: classes2.dex */
public class C0952j {

    /* renamed from: a */
    private static boolean f934a;

    /* renamed from: b */
    private static String f935b;

    /* renamed from: a */
    public static void m4646a(String str) {
        f935b = str;
    }

    /* renamed from: a */
    public static String m4648a() {
        return f935b;
    }

    /* renamed from: b */
    public static boolean m4644b() {
        return f934a;
    }

    /* renamed from: a */
    public static void m4645a(boolean z) {
        f934a = z;
    }

    /* renamed from: c */
    public static String m4643c() {
        EnumC0953k m4636b = EnumC0953k.m4636b(EnumC0953k.CANCELED.m4640a());
        return m4647a(m4636b.m4640a(), m4636b.m4637b(), "");
    }

    /* renamed from: d */
    public static String m4642d() {
        EnumC0953k m4636b = EnumC0953k.m4636b(EnumC0953k.DOUBLE_REQUEST.m4640a());
        return m4647a(m4636b.m4640a(), m4636b.m4637b(), "");
    }

    /* renamed from: e */
    public static String m4641e() {
        EnumC0953k m4636b = EnumC0953k.m4636b(EnumC0953k.PARAMS_ERROR.m4640a());
        return m4647a(m4636b.m4640a(), m4636b.m4637b(), "");
    }

    /* renamed from: a */
    public static String m4647a(int i, String str, String str2) {
        return "resultStatus={" + i + "};memo={" + str + "};result={" + str2 + "}";
    }
}
