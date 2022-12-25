package net.vidageek.mirror.reflect;

import java.lang.reflect.Constructor;
import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.exception.MirrorException;
import net.vidageek.mirror.list.dsl.MirrorList;
import net.vidageek.mirror.provider.ReflectionProvider;
import net.vidageek.mirror.reflect.dsl.ConstructorReflector;

/* loaded from: classes4.dex */
public final class DefaultConstructorReflector<T> implements ConstructorReflector<T> {
    private final Class<T> clazz;
    private final ReflectionProvider provider;

    public DefaultConstructorReflector(ReflectionProvider reflectionProvider, Class<T> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("argument class cannot be null.");
        }
        this.provider = reflectionProvider;
        this.clazz = cls;
    }

    @Override // net.vidageek.mirror.reflect.dsl.ConstructorReflector
    public Constructor<T> withArgs(Class<?>... clsArr) {
        if (clsArr == null) {
            throw new IllegalArgumentException("classes cannot be null");
        }
        return this.provider.getClassReflectionProvider(this.clazz).reflectConstructor(clsArr);
    }

    @Override // net.vidageek.mirror.reflect.dsl.ConstructorReflector
    public Constructor<T> withoutArgs() {
        return withArgs(new Class[0]);
    }

    @Override // net.vidageek.mirror.reflect.dsl.ConstructorReflector
    public Constructor<T> withAnyArgs() {
        MirrorList<Constructor<T>> constructors = new Mirror(this.provider).m78on((Class) this.clazz).reflectAll().constructors();
        if (constructors.size() != 1) {
            throw new MirrorException("there is more than one constructor on class " + this.clazz.getName() + ". withAnyArgs must be called only on classes with a single constructor.");
        }
        return constructors.get(0);
    }
}
