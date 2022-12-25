package com.p076mh.webappStart.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* renamed from: com.mh.webappStart.util.ReflectManger */
/* loaded from: classes3.dex */
public class ReflectManger {
    public static Object invokeMethod(Class cls, Object obj, String str, Class[] clsArr, Object[] objArr) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
        declaredMethod.setAccessible(true);
        return declaredMethod.invoke(obj, objArr);
    }
}
