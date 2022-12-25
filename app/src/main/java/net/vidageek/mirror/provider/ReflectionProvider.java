package net.vidageek.mirror.provider;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import net.vidageek.mirror.proxy.dsl.MethodInterceptor;

/* loaded from: classes4.dex */
public interface ReflectionProvider {
    AnnotatedElementReflectionProvider getAnnotatedElementReflectionProvider(AnnotatedElement annotatedElement);

    GenericTypeAccessor getClassGenericTypeAccessor(Class<?> cls);

    <T> ClassReflectionProvider<T> getClassReflectionProvider(Class<T> cls);

    ClassReflectionProvider<? extends Object> getClassReflectionProvider(String str);

    <T> ConstructorBypassingReflectionProvider<T> getConstructorBypassingReflectionProvider(Class<T> cls);

    <T> ConstructorReflectionProvider<T> getConstructorReflectionProvider(Class<T> cls, Constructor<T> constructor);

    GenericTypeAccessor getFieldGenericTypeAccessor(Field field);

    FieldReflectionProvider getFieldReflectionProvider(Object obj, Class<?> cls, Field field);

    MethodReflectionProvider getMethodReflectionProvider(Object obj, Class<?> cls, Method method);

    ParameterizedElementReflectionProvider getParameterizedElementProvider(GenericTypeAccessor genericTypeAccessor);

    ProxyReflectionProvider getProxyReflectionProvider(Class<?> cls, List<Class<?>> list, MethodInterceptor... methodInterceptorArr);
}
