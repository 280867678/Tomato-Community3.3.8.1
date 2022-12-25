package net.vidageek.mirror.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.provider.ReflectionProvider;
import net.vidageek.mirror.reflect.dsl.AllAnnotationsHandler;
import net.vidageek.mirror.reflect.dsl.AllMethodAnnotationsHandler;

/* loaded from: classes4.dex */
public final class DefaultAllAnnotationsHandler implements AllAnnotationsHandler {
    private final Class<?> clazz;
    private final ReflectionProvider provider;

    public DefaultAllAnnotationsHandler(ReflectionProvider reflectionProvider, Class<?> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("Argument clazz cannot be null.");
        }
        this.provider = reflectionProvider;
        this.clazz = cls;
    }

    @Override // net.vidageek.mirror.reflect.dsl.AllAnnotationsHandler
    public List<Annotation> atClass() {
        return this.provider.getAnnotatedElementReflectionProvider(this.clazz).getAnnotations();
    }

    @Override // net.vidageek.mirror.reflect.dsl.AllAnnotationsHandler
    public List<Annotation> atField(String str) {
        Field field = new Mirror(this.provider).m78on((Class) this.clazz).reflect().field(str);
        if (field == null) {
            throw new IllegalArgumentException("could not find field " + str + " at class " + this.clazz);
        }
        return this.provider.getAnnotatedElementReflectionProvider(field).getAnnotations();
    }

    @Override // net.vidageek.mirror.reflect.dsl.AllAnnotationsHandler
    public AllMethodAnnotationsHandler atMethod(String str) {
        return new DefaultAllMethodAnnotationsHandler(this.provider, this.clazz, str);
    }
}
