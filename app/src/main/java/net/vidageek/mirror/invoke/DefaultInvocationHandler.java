package net.vidageek.mirror.invoke;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.vidageek.mirror.bean.Bean;
import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.exception.MirrorException;
import net.vidageek.mirror.invoke.dsl.ConstructorHandler;
import net.vidageek.mirror.invoke.dsl.InvocationHandler;
import net.vidageek.mirror.invoke.dsl.MethodHandler;
import net.vidageek.mirror.invoke.dsl.SetterMethodHandler;
import net.vidageek.mirror.provider.ReflectionProvider;

/* loaded from: classes4.dex */
public final class DefaultInvocationHandler<T> implements InvocationHandler<T> {
    private final Class<?> clazz;
    private final ReflectionProvider provider;
    private final Object target;

    public DefaultInvocationHandler(ReflectionProvider reflectionProvider, Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("target can't be null");
        }
        this.provider = reflectionProvider;
        this.target = obj;
        this.clazz = obj.getClass();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public DefaultInvocationHandler(ReflectionProvider reflectionProvider, Class<T> cls) {
        if (cls == 0) {
            throw new IllegalArgumentException("target can't be null");
        }
        this.provider = reflectionProvider;
        this.clazz = cls;
        this.target = null;
    }

    @Override // net.vidageek.mirror.invoke.dsl.InvocationHandler
    public MethodHandler method(String str) {
        if (str == null) {
            throw new IllegalArgumentException("methodName can't be null");
        }
        return new MethodHandlerByName(this.provider, this.target, this.clazz, str);
    }

    @Override // net.vidageek.mirror.invoke.dsl.InvocationHandler
    public ConstructorHandler<T> constructor() {
        if (this.target != null) {
            throw new IllegalStateException("must use constructor InvocationHandler(Class<T>) instead of InvocationHandler(Object).");
        }
        return new ConstructorHandlerByArgs(this.provider, this.clazz);
    }

    @Override // net.vidageek.mirror.invoke.dsl.InvocationHandler
    public MethodHandler method(Method method) {
        return new MethodHandlerByMethod(this.provider, this.target, this.clazz, method);
    }

    @Override // net.vidageek.mirror.invoke.dsl.InvocationHandler
    public <C> ConstructorHandler<C> constructor(Constructor<C> constructor) {
        return new ConstructorHandlerByConstructor(this.provider, this.clazz, constructor);
    }

    @Override // net.vidageek.mirror.invoke.dsl.InvocationHandler
    public Object getterFor(String str) {
        Method method = null;
        for (String str2 : new Bean().getter(str)) {
            method = new Mirror(this.provider).m78on((Class) this.target.getClass()).reflect().method(str2).withoutArgs();
            if (method != null) {
                break;
            }
        }
        if (method == null) {
            throw new MirrorException("Could not find getter for field " + str);
        }
        return new Mirror(this.provider).m77on(this.target).invoke().method(method).withoutArgs();
    }

    @Override // net.vidageek.mirror.invoke.dsl.InvocationHandler
    public Object getterFor(Field field) {
        return getterFor(field.getName());
    }

    @Override // net.vidageek.mirror.invoke.dsl.InvocationHandler
    public SetterMethodHandler setterFor(String str) {
        return new DefaultSetterMethodHandler(this.provider, this.target, str);
    }

    @Override // net.vidageek.mirror.invoke.dsl.InvocationHandler
    public SetterMethodHandler setterFor(Field field) {
        return setterFor(field.getName());
    }
}
