package net.vidageek.mirror.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import net.vidageek.mirror.provider.ReflectionProvider;
import net.vidageek.mirror.reflect.dsl.MemberHandler;

/* loaded from: classes4.dex */
public final class DefaultMemberHandler implements MemberHandler {
    private final AnnotatedElement member;
    private final ReflectionProvider provider;

    public DefaultMemberHandler(ReflectionProvider reflectionProvider, AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            throw new IllegalArgumentException("Argument member cannot be null");
        }
        this.provider = reflectionProvider;
        this.member = annotatedElement;
    }

    @Override // net.vidageek.mirror.reflect.dsl.MemberHandler
    public <T extends Annotation> T annotation(Class<T> cls) {
        return (T) this.provider.getAnnotatedElementReflectionProvider(this.member).getAnnotation(cls);
    }
}
