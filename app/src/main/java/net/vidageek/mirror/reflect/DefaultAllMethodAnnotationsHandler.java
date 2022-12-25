package net.vidageek.mirror.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.provider.ReflectionProvider;
import net.vidageek.mirror.reflect.dsl.AllMethodAnnotationsHandler;

/* loaded from: classes4.dex */
public final class DefaultAllMethodAnnotationsHandler implements AllMethodAnnotationsHandler {
    private final Class<?> clazz;
    private final String methodName;
    private final ReflectionProvider provider;

    public DefaultAllMethodAnnotationsHandler(ReflectionProvider reflectionProvider, Class<?> cls, String str) {
        if (cls == null) {
            throw new IllegalArgumentException("Argument clazz cannot be null.");
        }
        if (str == null || str.trim().length() == 0) {
            throw new IllegalArgumentException("Argument methodName cannot be null or blank.");
        }
        this.provider = reflectionProvider;
        this.clazz = cls;
        this.methodName = str.trim();
    }

    @Override // net.vidageek.mirror.reflect.dsl.AllMethodAnnotationsHandler
    public List<Annotation> withoutArgs() {
        return withArgs(new Class[0]);
    }

    @Override // net.vidageek.mirror.reflect.dsl.AllMethodAnnotationsHandler
    public List<Annotation> withArgs(Class<?>... clsArr) {
        Method withArgs = new Mirror(this.provider).m78on((Class) this.clazz).reflect().method(this.methodName).withArgs(clsArr);
        if (withArgs == null) {
            throw new IllegalArgumentException("could not find method that matched " + Arrays.asList(clsArr));
        }
        return this.provider.getAnnotatedElementReflectionProvider(withArgs).getAnnotations();
    }
}
