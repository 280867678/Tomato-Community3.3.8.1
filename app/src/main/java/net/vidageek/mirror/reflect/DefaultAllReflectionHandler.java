package net.vidageek.mirror.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import net.vidageek.mirror.list.BackedMirrorList;
import net.vidageek.mirror.list.dsl.Matcher;
import net.vidageek.mirror.list.dsl.MirrorList;
import net.vidageek.mirror.matcher.GetterMatcher;
import net.vidageek.mirror.matcher.SetterMatcher;
import net.vidageek.mirror.provider.ReflectionProvider;
import net.vidageek.mirror.reflect.dsl.AllAnnotationsHandler;
import net.vidageek.mirror.reflect.dsl.AllReflectionHandler;

/* loaded from: classes4.dex */
public final class DefaultAllReflectionHandler<T> implements AllReflectionHandler<T> {
    private final Class<T> clazz;
    private final ReflectionProvider provider;

    public DefaultAllReflectionHandler(ReflectionProvider reflectionProvider, Class<T> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("clazz cannot be null");
        }
        this.provider = reflectionProvider;
        this.clazz = cls;
    }

    @Override // net.vidageek.mirror.reflect.dsl.AllReflectionHandler
    public MirrorList<Field> fields() {
        return new BackedMirrorList(this.provider.getClassReflectionProvider(this.clazz).reflectAllFields());
    }

    @Override // net.vidageek.mirror.reflect.dsl.AllReflectionHandler
    public MirrorList<Method> methods() {
        return new BackedMirrorList(this.provider.getClassReflectionProvider(this.clazz).reflectAllMethods());
    }

    @Override // net.vidageek.mirror.reflect.dsl.AllReflectionHandler
    public MirrorList<Constructor<T>> constructors() {
        return new BackedMirrorList(this.provider.getClassReflectionProvider(this.clazz).reflectAllConstructors());
    }

    @Override // net.vidageek.mirror.reflect.dsl.AllReflectionHandler
    public AllAnnotationsHandler annotations() {
        return new DefaultAllAnnotationsHandler(this.provider, this.clazz);
    }

    @Override // net.vidageek.mirror.reflect.dsl.AllReflectionHandler
    public MirrorList<Method> setters() {
        return methods().matching(new SetterMatcher());
    }

    @Override // net.vidageek.mirror.reflect.dsl.AllReflectionHandler
    public MirrorList<Method> getters() {
        return methods().matching(new GetterMatcher());
    }

    @Override // net.vidageek.mirror.reflect.dsl.AllReflectionHandler
    public List<Field> fieldsMatching(Matcher<Field> matcher) {
        return fields().matching(matcher);
    }

    @Override // net.vidageek.mirror.reflect.dsl.AllReflectionHandler
    public List<Method> methodsMatching(Matcher<Method> matcher) {
        return methods().matching(matcher);
    }

    @Override // net.vidageek.mirror.reflect.dsl.AllReflectionHandler
    public List<Constructor<T>> constructorsMatching(Matcher<Constructor<T>> matcher) {
        return constructors().matching(matcher);
    }

    @Override // net.vidageek.mirror.reflect.dsl.AllReflectionHandler
    public List<Annotation> annotationsMatching(Matcher<Annotation> matcher) {
        return new BackedMirrorList(annotations().atClass()).matching(matcher);
    }
}
