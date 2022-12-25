package com.alipay.sdk.util;

/* renamed from: com.alipay.sdk.util.d */
/* loaded from: classes2.dex */
public enum EnumC0997d {
    WIFI(0, "WIFI"),
    NETWORK_TYPE_1(1, "unicom2G"),
    NETWORK_TYPE_2(2, "mobile2G"),
    NETWORK_TYPE_4(4, "telecom2G"),
    NETWORK_TYPE_5(5, "telecom3G"),
    NETWORK_TYPE_6(6, "telecom3G"),
    NETWORK_TYPE_12(12, "telecom3G"),
    NETWORK_TYPE_8(8, "unicom3G"),
    NETWORK_TYPE_3(3, "unicom3G"),
    NETWORK_TYPE_13(13, "LTE"),
    NETWORK_TYPE_11(11, "IDEN"),
    NETWORK_TYPE_9(9, "HSUPA"),
    NETWORK_TYPE_10(10, "HSPA"),
    NETWORK_TYPE_15(15, "HSPAP"),
    NONE(-1, "none");
    

    /* renamed from: p */
    private int f1052p;

    /* renamed from: q */
    private String f1053q;

    EnumC0997d(int i, String str) {
        this.f1052p = i;
        this.f1053q = str;
    }

    /* renamed from: a */
    public final int m4430a() {
        return this.f1052p;
    }

    /* renamed from: b */
    public final String m4428b() {
        return this.f1053q;
    }

    /* renamed from: a */
    public static EnumC0997d m4429a(int i) {
        EnumC0997d[] values;
        for (EnumC0997d enumC0997d : values()) {
            if (enumC0997d.m4430a() == i) {
                return enumC0997d;
            }
        }
        return NONE;
    }
}
