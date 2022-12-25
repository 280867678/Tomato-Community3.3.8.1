package net.vidageek.mirror.provider;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/* loaded from: classes4.dex */
public interface ClassReflectionProvider<T> {
    List<Constructor<T>> reflectAllConstructors();

    List<Field> reflectAllFields();

    List<Method> reflectAllMethods();

    Class<T> reflectClass();

    Constructor<T> reflectConstructor(Class<?>[] clsArr);

    Field reflectField(String str);

    Method reflectMethod(String str, Class<?>[] clsArr);
}
