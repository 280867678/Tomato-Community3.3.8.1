package net.vidageek.mirror.reflect.dsl;

import java.lang.annotation.Annotation;

/* loaded from: classes4.dex */
public interface MethodAnnotationHandler<T extends Annotation> {
    T withArgs(Class<?>... clsArr);

    T withoutArgs();
}
