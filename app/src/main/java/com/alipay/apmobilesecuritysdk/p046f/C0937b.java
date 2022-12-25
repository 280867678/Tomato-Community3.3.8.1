package com.alipay.apmobilesecuritysdk.p046f;

import java.util.LinkedList;

/* renamed from: com.alipay.apmobilesecuritysdk.f.b */
/* loaded from: classes2.dex */
public final class C0937b {

    /* renamed from: a */
    private static C0937b f869a = new C0937b();

    /* renamed from: b */
    private Thread f870b = null;

    /* renamed from: c */
    private LinkedList<Runnable> f871c = new LinkedList<>();

    /* renamed from: a */
    public static C0937b m4698a() {
        return f869a;
    }

    /* renamed from: a */
    public final synchronized void m4696a(Runnable runnable) {
        this.f871c.add(runnable);
        if (this.f870b == null) {
            this.f870b = new Thread(new RunnableC0938c(this));
            this.f870b.start();
        }
    }
}
