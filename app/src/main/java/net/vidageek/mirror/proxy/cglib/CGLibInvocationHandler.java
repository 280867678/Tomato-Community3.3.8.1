package net.vidageek.mirror.proxy.cglib;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.vidageek.mirror.exception.MethodNonInterceptedException;

/* loaded from: classes4.dex */
public class CGLibInvocationHandler implements MethodInterceptor {
    private final net.vidageek.mirror.proxy.dsl.MethodInterceptor[] interceptors;

    public CGLibInvocationHandler(net.vidageek.mirror.proxy.dsl.MethodInterceptor... methodInterceptorArr) {
        this.interceptors = methodInterceptorArr;
    }

    public Object intercept(Object obj, Method method, Object[] objArr, MethodProxy methodProxy) throws Throwable {
        net.vidageek.mirror.proxy.dsl.MethodInterceptor[] methodInterceptorArr;
        for (net.vidageek.mirror.proxy.dsl.MethodInterceptor methodInterceptor : this.interceptors) {
            if (methodInterceptor.accepts(method)) {
                return methodInterceptor.intercepts(obj, method, objArr);
            }
        }
        throw new MethodNonInterceptedException(method.getName() + " was not intercepted");
    }
}
