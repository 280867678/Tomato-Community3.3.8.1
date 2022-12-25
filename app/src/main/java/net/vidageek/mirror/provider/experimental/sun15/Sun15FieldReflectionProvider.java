package net.vidageek.mirror.provider.experimental.sun15;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.exception.ReflectionProviderException;
import net.vidageek.mirror.provider.FieldReflectionProvider;
import net.vidageek.mirror.provider.java.DefaultMirrorReflectionProvider;
import sun.misc.Unsafe;
import sun.reflect.FieldAccessor;
import sun.reflect.MethodAccessor;
import sun.reflect.ReflectionFactory;

/* loaded from: classes4.dex */
public final class Sun15FieldReflectionProvider implements FieldReflectionProvider {
    private static final MethodAccessor fieldAccessorAcquirer;
    private static final long fieldAccessorFieldOffset;
    private static final Object[] objectArray = {true};
    private static final Unsafe unsafe;
    private final FieldAccessor accessor;
    private final Class<?> clazz;
    private final Field field;
    private final Object target;

    static {
        Mirror mirror = new Mirror(new DefaultMirrorReflectionProvider());
        Field field = mirror.m78on(Field.class).reflect().field("overrideFieldAccessor");
        Method withArgs = mirror.m78on(Field.class).reflect().method("acquireFieldAccessor").withArgs(Boolean.TYPE);
        unsafe = (Unsafe) mirror.m78on(Unsafe.class).get().field("theUnsafe");
        fieldAccessorFieldOffset = unsafe.objectFieldOffset(field);
        fieldAccessorAcquirer = ReflectionFactory.getReflectionFactory().newMethodAccessor(withArgs);
    }

    public Sun15FieldReflectionProvider(Object obj, Class<?> cls, Field field) {
        this.target = obj;
        this.clazz = cls;
        this.field = field;
        FieldAccessor fieldAccessor = (FieldAccessor) unsafe.getObject(field, fieldAccessorFieldOffset);
        if (fieldAccessor == null) {
            try {
                fieldAccessorAcquirer.invoke(field, objectArray);
                fieldAccessor = (FieldAccessor) unsafe.getObject(field, fieldAccessorFieldOffset);
            } catch (IllegalArgumentException e) {
                throw new ReflectionProviderException("Could not acquire FieldAccessor.", e);
            } catch (InvocationTargetException e2) {
                throw new ReflectionProviderException("Could not acquire FieldAccessor.", e2);
            }
        }
        this.accessor = fieldAccessor;
    }

    @Override // net.vidageek.mirror.provider.FieldReflectionProvider
    public Object getValue() {
        return this.accessor.get(this.target);
    }

    @Override // net.vidageek.mirror.provider.FieldReflectionProvider
    public void setValue(Object obj) {
        try {
            this.accessor.set(this.target, obj);
        } catch (IllegalAccessException unused) {
            throw new ReflectionProviderException("could not set value " + obj + " on field " + this.field.getName() + " of class " + this.clazz.getName());
        } catch (IllegalArgumentException unused2) {
            throw new ReflectionProviderException("could not set value " + obj + " on field " + this.field.getName() + " of class " + this.clazz.getName());
        }
    }

    @Override // net.vidageek.mirror.provider.ReflectionElementReflectionProvider
    public void setAccessible() {
        this.field.setAccessible(true);
    }
}
