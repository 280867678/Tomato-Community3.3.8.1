package com.alipay.android.phone.mrpc.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/* renamed from: com.alipay.android.phone.mrpc.core.y */
/* loaded from: classes2.dex */
public final class C0915y implements InvocationHandler {

    /* renamed from: a */
    protected AbstractC0896g f834a;

    /* renamed from: b */
    protected Class<?> f835b;

    /* renamed from: c */
    protected C0916z f836c;

    public C0915y(AbstractC0896g abstractC0896g, Class<?> cls, C0916z c0916z) {
        this.f834a = abstractC0896g;
        this.f835b = cls;
        this.f836c = c0916z;
    }

    @Override // java.lang.reflect.InvocationHandler
    public final Object invoke(Object obj, Method method, Object[] objArr) {
        return this.f836c.m4794a(method, objArr);
    }
}
