package net.vidageek.mirror.provider.java;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.List;
import net.vidageek.mirror.provider.AnnotatedElementReflectionProvider;

/* loaded from: classes4.dex */
public final class PureJavaAnnotatedElementReflectionProvider implements AnnotatedElementReflectionProvider {
    private final AnnotatedElement element;

    public PureJavaAnnotatedElementReflectionProvider(AnnotatedElement annotatedElement) {
        this.element = annotatedElement;
    }

    @Override // net.vidageek.mirror.provider.AnnotatedElementReflectionProvider
    public List<Annotation> getAnnotations() {
        return Arrays.asList(this.element.getAnnotations());
    }

    @Override // net.vidageek.mirror.provider.AnnotatedElementReflectionProvider
    public <T extends Annotation> T getAnnotation(Class<T> cls) {
        return (T) this.element.getAnnotation(cls);
    }
}
