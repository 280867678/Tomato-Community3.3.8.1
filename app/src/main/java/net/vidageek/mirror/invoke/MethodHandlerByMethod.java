package net.vidageek.mirror.invoke;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import net.vidageek.mirror.invoke.dsl.MethodHandler;
import net.vidageek.mirror.provider.MethodReflectionProvider;
import net.vidageek.mirror.provider.ReflectionProvider;

/* loaded from: classes4.dex */
public final class MethodHandlerByMethod implements MethodHandler {
    private final Class<?> clazz;
    private final Method method;
    private final ReflectionProvider provider;
    private final Object target;

    public MethodHandlerByMethod(ReflectionProvider reflectionProvider, Object obj, Class<?> cls, Method method) {
        if (cls != null) {
            if (method == null) {
                throw new IllegalArgumentException("method cannot be null");
            }
            if (!method.getDeclaringClass().isAssignableFrom(cls)) {
                throw new IllegalArgumentException("method " + method + " cannot be invoked on clazz " + cls.getName());
            }
            this.provider = reflectionProvider;
            this.target = obj;
            this.clazz = cls;
            this.method = method;
            return;
        }
        throw new IllegalArgumentException("clazz cannot be null");
    }

    @Override // net.vidageek.mirror.invoke.dsl.MethodHandler
    public Object withArgs(Object... objArr) {
        if (this.target == null && !Modifier.isStatic(this.method.getModifiers())) {
            throw new IllegalStateException("attempt to call instance method " + this.method.getName() + " on class " + this.clazz.getName());
        }
        MethodReflectionProvider methodReflectionProvider = this.provider.getMethodReflectionProvider(this.target, this.clazz, this.method);
        methodReflectionProvider.setAccessible();
        return methodReflectionProvider.invoke(objArr);
    }

    @Override // net.vidageek.mirror.invoke.dsl.MethodHandler
    public Object withoutArgs() {
        return withArgs(new Object[0]);
    }
}
