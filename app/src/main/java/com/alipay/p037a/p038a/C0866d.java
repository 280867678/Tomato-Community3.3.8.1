package com.alipay.p037a.p038a;

import java.lang.reflect.Type;

/* renamed from: com.alipay.a.a.d */
/* loaded from: classes2.dex */
public final class C0866d implements AbstractC0871i, AbstractC0872j {
    @Override // com.alipay.p037a.p038a.AbstractC0872j
    /* renamed from: a */
    public final Object mo4877a(Object obj) {
        return ((Enum) obj).name();
    }

    @Override // com.alipay.p037a.p038a.AbstractC0871i
    /* renamed from: a */
    public final Object mo4876a(Object obj, Type type) {
        return Enum.valueOf((Class) type, obj.toString());
    }

    @Override // com.alipay.p037a.p038a.AbstractC0871i, com.alipay.p037a.p038a.AbstractC0872j
    /* renamed from: a */
    public final boolean mo4878a(Class<?> cls) {
        return Enum.class.isAssignableFrom(cls);
    }
}
