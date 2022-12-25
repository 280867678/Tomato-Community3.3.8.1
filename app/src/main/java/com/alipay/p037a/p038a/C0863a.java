package com.alipay.p037a.p038a;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import org.json.alipay.C5327a;

/* renamed from: com.alipay.a.a.a */
/* loaded from: classes2.dex */
public final class C0863a implements AbstractC0871i, AbstractC0872j {
    @Override // com.alipay.p037a.p038a.AbstractC0872j
    /* renamed from: a */
    public final Object mo4877a(Object obj) {
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : (Object[]) obj) {
            arrayList.add(C0868f.m4880b(obj2));
        }
        return arrayList;
    }

    @Override // com.alipay.p037a.p038a.AbstractC0871i
    /* renamed from: a */
    public final Object mo4876a(Object obj, Type type) {
        if (!obj.getClass().equals(C5327a.class)) {
            return null;
        }
        C5327a c5327a = (C5327a) obj;
        if (type instanceof GenericArrayType) {
            throw new IllegalArgumentException("Does not support generic array!");
        }
        Class<?> componentType = ((Class) type).getComponentType();
        int m68a = c5327a.m68a();
        Object newInstance = Array.newInstance(componentType, m68a);
        for (int i = 0; i < m68a; i++) {
            Array.set(newInstance, i, C0867e.m4883a(c5327a.m67a(i), componentType));
        }
        return newInstance;
    }

    @Override // com.alipay.p037a.p038a.AbstractC0871i, com.alipay.p037a.p038a.AbstractC0872j
    /* renamed from: a */
    public final boolean mo4878a(Class<?> cls) {
        return cls.isArray();
    }
}
