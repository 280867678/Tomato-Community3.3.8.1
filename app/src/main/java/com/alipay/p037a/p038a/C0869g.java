package com.alipay.p037a.p038a;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.TreeMap;
import org.json.alipay.C5328b;

/* renamed from: com.alipay.a.a.g */
/* loaded from: classes2.dex */
public final class C0869g implements AbstractC0871i, AbstractC0872j {
    @Override // com.alipay.p037a.p038a.AbstractC0872j
    /* renamed from: a */
    public final Object mo4877a(Object obj) {
        TreeMap treeMap = new TreeMap();
        Class<?> cls = obj.getClass();
        while (true) {
            Field[] declaredFields = cls.getDeclaredFields();
            if (!cls.equals(Object.class)) {
                if (declaredFields != null && declaredFields.length > 0) {
                    for (Field field : declaredFields) {
                        Object obj2 = null;
                        if (field != null && obj != null && !"this$0".equals(field.getName())) {
                            boolean isAccessible = field.isAccessible();
                            field.setAccessible(true);
                            Object obj3 = field.get(obj);
                            if (obj3 != null) {
                                field.setAccessible(isAccessible);
                                obj2 = C0868f.m4880b(obj3);
                            }
                        }
                        if (obj2 != null) {
                            treeMap.put(field.getName(), obj2);
                        }
                    }
                }
                cls = cls.getSuperclass();
            } else {
                return treeMap;
            }
        }
    }

    @Override // com.alipay.p037a.p038a.AbstractC0871i
    /* renamed from: a */
    public final Object mo4876a(Object obj, Type type) {
        if (!obj.getClass().equals(C5328b.class)) {
            return null;
        }
        C5328b c5328b = (C5328b) obj;
        Class cls = (Class) type;
        Object newInstance = cls.newInstance();
        while (!cls.equals(Object.class)) {
            Field[] declaredFields = cls.getDeclaredFields();
            if (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    String name = field.getName();
                    Type genericType = field.getGenericType();
                    if (c5328b.m61b(name)) {
                        field.setAccessible(true);
                        field.set(newInstance, C0867e.m4883a(c5328b.m63a(name), genericType));
                    }
                }
            }
            cls = cls.getSuperclass();
        }
        return newInstance;
    }

    @Override // com.alipay.p037a.p038a.AbstractC0871i, com.alipay.p037a.p038a.AbstractC0872j
    /* renamed from: a */
    public final boolean mo4878a(Class<?> cls) {
        return true;
    }
}
