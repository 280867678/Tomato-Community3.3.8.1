package net.vidageek.mirror.provider.java;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import net.vidageek.mirror.exception.ReflectionProviderException;
import net.vidageek.mirror.provider.ConstructorReflectionProvider;

/* loaded from: classes4.dex */
public final class PureJavaConstructorReflectionProvider<T> implements ConstructorReflectionProvider<T> {
    private final Class<T> clazz;
    private final Constructor<T> constructor;

    public PureJavaConstructorReflectionProvider(Class<T> cls, Constructor<T> constructor) {
        this.clazz = cls;
        this.constructor = constructor;
    }

    @Override // net.vidageek.mirror.provider.ReflectionElementReflectionProvider
    public void setAccessible() {
        this.constructor.setAccessible(true);
    }

    @Override // net.vidageek.mirror.provider.ConstructorReflectionProvider
    public T instantiate(Object... objArr) {
        try {
            setAccessible();
            return this.constructor.newInstance(objArr);
        } catch (IllegalAccessException e) {
            throw new ReflectionProviderException("could not invoke constructor " + this.constructor.toGenericString() + " on class " + this.clazz.getName(), e);
        } catch (IllegalArgumentException e2) {
            throw new ReflectionProviderException("could not invoke constructor " + this.constructor.toGenericString() + " on class " + this.clazz.getName(), e2);
        } catch (InstantiationException e3) {
            throw new ReflectionProviderException("could not invoke constructor " + this.constructor.toGenericString() + " on class " + this.clazz.getName(), e3);
        } catch (InvocationTargetException e4) {
            String str = "could not invoke constructor " + this.constructor.toGenericString() + " on class " + this.clazz.getName();
            Throwable cause = e4.getCause();
            Throwable th = e4;
            if (cause != null) {
                th = e4.getCause();
            }
            throw new ReflectionProviderException(str, th);
        }
    }

    @Override // net.vidageek.mirror.provider.ConstructorReflectionProvider
    public Class<?>[] getParameters() {
        return this.constructor.getParameterTypes();
    }
}
