package com.alipay.p037a.p038a;

import com.alipay.p037a.p039b.C0875a;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;
import org.json.alipay.C5327a;

/* renamed from: com.alipay.a.a.b */
/* loaded from: classes2.dex */
public final class C0864b implements AbstractC0871i, AbstractC0872j {
    /* renamed from: a */
    private static Collection<Object> m4884a(Class<?> cls, Type type) {
        if (cls == AbstractCollection.class) {
            return new ArrayList();
        }
        if (cls.isAssignableFrom(HashSet.class)) {
            return new HashSet();
        }
        if (cls.isAssignableFrom(LinkedHashSet.class)) {
            return new LinkedHashSet();
        }
        if (cls.isAssignableFrom(TreeSet.class)) {
            return new TreeSet();
        }
        if (cls.isAssignableFrom(ArrayList.class)) {
            return new ArrayList();
        }
        if (cls.isAssignableFrom(EnumSet.class)) {
            return EnumSet.noneOf(type instanceof ParameterizedType ? ((ParameterizedType) type).getActualTypeArguments()[0] : Object.class);
        }
        try {
            return (Collection) cls.newInstance();
        } catch (Exception unused) {
            throw new IllegalArgumentException("create instane error, class " + cls.getName());
        }
    }

    @Override // com.alipay.p037a.p038a.AbstractC0872j
    /* renamed from: a */
    public final Object mo4877a(Object obj) {
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : (Iterable) obj) {
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
        Collection<Object> m4884a = m4884a(C0875a.m4874a(type), type);
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalArgumentException("Does not support the implement for generics.");
        }
        Type type2 = ((ParameterizedType) type).getActualTypeArguments()[0];
        for (int i = 0; i < c5327a.m68a(); i++) {
            m4884a.add(C0867e.m4883a(c5327a.m67a(i), type2));
        }
        return m4884a;
    }

    @Override // com.alipay.p037a.p038a.AbstractC0871i, com.alipay.p037a.p038a.AbstractC0872j
    /* renamed from: a */
    public final boolean mo4878a(Class<?> cls) {
        return Collection.class.isAssignableFrom(cls);
    }
}
