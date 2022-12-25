package com.p065io.liquidlink.p073h;

import com.p089pm.liquidlink.p091b.C3049b;

/* renamed from: com.io.liquidlink.h.c */
/* loaded from: classes3.dex */
class RunnableC2172c implements Runnable {

    /* renamed from: a */
    final /* synthetic */ C3049b f1487a;

    /* renamed from: b */
    final /* synthetic */ RunnableC2171b f1488b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RunnableC2172c(RunnableC2171b runnableC2171b, C3049b c3049b) {
        this.f1488b = runnableC2171b;
        this.f1487a = c3049b;
    }

    @Override // java.lang.Runnable
    public void run() {
        AbstractC2170a abstractC2170a;
        abstractC2170a = this.f1488b.f1484c;
        abstractC2170a.mo3924a(this.f1487a);
    }
}
