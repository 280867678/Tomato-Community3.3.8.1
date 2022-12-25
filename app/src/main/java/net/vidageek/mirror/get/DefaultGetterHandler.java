package net.vidageek.mirror.get;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.exception.MirrorException;
import net.vidageek.mirror.get.dsl.GetterHandler;
import net.vidageek.mirror.provider.FieldReflectionProvider;
import net.vidageek.mirror.provider.ReflectionProvider;

/* loaded from: classes4.dex */
public final class DefaultGetterHandler implements GetterHandler {
    private final Class<?> clazz;
    private final ReflectionProvider provider;
    private final Object target;

    public DefaultGetterHandler(ReflectionProvider reflectionProvider, Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("target cannot be null");
        }
        this.provider = reflectionProvider;
        this.clazz = obj.getClass();
        this.target = obj;
    }

    public DefaultGetterHandler(ReflectionProvider reflectionProvider, Class<?> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("clazz cannot be null");
        }
        this.provider = reflectionProvider;
        this.clazz = cls;
        this.target = null;
    }

    @Override // net.vidageek.mirror.get.dsl.GetterHandler
    public Object field(String str) {
        if (str == null || str.trim().length() == 0) {
            throw new IllegalArgumentException("fieldName cannot be null or empty.");
        }
        return field(getField(str));
    }

    @Override // net.vidageek.mirror.get.dsl.GetterHandler
    public Object field(Field field) {
        if (field == null) {
            throw new IllegalArgumentException("field cannot be null");
        }
        if (!field.getDeclaringClass().isAssignableFrom(this.clazz)) {
            throw new IllegalArgumentException("field declaring class (" + field.getDeclaringClass().getName() + ") doesn't match clazz " + this.clazz.getName());
        } else if (this.target == null && !Modifier.isStatic(field.getModifiers())) {
            throw new IllegalStateException("attempt to get instance field " + field.getName() + " on class " + this.clazz.getName());
        } else {
            FieldReflectionProvider fieldReflectionProvider = this.provider.getFieldReflectionProvider(this.target, this.clazz, field);
            fieldReflectionProvider.setAccessible();
            return fieldReflectionProvider.getValue();
        }
    }

    private Field getField(String str) {
        Field field = new Mirror(this.provider).m78on((Class) this.clazz).reflect().field(str);
        if (field != null) {
            return field;
        }
        throw new MirrorException("could not find field " + str + " for class " + this.clazz.getName());
    }
}
