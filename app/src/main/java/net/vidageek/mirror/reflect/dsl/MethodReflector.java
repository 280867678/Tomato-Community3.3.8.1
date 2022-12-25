package net.vidageek.mirror.reflect.dsl;

import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public interface MethodReflector {
    Method withAnyArgs();

    Method withArgs(Class<?>... clsArr);

    Method withoutArgs();
}
