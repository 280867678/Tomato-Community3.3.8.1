package net.vidageek.mirror.provider.java;

import java.lang.reflect.Field;
import net.vidageek.mirror.exception.ReflectionProviderException;
import net.vidageek.mirror.provider.FieldReflectionProvider;

/* loaded from: classes4.dex */
public final class PureJavaFieldReflectionProvider implements FieldReflectionProvider {
    private final Class<?> clazz;
    private final Field field;
    private final Object target;

    public PureJavaFieldReflectionProvider(Object obj, Class<?> cls, Field field) {
        this.target = obj;
        this.clazz = cls;
        this.field = field;
    }

    @Override // net.vidageek.mirror.provider.FieldReflectionProvider
    public void setValue(Object obj) {
        try {
            setAccessible();
            this.field.set(this.target, obj);
        } catch (IllegalAccessException unused) {
            throw new ReflectionProviderException("could not set value " + obj + " on field " + this.field.getName() + " of class " + this.clazz.getName());
        }
    }

    @Override // net.vidageek.mirror.provider.FieldReflectionProvider
    public Object getValue() {
        try {
            setAccessible();
            return this.field.get(this.target);
        } catch (IllegalAccessException unused) {
            throw new ReflectionProviderException("could not get value for field " + this.field.getName() + " of class " + this.clazz.getName());
        }
    }

    @Override // net.vidageek.mirror.provider.ReflectionElementReflectionProvider
    public void setAccessible() {
        this.field.setAccessible(true);
    }
}
