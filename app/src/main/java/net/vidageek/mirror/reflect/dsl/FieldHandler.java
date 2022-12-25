package net.vidageek.mirror.reflect.dsl;

import java.lang.annotation.Annotation;

/* loaded from: classes4.dex */
public interface FieldHandler {
    <T extends Annotation> T annotation(Class<T> cls);

    ParameterizedElementHandler genericType();
}
