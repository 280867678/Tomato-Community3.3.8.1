package net.vidageek.mirror.invoke;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.exception.MirrorException;
import net.vidageek.mirror.invoke.dsl.ConstructorHandler;
import net.vidageek.mirror.provider.ReflectionProvider;

/* loaded from: classes4.dex */
public final class ConstructorHandlerByArgs<T> implements ConstructorHandler<T> {
    private final Class<T> clazz;
    private final ReflectionProvider provider;

    public ConstructorHandlerByArgs(ReflectionProvider reflectionProvider, Class<T> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("Argument class cannot be null");
        }
        this.provider = reflectionProvider;
        this.clazz = cls;
    }

    @Override // net.vidageek.mirror.invoke.dsl.ConstructorHandler
    public T withoutArgs() {
        return withArgs(new Object[0]);
    }

    @Override // net.vidageek.mirror.invoke.dsl.ConstructorHandler
    public T withArgs(Object... objArr) {
        return (T) new ConstructorHandlerByConstructor(this.provider, this.clazz, getConstructor(objArr)).withArgs(objArr);
    }

    @Override // net.vidageek.mirror.invoke.dsl.ConstructorHandler
    public T bypasser() {
        return this.provider.getConstructorBypassingReflectionProvider(this.clazz).bypassConstructor();
    }

    private Constructor<T> getConstructor(Object... objArr) {
        int length = objArr == null ? 0 : objArr.length;
        Class<?>[] clsArr = new Class[length];
        for (int i = 0; i < length; i++) {
            if (objArr[i] == null) {
                throw new IllegalArgumentException("Cannot invoke a constructor by args if one of it's arguments is null. First reflect the constructor.");
            }
            clsArr[i] = objArr[i].getClass();
        }
        Constructor<T> withArgs = new Mirror(this.provider).m78on((Class) this.clazz).reflect().constructor().withArgs(clsArr);
        if (withArgs != null) {
            return withArgs;
        }
        throw new MirrorException("Could not find constructor with args " + Arrays.asList(clsArr) + " on class " + this.clazz.getName());
    }
}
