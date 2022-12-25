package net.vidageek.mirror.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import net.vidageek.mirror.proxy.dsl.MethodInterceptor;

/* loaded from: classes4.dex */
public class DynamicInvocationHadler implements InvocationHandler {
    private final MethodInterceptor[] interceptors;

    public DynamicInvocationHadler(MethodInterceptor... methodInterceptorArr) {
        this.interceptors = methodInterceptorArr;
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
        MethodInterceptor[] methodInterceptorArr;
        for (MethodInterceptor methodInterceptor : this.interceptors) {
            if (methodInterceptor.accepts(method)) {
                return methodInterceptor.intercepts(obj, method, objArr);
            }
        }
        throw new Exception(method.getName() + " was not intercepted");
    }
}
