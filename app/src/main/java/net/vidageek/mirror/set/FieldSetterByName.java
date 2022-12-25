package net.vidageek.mirror.set;

import java.lang.reflect.Field;
import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.exception.MirrorException;
import net.vidageek.mirror.provider.ReflectionProvider;
import net.vidageek.mirror.set.dsl.FieldSetter;

/* loaded from: classes4.dex */
public final class FieldSetterByName implements FieldSetter {
    private final Class<?> clazz;
    private final String fieldName;
    private final ReflectionProvider provider;
    private final Object target;

    public FieldSetterByName(ReflectionProvider reflectionProvider, String str, Object obj, Class<?> cls) {
        if (str == null || str.trim().length() == 0) {
            throw new IllegalArgumentException("fieldName cannot be null or blank");
        }
        if (cls == null) {
            throw new IllegalArgumentException("clazz cannot be null");
        }
        this.provider = reflectionProvider;
        this.fieldName = str;
        this.target = obj;
        this.clazz = cls;
    }

    @Override // net.vidageek.mirror.set.dsl.FieldSetter
    public void withValue(Object obj) {
        Field field = new Mirror(this.provider).m78on((Class) this.clazz).reflect().field(this.fieldName);
        if (field == null) {
            throw new MirrorException("could not find field " + this.fieldName + " on class " + this.clazz.getName());
        }
        new FieldSetterByField(this.provider, this.target, this.clazz, field).withValue(obj);
    }
}
