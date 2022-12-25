package net.vidageek.mirror.provider.java;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.vidageek.mirror.exception.ReflectionProviderException;
import net.vidageek.mirror.provider.MethodReflectionProvider;

/* loaded from: classes4.dex */
public final class PureJavaMethodReflectionProvider implements MethodReflectionProvider {
    private final Method method;
    private final Object target;

    public PureJavaMethodReflectionProvider(Object obj, Class<?> cls, Method method) {
        this.target = obj;
        this.method = method;
    }

    @Override // net.vidageek.mirror.provider.MethodReflectionProvider
    public Class<?>[] getParameters() {
        return this.method.getParameterTypes();
    }

    @Override // net.vidageek.mirror.provider.ReflectionElementReflectionProvider
    public void setAccessible() {
        this.method.setAccessible(true);
    }

    @Override // net.vidageek.mirror.provider.MethodReflectionProvider
    public Object invoke(Object[] objArr) {
        try {
            setAccessible();
            return this.method.invoke(this.target, objArr);
        } catch (IllegalAccessException e) {
            throw new ReflectionProviderException("Could not invoke method " + this.method.getName(), e);
        } catch (IllegalArgumentException e2) {
            throw new ReflectionProviderException("Could not invoke method " + this.method.getName(), e2);
        } catch (NullPointerException e3) {
            throw new ReflectionProviderException("Attempt to call an instance method ( " + this.method.getName() + ") on a null object.", e3);
        } catch (InvocationTargetException e4) {
            String str = "Could not invoke method " + this.method.getName();
            Throwable cause = e4.getCause();
            Throwable th = e4;
            if (cause != null) {
                th = e4.getCause();
            }
            throw new ReflectionProviderException(str, th);
        }
    }
}
