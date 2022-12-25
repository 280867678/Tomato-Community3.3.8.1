package net.vidageek.mirror.reflect;

import java.lang.annotation.Annotation;
import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.provider.ReflectionProvider;
import net.vidageek.mirror.reflect.dsl.AnnotationHandler;
import net.vidageek.mirror.reflect.dsl.MethodAnnotationHandler;

/* loaded from: classes4.dex */
public final class DefaultAnnotationHandler<T extends Annotation> implements AnnotationHandler<T> {
    private final Class<T> annotation;
    private final Class<?> clazz;
    private final ReflectionProvider provider;

    public DefaultAnnotationHandler(ReflectionProvider reflectionProvider, Class<?> cls, Class<T> cls2) {
        if (cls != null) {
            if (cls2 == null) {
                throw new IllegalArgumentException("Argument annotation cannot be null.");
            }
            this.provider = reflectionProvider;
            this.clazz = cls;
            this.annotation = cls2;
            return;
        }
        throw new IllegalArgumentException("Argument clazz cannot be null.");
    }

    @Override // net.vidageek.mirror.reflect.dsl.AnnotationHandler
    public T atField(String str) {
        if (str == null || str.trim().length() == 0) {
            throw new IllegalArgumentException("fieldName cannot be null or empty.");
        }
        ReflectionProvider reflectionProvider = this.provider;
        return (T) reflectionProvider.getAnnotatedElementReflectionProvider(new Mirror(reflectionProvider).m78on((Class) this.clazz).reflect().field(str)).getAnnotation(this.annotation);
    }

    @Override // net.vidageek.mirror.reflect.dsl.AnnotationHandler
    public MethodAnnotationHandler<T> atMethod(String str) {
        if (str == null || str.trim().length() == 0) {
            throw new IllegalArgumentException("methodName cannot be null or empty");
        }
        return new DefaultMethodAnnotationHandler(this.provider, this.clazz, str, this.annotation);
    }

    @Override // net.vidageek.mirror.reflect.dsl.AnnotationHandler
    public T atClass() {
        return (T) this.provider.getAnnotatedElementReflectionProvider(this.clazz).getAnnotation(this.annotation);
    }
}
