package com.alipay.p037a.p038a;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import org.json.alipay.C5327a;

/* renamed from: com.alipay.a.a.k */
/* loaded from: classes2.dex */
public final class C0873k implements AbstractC0871i {
    @Override // com.alipay.p037a.p038a.AbstractC0871i
    /* renamed from: a */
    public final Object mo4876a(Object obj, Type type) {
        if (!obj.getClass().equals(C5327a.class)) {
            return null;
        }
        C5327a c5327a = (C5327a) obj;
        HashSet hashSet = new HashSet();
        Class cls = type instanceof ParameterizedType ? ((ParameterizedType) type).getActualTypeArguments()[0] : Object.class;
        for (int i = 0; i < c5327a.m68a(); i++) {
            hashSet.add(C0867e.m4883a(c5327a.m67a(i), cls));
        }
        return hashSet;
    }

    @Override // com.alipay.p037a.p038a.AbstractC0871i, com.alipay.p037a.p038a.AbstractC0872j
    /* renamed from: a */
    public final boolean mo4878a(Class<?> cls) {
        return Set.class.isAssignableFrom(cls);
    }
}
