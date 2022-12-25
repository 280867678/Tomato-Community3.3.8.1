package net.vidageek.mirror.set;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import net.vidageek.mirror.exception.MirrorException;
import net.vidageek.mirror.matcher.ClassArrayMatcher;
import net.vidageek.mirror.matcher.MatchType;
import net.vidageek.mirror.provider.FieldReflectionProvider;
import net.vidageek.mirror.provider.ReflectionProvider;
import net.vidageek.mirror.set.dsl.FieldSetter;

/* loaded from: classes4.dex */
public final class FieldSetterByField implements FieldSetter {
    private final Class<?> clazz;
    private final Field field;
    private final ReflectionProvider provider;
    private final Object target;

    public FieldSetterByField(ReflectionProvider reflectionProvider, Object obj, Class<?> cls, Field field) {
        if (cls != null) {
            if (field == null) {
                throw new IllegalArgumentException("field cannot be null");
            }
            if (!field.getDeclaringClass().isAssignableFrom(cls)) {
                throw new IllegalArgumentException("field declaring class (" + field.getDeclaringClass().getName() + ") doesn't match clazz " + cls.getName());
            }
            this.provider = reflectionProvider;
            this.target = obj;
            this.clazz = cls;
            this.field = field;
            return;
        }
        throw new IllegalArgumentException("clazz cannot be null");
    }

    @Override // net.vidageek.mirror.set.dsl.FieldSetter
    public void withValue(Object obj) {
        if (this.target == null && !Modifier.isStatic(this.field.getModifiers())) {
            throw new MirrorException("attempt to set instance field " + this.field.getName() + " on class " + this.clazz.getName());
        } else if (obj == null && this.field.getType().isPrimitive()) {
            throw new IllegalArgumentException("cannot set null value on primitive field");
        } else {
            if (obj != null && MatchType.DONT_MATCH.equals(new ClassArrayMatcher(obj.getClass()).match(this.field.getType()))) {
                throw new IllegalArgumentException("Value of type " + obj.getClass() + " cannot be set on field " + this.field.getName() + " of type " + this.field.getType() + " from class " + this.clazz.getName() + ". Incompatible types");
            }
            FieldReflectionProvider fieldReflectionProvider = this.provider.getFieldReflectionProvider(this.target, this.clazz, this.field);
            fieldReflectionProvider.setAccessible();
            fieldReflectionProvider.setValue(obj);
        }
    }
}
