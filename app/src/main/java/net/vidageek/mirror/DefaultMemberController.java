package net.vidageek.mirror;

import java.lang.reflect.AnnotatedElement;
import net.vidageek.mirror.dsl.MemberController;
import net.vidageek.mirror.provider.ReflectionProvider;
import net.vidageek.mirror.reflect.DefaultAllMemberHandler;
import net.vidageek.mirror.reflect.DefaultMemberHandler;
import net.vidageek.mirror.reflect.dsl.AllMemberHandler;
import net.vidageek.mirror.reflect.dsl.MemberHandler;

/* loaded from: classes4.dex */
public final class DefaultMemberController implements MemberController {
    private final AnnotatedElement member;
    private final ReflectionProvider provider;

    public DefaultMemberController(ReflectionProvider reflectionProvider, AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            throw new IllegalArgumentException("Argument member cannot be null");
        }
        this.provider = reflectionProvider;
        this.member = annotatedElement;
    }

    @Override // net.vidageek.mirror.dsl.MemberController
    public AllMemberHandler reflectAll() {
        return new DefaultAllMemberHandler(this.provider, this.member);
    }

    @Override // net.vidageek.mirror.dsl.MemberController
    public MemberHandler reflect() {
        return new DefaultMemberHandler(this.provider, this.member);
    }
}
