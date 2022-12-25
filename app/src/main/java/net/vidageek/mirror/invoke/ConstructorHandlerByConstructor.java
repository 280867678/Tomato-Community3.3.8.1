package net.vidageek.mirror.invoke;

import java.lang.reflect.Constructor;
import net.vidageek.mirror.invoke.dsl.ConstructorHandler;
import net.vidageek.mirror.provider.ConstructorReflectionProvider;
import net.vidageek.mirror.provider.ReflectionProvider;

/* loaded from: classes4.dex */
public final class ConstructorHandlerByConstructor<T> implements ConstructorHandler<T> {
    private final Class<T> clazz;
    private final Constructor<T> constructor;
    private final ReflectionProvider provider;

    public ConstructorHandlerByConstructor(ReflectionProvider reflectionProvider, Class<T> cls, Constructor<T> constructor) {
        if (cls != null) {
            if (constructor == null) {
                throw new IllegalArgumentException("constructor cannot be null");
            }
            if (!cls.equals(constructor.getDeclaringClass())) {
                throw new IllegalArgumentException("constructor declaring type should be " + cls.getName() + " but was " + constructor.getDeclaringClass().getName());
            }
            this.provider = reflectionProvider;
            this.clazz = cls;
            this.constructor = constructor;
            return;
        }
        throw new IllegalArgumentException("clazz cannot be null");
    }

    @Override // net.vidageek.mirror.invoke.dsl.ConstructorHandler
    public T withArgs(Object... objArr) {
        ConstructorReflectionProvider<T> constructorReflectionProvider = this.provider.getConstructorReflectionProvider(this.clazz, this.constructor);
        constructorReflectionProvider.setAccessible();
        return constructorReflectionProvider.instantiate(objArr);
    }

    @Override // net.vidageek.mirror.invoke.dsl.ConstructorHandler
    public T withoutArgs() {
        return withArgs(new Object[0]);
    }

    @Override // net.vidageek.mirror.invoke.dsl.ConstructorHandler
    public T bypasser() {
        return this.provider.getConstructorBypassingReflectionProvider(this.clazz).bypassConstructor();
    }
}
