package net.vidageek.mirror.provider.experimental.sun15;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.exception.ReflectionProviderException;
import net.vidageek.mirror.provider.ConstructorReflectionProvider;
import net.vidageek.mirror.provider.java.DefaultMirrorReflectionProvider;
import net.vidageek.mirror.provider.java.PureJavaConstructorReflectionProvider;
import sun.misc.Unsafe;
import sun.reflect.ConstructorAccessor;
import sun.reflect.MethodAccessor;
import sun.reflect.ReflectionFactory;

/* loaded from: classes4.dex */
public final class Sun15ConstructorReflectionProvider<T> implements ConstructorReflectionProvider<T> {
    private static MethodAccessor constructorAccessorAcquirer;
    private static long constructorAccessorFieldOffset;
    private static final Object[] emptyObjectArray = new Object[0];
    private static Unsafe unsafe;
    private final ConstructorAccessor accessor;
    private final Class<T> clazz;
    private final Constructor<T> constructor;

    static {
        Mirror mirror = new Mirror(new DefaultMirrorReflectionProvider());
        Field field = mirror.m78on((Class) Constructor.class).reflect().field("constructorAccessor");
        Method withoutArgs = mirror.m78on((Class) Constructor.class).reflect().method("acquireConstructorAccessor").withoutArgs();
        unsafe = (Unsafe) mirror.m78on((Class) Unsafe.class).get().field("theUnsafe");
        constructorAccessorFieldOffset = unsafe.objectFieldOffset(field);
        constructorAccessorAcquirer = ReflectionFactory.getReflectionFactory().newMethodAccessor(withoutArgs);
    }

    public Sun15ConstructorReflectionProvider(Class<T> cls, Constructor<T> constructor) {
        this.clazz = cls;
        this.constructor = constructor;
        ConstructorAccessor constructorAccessor = (ConstructorAccessor) unsafe.getObject(constructor, constructorAccessorFieldOffset);
        if (constructorAccessor == null) {
            try {
                constructorAccessorAcquirer.invoke(constructor, emptyObjectArray);
                constructorAccessor = (ConstructorAccessor) unsafe.getObject(constructor, constructorAccessorFieldOffset);
            } catch (IllegalArgumentException e) {
                throw new ReflectionProviderException("Could not acquire ConstructorAccessor.", e);
            } catch (InvocationTargetException e2) {
                throw new ReflectionProviderException("Could not acquire ConstructorAccessor.", e2);
            }
        }
        this.accessor = constructorAccessor;
    }

    @Override // net.vidageek.mirror.provider.ConstructorReflectionProvider
    public Class<?>[] getParameters() {
        return new PureJavaConstructorReflectionProvider(this.clazz, this.constructor).getParameters();
    }

    @Override // net.vidageek.mirror.provider.ConstructorReflectionProvider
    public T instantiate(Object... objArr) {
        try {
            return (T) this.accessor.newInstance(objArr);
        } catch (IllegalArgumentException e) {
            throw new ReflectionProviderException("could not invoke constructor " + this.constructor.toGenericString() + " on class " + this.clazz.getName(), e);
        } catch (InstantiationException e2) {
            throw new ReflectionProviderException("could not invoke constructor " + this.constructor.toGenericString() + " on class " + this.clazz.getName(), e2);
        } catch (InvocationTargetException e3) {
            throw new ReflectionProviderException("could not invoke constructor " + this.constructor.toGenericString() + " on class " + this.clazz.getName(), e3);
        }
    }

    @Override // net.vidageek.mirror.provider.ReflectionElementReflectionProvider
    public void setAccessible() {
        this.constructor.setAccessible(true);
    }
}
