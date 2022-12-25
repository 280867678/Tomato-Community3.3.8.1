package com.alipay.security.mobile.module.p051d;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.alipay.security.mobile.module.d.c */
/* loaded from: classes2.dex */
public final class RunnableC1054c implements Runnable {

    /* renamed from: a */
    final /* synthetic */ C1053b f1133a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RunnableC1054c(C1053b c1053b) {
        this.f1133a = c1053b;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.f1133a.m4207b();
        } catch (Exception e) {
            C1055d.m4204a(e);
        }
    }
}
