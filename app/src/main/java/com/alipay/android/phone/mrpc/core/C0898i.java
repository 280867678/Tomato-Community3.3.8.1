package com.alipay.android.phone.mrpc.core;

import android.content.Context;

/* renamed from: com.alipay.android.phone.mrpc.core.i */
/* loaded from: classes2.dex */
final class C0898i implements AbstractC0896g {

    /* renamed from: a */
    final /* synthetic */ C0885aa f778a;

    /* renamed from: b */
    final /* synthetic */ C0897h f779b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public C0898i(C0897h c0897h, C0885aa c0885aa) {
        this.f779b = c0897h;
        this.f778a = c0885aa;
    }

    @Override // com.alipay.android.phone.mrpc.core.AbstractC0896g
    /* renamed from: a */
    public final String mo4851a() {
        return this.f778a.m4869a();
    }

    @Override // com.alipay.android.phone.mrpc.core.AbstractC0896g
    /* renamed from: b */
    public final AbstractC0886ab mo4850b() {
        Context context;
        context = this.f779b.f777a;
        return C0902l.m4840a(context.getApplicationContext());
    }

    @Override // com.alipay.android.phone.mrpc.core.AbstractC0896g
    /* renamed from: c */
    public final C0885aa mo4849c() {
        return this.f778a;
    }

    @Override // com.alipay.android.phone.mrpc.core.AbstractC0896g
    /* renamed from: d */
    public final boolean mo4848d() {
        return this.f778a.m4866c();
    }
}
