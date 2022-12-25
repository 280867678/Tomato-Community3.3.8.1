package net.vidageek.mirror.reflect;

import java.lang.reflect.Field;
import net.vidageek.mirror.provider.ReflectionProvider;
import net.vidageek.mirror.provider.java.PureJavaClassGenericTypeAccessor;
import net.vidageek.mirror.reflect.dsl.AnnotationHandler;
import net.vidageek.mirror.reflect.dsl.ConstructorReflector;
import net.vidageek.mirror.reflect.dsl.MethodReflector;
import net.vidageek.mirror.reflect.dsl.ParameterizedElementHandler;
import net.vidageek.mirror.reflect.dsl.ReflectionHandler;

/* loaded from: classes4.dex */
public final class DefaultReflectionHandler<T> implements ReflectionHandler<T> {
    private final Class<T> clazz;
    private final ReflectionProvider provider;

    public DefaultReflectionHandler(ReflectionProvider reflectionProvider, Class<T> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("clazz cannot be null");
        }
        this.provider = reflectionProvider;
        this.clazz = cls;
    }

    @Override // net.vidageek.mirror.reflect.dsl.ReflectionHandler
    public Field field(String str) {
        if (str == null || str.trim().length() == 0) {
            throw new IllegalArgumentException("fieldName cannot be null or empty.");
        }
        return new DefaultFieldReflector(this.provider, str).onClass(this.clazz);
    }

    @Override // net.vidageek.mirror.reflect.dsl.ReflectionHandler
    public MethodReflector method(String str) {
        if (str == null || str.trim().length() == 0) {
            throw new IllegalArgumentException("methodName cannot be null or empty.");
        }
        return new DefaultMethodReflector(this.provider, str, this.clazz);
    }

    @Override // net.vidageek.mirror.reflect.dsl.ReflectionHandler
    public ConstructorReflector<T> constructor() {
        return new DefaultConstructorReflector(this.provider, this.clazz);
    }

    @Override // net.vidageek.mirror.reflect.dsl.ReflectionHandler
    public <A> AnnotationHandler<? extends A> annotation(Class<A> cls) {
        return new DefaultAnnotationHandler(this.provider, this.clazz, cls);
    }

    @Override // net.vidageek.mirror.reflect.dsl.ReflectionHandler
    public ParameterizedElementHandler parentGenericType() {
        return new DefaultParameterizedElementHandler(this.provider, new PureJavaClassGenericTypeAccessor(this.clazz));
    }
}
