package org.xutils.p148db.table;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import org.xutils.common.util.LogUtil;
import org.xutils.p148db.converter.ColumnConverterFactory;

/* renamed from: org.xutils.db.table.ColumnUtils */
/* loaded from: classes4.dex */
public final class ColumnUtils {
    private static final HashSet<Class<?>> BOOLEAN_TYPES = new HashSet<>(2);
    private static final HashSet<Class<?>> INTEGER_TYPES = new HashSet<>(2);
    private static final HashSet<Class<?>> AUTO_INCREMENT_TYPES = new HashSet<>(4);

    private ColumnUtils() {
    }

    static {
        BOOLEAN_TYPES.add(Boolean.TYPE);
        BOOLEAN_TYPES.add(Boolean.class);
        INTEGER_TYPES.add(Integer.TYPE);
        INTEGER_TYPES.add(Integer.class);
        AUTO_INCREMENT_TYPES.addAll(INTEGER_TYPES);
        AUTO_INCREMENT_TYPES.add(Long.TYPE);
        AUTO_INCREMENT_TYPES.add(Long.class);
    }

    public static boolean isAutoIdType(Class<?> cls) {
        return AUTO_INCREMENT_TYPES.contains(cls);
    }

    public static boolean isInteger(Class<?> cls) {
        return INTEGER_TYPES.contains(cls);
    }

    public static boolean isBoolean(Class<?> cls) {
        return BOOLEAN_TYPES.contains(cls);
    }

    public static Object convert2DbValueIfNeeded(Object obj) {
        return obj != null ? ColumnConverterFactory.getColumnConverter(obj.getClass()).fieldValue2DbValue(obj) : obj;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Method findGetMethod(Class<?> cls, Field field) {
        Method method = null;
        if (Object.class.equals(cls)) {
            return null;
        }
        String name = field.getName();
        if (isBoolean(field.getType())) {
            method = findBooleanGetMethod(cls, name);
        }
        if (method == null) {
            String str = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
            try {
                method = cls.getDeclaredMethod(str, new Class[0]);
            } catch (NoSuchMethodException unused) {
                LogUtil.m46d(cls.getName() + "#" + str + " not exist");
            }
        }
        return method == null ? findGetMethod(cls.getSuperclass(), field) : method;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Method findSetMethod(Class<?> cls, Field field) {
        Method method = null;
        if (Object.class.equals(cls)) {
            return null;
        }
        String name = field.getName();
        Class<?> type = field.getType();
        if (isBoolean(type)) {
            method = findBooleanSetMethod(cls, name, type);
        }
        if (method == null) {
            String str = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
            try {
                method = cls.getDeclaredMethod(str, type);
            } catch (NoSuchMethodException unused) {
                LogUtil.m46d(cls.getName() + "#" + str + " not exist");
            }
        }
        return method == null ? findSetMethod(cls.getSuperclass(), field) : method;
    }

    private static Method findBooleanGetMethod(Class<?> cls, String str) {
        if (!str.startsWith("is")) {
            str = "is" + str.substring(0, 1).toUpperCase() + str.substring(1);
        }
        try {
            return cls.getDeclaredMethod(str, new Class[0]);
        } catch (NoSuchMethodException unused) {
            LogUtil.m46d(cls.getName() + "#" + str + " not exist");
            return null;
        }
    }

    private static Method findBooleanSetMethod(Class<?> cls, String str, Class<?> cls2) {
        String str2;
        if (str.startsWith("is")) {
            str2 = "set" + str.substring(2, 3).toUpperCase() + str.substring(3);
        } else {
            str2 = "set" + str.substring(0, 1).toUpperCase() + str.substring(1);
        }
        try {
            return cls.getDeclaredMethod(str2, cls2);
        } catch (NoSuchMethodException unused) {
            LogUtil.m46d(cls.getName() + "#" + str2 + " not exist");
            return null;
        }
    }
}
