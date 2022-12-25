package com.alipay.android.phone.mrpc.core;

import java.lang.reflect.Proxy;

/* renamed from: com.alipay.android.phone.mrpc.core.x */
/* loaded from: classes2.dex */
public final class C0914x {

    /* renamed from: a */
    private AbstractC0896g f832a;

    /* renamed from: b */
    private C0916z f833b = new C0916z(this);

    public C0914x(AbstractC0896g abstractC0896g) {
        this.f832a = abstractC0896g;
    }

    /* renamed from: a */
    public final AbstractC0896g m4796a() {
        return this.f832a;
    }

    /* renamed from: a */
    public final <T> T m4795a(Class<T> cls) {
        return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new C0915y(this.f832a, cls, this.f833b));
    }
}
