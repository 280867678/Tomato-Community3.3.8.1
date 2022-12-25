package com.tencent.liteav.p118c;

/* renamed from: com.tencent.liteav.c.e */
/* loaded from: classes3.dex */
public class PicConfig {

    /* renamed from: b */
    private static PicConfig f3348b;

    /* renamed from: a */
    private final String f3349a = "PicConfig";

    /* renamed from: c */
    private int f3350c;

    /* renamed from: a */
    public static PicConfig m2485a() {
        if (f3348b == null) {
            synchronized (PicConfig.class) {
                if (f3348b == null) {
                    f3348b = new PicConfig();
                }
            }
        }
        return f3348b;
    }

    /* renamed from: a */
    public void m2484a(int i) {
        this.f3350c = i;
    }

    /* renamed from: b */
    public int m2483b() {
        return this.f3350c;
    }
}
