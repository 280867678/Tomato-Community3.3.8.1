package net.vidageek.mirror.proxy.dsl;

import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public interface MethodInterceptor {
    boolean accepts(Method method);

    Object intercepts(Object obj, Method method, Object... objArr);
}
