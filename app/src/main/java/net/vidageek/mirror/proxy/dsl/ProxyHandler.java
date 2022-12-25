package net.vidageek.mirror.proxy.dsl;

/* loaded from: classes4.dex */
public interface ProxyHandler<T> {
    T interceptingWith(MethodInterceptor... methodInterceptorArr);
}
