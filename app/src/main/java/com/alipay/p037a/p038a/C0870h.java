package com.alipay.p037a.p038a;

import com.alipay.p037a.p039b.C0875a;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.json.alipay.C5328b;

/* renamed from: com.alipay.a.a.h */
/* loaded from: classes2.dex */
public final class C0870h implements AbstractC0871i, AbstractC0872j {
    /* renamed from: a */
    private static Map<Object, Object> m4879a(Type type) {
        while (type != Properties.class) {
            if (type == Hashtable.class) {
                return new Hashtable();
            }
            if (type == IdentityHashMap.class) {
                return new IdentityHashMap();
            }
            if (type == SortedMap.class || type == TreeMap.class) {
                return new TreeMap();
            }
            if (type == ConcurrentMap.class || type == ConcurrentHashMap.class) {
                return new ConcurrentHashMap();
            }
            if (type == Map.class || type == HashMap.class) {
                return new HashMap();
            }
            if (type == LinkedHashMap.class) {
                return new LinkedHashMap();
            }
            if (!(type instanceof ParameterizedType)) {
                Class cls = (Class) type;
                if (cls.isInterface()) {
                    throw new IllegalArgumentException("unsupport type " + type);
                }
                try {
                    return (Map) cls.newInstance();
                } catch (Exception e) {
                    throw new IllegalArgumentException("unsupport type " + type, e);
                }
            }
            type = ((ParameterizedType) type).getRawType();
        }
        return new Properties();
    }

    @Override // com.alipay.p037a.p038a.AbstractC0872j
    /* renamed from: a */
    public final Object mo4877a(Object obj) {
        TreeMap treeMap = new TreeMap();
        for (Map.Entry entry : ((Map) obj).entrySet()) {
            if (!(entry.getKey() instanceof String)) {
                throw new IllegalArgumentException("Map key must be String!");
            }
            treeMap.put((String) entry.getKey(), C0868f.m4880b(entry.getValue()));
        }
        return treeMap;
    }

    @Override // com.alipay.p037a.p038a.AbstractC0871i
    /* renamed from: a */
    public final Object mo4876a(Object obj, Type type) {
        if (!obj.getClass().equals(C5328b.class)) {
            return null;
        }
        C5328b c5328b = (C5328b) obj;
        Map<Object, Object> m4879a = m4879a(type);
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalArgumentException("Deserialize Map must be Generics!");
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type type2 = parameterizedType.getActualTypeArguments()[0];
        Type type3 = parameterizedType.getActualTypeArguments()[1];
        if (String.class != type2) {
            throw new IllegalArgumentException("Deserialize Map Key must be String.class");
        }
        Iterator m65a = c5328b.m65a();
        while (m65a.hasNext()) {
            String str = (String) m65a.next();
            m4879a.put(str, C0875a.m4875a((Class<?>) ((Class) type3)) ? c5328b.m63a(str) : C0867e.m4883a(c5328b.m63a(str), type3));
        }
        return m4879a;
    }

    @Override // com.alipay.p037a.p038a.AbstractC0871i, com.alipay.p037a.p038a.AbstractC0872j
    /* renamed from: a */
    public final boolean mo4878a(Class<?> cls) {
        return Map.class.isAssignableFrom(cls);
    }
}
