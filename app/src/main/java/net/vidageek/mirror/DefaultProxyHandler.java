package net.vidageek.mirror;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import net.vidageek.mirror.provider.ReflectionProvider;
import net.vidageek.mirror.proxy.dsl.MethodInterceptor;
import net.vidageek.mirror.proxy.dsl.ProxyHandler;

/* loaded from: classes4.dex */
public class DefaultProxyHandler<T> implements ProxyHandler<T> {
    private Class<?> baseClass = Object.class;
    private final List<Class<?>> interfaces = new ArrayList();
    private final ReflectionProvider provider;

    public DefaultProxyHandler(ReflectionProvider reflectionProvider, Class<?>[] clsArr) {
        this.provider = reflectionProvider;
        extractBaseClassAndInterfaces(clsArr);
    }

    private void extractBaseClassAndInterfaces(Class<?>[] clsArr) {
        boolean z = false;
        for (Class<?> cls : clsArr) {
            if (cls.isInterface()) {
                this.interfaces.add(cls);
            } else if (!z) {
                if (Modifier.isFinal(cls.getModifiers())) {
                    throw new IllegalArgumentException("Cannot proxify final class " + cls.getName());
                }
                this.baseClass = cls;
                z = true;
            } else {
                throw new IllegalArgumentException("Cannot proxify more than one concrete/abstract class");
            }
        }
    }

    @Override // net.vidageek.mirror.proxy.dsl.ProxyHandler
    public T interceptingWith(MethodInterceptor... methodInterceptorArr) {
        if (methodInterceptorArr == null || methodInterceptorArr.length == 0) {
            throw new IllegalArgumentException("interceptors cannot be null or empty");
        }
        return (T) this.provider.getProxyReflectionProvider(this.baseClass, this.interfaces, methodInterceptorArr).createProxy();
    }
}
