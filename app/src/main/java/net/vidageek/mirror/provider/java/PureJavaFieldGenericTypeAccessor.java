package net.vidageek.mirror.provider.java;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import net.vidageek.mirror.provider.GenericTypeAccessor;

/* loaded from: classes4.dex */
public class PureJavaFieldGenericTypeAccessor implements GenericTypeAccessor {
    private final Field field;

    public PureJavaFieldGenericTypeAccessor(Field field) {
        if (field == null) {
            throw new IllegalArgumentException("Argument field cannot be null.");
        }
        this.field = field;
    }

    @Override // net.vidageek.mirror.provider.GenericTypeAccessor
    public Type getGenericTypes() {
        return this.field.getGenericType();
    }
}
