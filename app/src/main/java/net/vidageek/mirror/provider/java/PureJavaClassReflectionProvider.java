package net.vidageek.mirror.provider.java;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.vidageek.mirror.exception.ReflectionProviderException;
import net.vidageek.mirror.matcher.ClassArrayMatcher;
import net.vidageek.mirror.matcher.MatchType;
import net.vidageek.mirror.provider.ClassReflectionProvider;

/* loaded from: classes4.dex */
public final class PureJavaClassReflectionProvider<T> implements ClassReflectionProvider<T> {
    private Class<T> clazz;

    public PureJavaClassReflectionProvider(String str) {
        try {
            this.clazz = (Class<T>) Class.forName(str, false, PureJavaClassReflectionProvider.class.getClassLoader());
        } catch (ClassNotFoundException e) {
            this.clazz = (Class<T>) FixedType.fromValue(str);
            if (this.clazz != null) {
                return;
            }
            throw new ReflectionProviderException("class " + str + " could not be found.", e);
        }
    }

    public PureJavaClassReflectionProvider(Class<T> cls) {
        this.clazz = cls;
    }

    @Override // net.vidageek.mirror.provider.ClassReflectionProvider
    public Class<T> reflectClass() {
        return this.clazz;
    }

    @Override // net.vidageek.mirror.provider.ClassReflectionProvider
    public List<Field> reflectAllFields() {
        ArrayList arrayList = new ArrayList();
        for (Class<T> cls = this.clazz; cls != null; cls = cls.getSuperclass()) {
            arrayList.addAll(Arrays.asList(cls.getDeclaredFields()));
            for (Class<?> cls2 : cls.getInterfaces()) {
                arrayList.addAll(Arrays.asList(cls2.getFields()));
            }
        }
        return arrayList;
    }

    @Override // net.vidageek.mirror.provider.ClassReflectionProvider
    public List<Method> reflectAllMethods() {
        ArrayList arrayList = new ArrayList();
        for (Class<T> cls = this.clazz; cls != null; cls = cls.getSuperclass()) {
            arrayList.addAll(Arrays.asList(cls.getDeclaredMethods()));
        }
        return arrayList;
    }

    @Override // net.vidageek.mirror.provider.ClassReflectionProvider
    public List<Constructor<T>> reflectAllConstructors() {
        return Arrays.asList(this.clazz.getDeclaredConstructors());
    }

    @Override // net.vidageek.mirror.provider.ClassReflectionProvider
    public Constructor<T> reflectConstructor(Class<?>[] clsArr) {
        ClassArrayMatcher classArrayMatcher = new ClassArrayMatcher(clsArr);
        Constructor<T> constructor = null;
        for (Constructor<T> constructor2 : reflectAllConstructors()) {
            MatchType match = classArrayMatcher.match(constructor2.getParameterTypes());
            if (MatchType.PERFECT.equals(match)) {
                return constructor2;
            }
            if (MatchType.MATCH.equals(match)) {
                constructor = constructor2;
            }
        }
        return constructor;
    }

    @Override // net.vidageek.mirror.provider.ClassReflectionProvider
    public Field reflectField(String str) {
        for (Field field : reflectAllFields()) {
            if (field.getName().equals(str)) {
                return field;
            }
        }
        return null;
    }

    @Override // net.vidageek.mirror.provider.ClassReflectionProvider
    public Method reflectMethod(String str, Class<?>[] clsArr) {
        ClassArrayMatcher classArrayMatcher = new ClassArrayMatcher(clsArr);
        Method method = null;
        for (Method method2 : reflectAllMethods()) {
            if (method2.getName().equals(str)) {
                MatchType match = classArrayMatcher.match(method2.getParameterTypes());
                if (MatchType.PERFECT.equals(match)) {
                    return method2;
                }
                if (MatchType.MATCH.equals(match)) {
                    method = method2;
                }
            }
        }
        return method;
    }
}
