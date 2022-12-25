package com.tencent.liteav.p118c;

/* renamed from: com.tencent.liteav.c.g */
/* loaded from: classes3.dex */
public class ReverseConfig {

    /* renamed from: a */
    private static ReverseConfig f3353a;

    /* renamed from: b */
    private boolean f3354b = false;

    /* renamed from: a */
    public static ReverseConfig m2478a() {
        if (f3353a == null) {
            synchronized (ReverseConfig.class) {
                if (f3353a == null) {
                    f3353a = new ReverseConfig();
                }
            }
        }
        return f3353a;
    }

    /* renamed from: a */
    public void m2477a(boolean z) {
        this.f3354b = z;
    }

    /* renamed from: b */
    public boolean m2476b() {
        return this.f3354b;
    }

    /* renamed from: c */
    public void m2475c() {
        this.f3354b = false;
    }
}
