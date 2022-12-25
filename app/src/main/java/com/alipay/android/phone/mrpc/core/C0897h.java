package com.alipay.android.phone.mrpc.core;

import android.content.Context;

/* renamed from: com.alipay.android.phone.mrpc.core.h */
/* loaded from: classes2.dex */
public final class C0897h extends AbstractC0913w {

    /* renamed from: a */
    private Context f777a;

    public C0897h(Context context) {
        this.f777a = context;
    }

    @Override // com.alipay.android.phone.mrpc.core.AbstractC0913w
    /* renamed from: a */
    public final <T> T mo4797a(Class<T> cls, C0885aa c0885aa) {
        return (T) new C0914x(new C0898i(this, c0885aa)).m4795a(cls);
    }
}
