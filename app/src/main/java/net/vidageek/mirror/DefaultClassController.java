package net.vidageek.mirror;

import net.vidageek.mirror.dsl.ClassController;
import net.vidageek.mirror.get.DefaultGetterHandler;
import net.vidageek.mirror.get.dsl.GetterHandler;
import net.vidageek.mirror.invoke.DefaultInvocationHandler;
import net.vidageek.mirror.invoke.dsl.InvocationHandler;
import net.vidageek.mirror.provider.ReflectionProvider;
import net.vidageek.mirror.reflect.DefaultAllReflectionHandler;
import net.vidageek.mirror.reflect.DefaultReflectionHandler;
import net.vidageek.mirror.reflect.dsl.AllReflectionHandler;
import net.vidageek.mirror.reflect.dsl.ReflectionHandler;
import net.vidageek.mirror.set.DefaultSetterHandler;
import net.vidageek.mirror.set.dsl.SetterHandler;

/* loaded from: classes4.dex */
public final class DefaultClassController<T> implements ClassController<T> {
    private final Class<T> clazz;
    private final ReflectionProvider provider;

    public DefaultClassController(ReflectionProvider reflectionProvider, Class<T> cls) {
        this.provider = reflectionProvider;
        if (cls == null) {
            throw new IllegalArgumentException("clazz cannot be null");
        }
        this.clazz = cls;
    }

    @Override // net.vidageek.mirror.dsl.ClassController
    public InvocationHandler<T> invoke() {
        return new DefaultInvocationHandler(this.provider, (Class) this.clazz);
    }

    @Override // net.vidageek.mirror.dsl.ClassController
    public SetterHandler set() {
        return new DefaultSetterHandler(this.provider, (Class<?>) this.clazz);
    }

    @Override // net.vidageek.mirror.dsl.ClassController
    public GetterHandler get() {
        return new DefaultGetterHandler(this.provider, (Class<?>) this.clazz);
    }

    @Override // net.vidageek.mirror.dsl.ClassController
    public ReflectionHandler<T> reflect() {
        return new DefaultReflectionHandler(this.provider, this.clazz);
    }

    @Override // net.vidageek.mirror.dsl.ClassController
    public AllReflectionHandler<T> reflectAll() {
        return new DefaultAllReflectionHandler(this.provider, this.clazz);
    }
}
