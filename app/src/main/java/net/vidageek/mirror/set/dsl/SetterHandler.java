package net.vidageek.mirror.set.dsl;

import java.lang.reflect.Field;

/* loaded from: classes4.dex */
public interface SetterHandler {
    FieldSetter field(String str);

    FieldSetter field(Field field);
}
