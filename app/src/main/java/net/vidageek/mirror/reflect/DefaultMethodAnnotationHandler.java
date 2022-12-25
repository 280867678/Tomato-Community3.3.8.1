package net.vidageek.mirror.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import net.vidageek.mirror.provider.ReflectionProvider;
import net.vidageek.mirror.reflect.dsl.MethodAnnotationHandler;

/* loaded from: classes4.dex */
public final class DefaultMethodAnnotationHandler<T extends Annotation> implements MethodAnnotationHandler<T> {
    private final Class<T> annotation;
    private final Class<?> clazz;
    private final String methodName;
    private final ReflectionProvider provider;

    public DefaultMethodAnnotationHandler(ReflectionProvider reflectionProvider, Class<?> cls, String str, Class<T> cls2) {
        if (cls == null) {
            throw new IllegalArgumentException("Argument clazz cannot be null.");
        }
        if (str == null || str.trim().length() == 0) {
            throw new IllegalArgumentException("Argument fieldName cannot be null or empty.");
        }
        if (cls2 == null) {
            throw new IllegalArgumentException("Argument annotation cannot be null.");
        }
        this.provider = reflectionProvider;
        this.clazz = cls;
        this.methodName = str.trim();
        this.annotation = cls2;
    }

    @Override // net.vidageek.mirror.reflect.dsl.MethodAnnotationHandler
    public T withArgs(Class<?>... clsArr) {
        Method withArgs = new DefaultMethodReflector(this.provider, this.methodName, this.clazz).withArgs(clsArr);
        if (withArgs == null) {
            throw new IllegalArgumentException("could not find method matching argument list " + Arrays.asList(clsArr));
        }
        return (T) this.provider.getAnnotatedElementReflectionProvider(withArgs).getAnnotation(this.annotation);
    }

    @Override // net.vidageek.mirror.reflect.dsl.MethodAnnotationHandler
    public T withoutArgs() {
        return withArgs(new Class[0]);
    }
}
