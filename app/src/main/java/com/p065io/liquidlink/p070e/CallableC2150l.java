package com.p065io.liquidlink.p070e;

import com.p089pm.liquidlink.p091b.C3049b;
import com.p089pm.liquidlink.p091b.EnumC3050c;
import java.util.concurrent.Callable;

/* renamed from: com.io.liquidlink.e.l */
/* loaded from: classes3.dex */
class CallableC2150l implements Callable {

    /* renamed from: a */
    final /* synthetic */ long f1420a;

    /* renamed from: b */
    final /* synthetic */ HandlerC2139a f1421b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CallableC2150l(HandlerC2139a handlerC2139a, long j) {
        this.f1421b = handlerC2139a;
        this.f1420a = j;
    }

    @Override // java.util.concurrent.Callable
    /* renamed from: a */
    public C3049b call() {
        if (this.f1421b.f1427d.m4105a(this.f1420a)) {
            String m4052b = this.f1421b.f1431h.m4052b();
            C3049b c3049b = new C3049b(EnumC3050c.SUCCESS, 0);
            c3049b.m3741c(m4052b);
            this.f1421b.m4001d(c3049b.m3738e());
            return c3049b;
        }
        String m4050c = this.f1421b.f1431h.m4050c();
        C3049b c3049b2 = new C3049b(EnumC3050c.ERROR, -12);
        c3049b2.m3743b("初始化时错误：" + m4050c);
        return c3049b2;
    }
}
