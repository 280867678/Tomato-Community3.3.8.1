package net.vidageek.mirror.provider;

import java.lang.annotation.Annotation;
import java.util.List;

/* loaded from: classes4.dex */
public interface AnnotatedElementReflectionProvider {
    <T extends Annotation> T getAnnotation(Class<T> cls);

    List<Annotation> getAnnotations();
}
