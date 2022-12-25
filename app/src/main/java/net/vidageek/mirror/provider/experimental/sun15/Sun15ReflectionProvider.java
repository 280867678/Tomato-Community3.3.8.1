package net.vidageek.mirror.provider.experimental.sun15;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import net.vidageek.mirror.provider.AnnotatedElementReflectionProvider;
import net.vidageek.mirror.provider.ClassReflectionProvider;
import net.vidageek.mirror.provider.ConstructorBypassingReflectionProvider;
import net.vidageek.mirror.provider.ConstructorReflectionProvider;
import net.vidageek.mirror.provider.FieldReflectionProvider;
import net.vidageek.mirror.provider.GenericTypeAccessor;
import net.vidageek.mirror.provider.MethodReflectionProvider;
import net.vidageek.mirror.provider.ParameterizedElementReflectionProvider;
import net.vidageek.mirror.provider.ProxyReflectionProvider;
import net.vidageek.mirror.provider.ReflectionProvider;
import net.vidageek.mirror.provider.java.ObjenesisConstructorBypassingReflectionProvider;
import net.vidageek.mirror.provider.java.PureJavaAnnotatedElementReflectionProvider;
import net.vidageek.mirror.provider.java.PureJavaClassGenericTypeAccessor;
import net.vidageek.mirror.provider.java.PureJavaClassReflectionProvider;
import net.vidageek.mirror.provider.java.PureJavaFieldGenericTypeAccessor;
import net.vidageek.mirror.provider.java.PureJavaParameterizedElementReflectionProvider;
import net.vidageek.mirror.proxy.cglib.CGLibProxyReflectionProvider;
import net.vidageek.mirror.proxy.dsl.MethodInterceptor;

/* loaded from: classes4.dex */
public final class Sun15ReflectionProvider implements ReflectionProvider {
    @Override // net.vidageek.mirror.provider.ReflectionProvider
    public AnnotatedElementReflectionProvider getAnnotatedElementReflectionProvider(AnnotatedElement annotatedElement) {
        return new PureJavaAnnotatedElementReflectionProvider(annotatedElement);
    }

    @Override // net.vidageek.mirror.provider.ReflectionProvider
    public GenericTypeAccessor getClassGenericTypeAccessor(Class<?> cls) {
        return new PureJavaClassGenericTypeAccessor(cls);
    }

    @Override // net.vidageek.mirror.provider.ReflectionProvider
    public ClassReflectionProvider<? extends Object> getClassReflectionProvider(String str) {
        return new PureJavaClassReflectionProvider(str);
    }

    @Override // net.vidageek.mirror.provider.ReflectionProvider
    public <T> ClassReflectionProvider<T> getClassReflectionProvider(Class<T> cls) {
        return new PureJavaClassReflectionProvider(cls);
    }

    @Override // net.vidageek.mirror.provider.ReflectionProvider
    public <T> ConstructorBypassingReflectionProvider<T> getConstructorBypassingReflectionProvider(Class<T> cls) {
        return new ObjenesisConstructorBypassingReflectionProvider(cls);
    }

    @Override // net.vidageek.mirror.provider.ReflectionProvider
    public <T> ConstructorReflectionProvider<T> getConstructorReflectionProvider(Class<T> cls, Constructor<T> constructor) {
        return new Sun15ConstructorReflectionProvider(cls, constructor);
    }

    @Override // net.vidageek.mirror.provider.ReflectionProvider
    public GenericTypeAccessor getFieldGenericTypeAccessor(Field field) {
        return new PureJavaFieldGenericTypeAccessor(field);
    }

    @Override // net.vidageek.mirror.provider.ReflectionProvider
    public FieldReflectionProvider getFieldReflectionProvider(Object obj, Class<?> cls, Field field) {
        return new Sun15FieldReflectionProvider(obj, cls, field);
    }

    @Override // net.vidageek.mirror.provider.ReflectionProvider
    public MethodReflectionProvider getMethodReflectionProvider(Object obj, Class<?> cls, Method method) {
        return new Sun15MethodReflectionProvider(obj, cls, method);
    }

    @Override // net.vidageek.mirror.provider.ReflectionProvider
    public ParameterizedElementReflectionProvider getParameterizedElementProvider(GenericTypeAccessor genericTypeAccessor) {
        return new PureJavaParameterizedElementReflectionProvider(genericTypeAccessor);
    }

    @Override // net.vidageek.mirror.provider.ReflectionProvider
    public ProxyReflectionProvider getProxyReflectionProvider(Class<?> cls, List<Class<?>> list, MethodInterceptor... methodInterceptorArr) {
        return new CGLibProxyReflectionProvider(cls, list, methodInterceptorArr);
    }
}
