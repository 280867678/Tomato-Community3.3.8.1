package net.vidageek.mirror.invoke;

import java.lang.reflect.Method;
import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.exception.MirrorException;
import net.vidageek.mirror.invoke.dsl.MethodHandler;
import net.vidageek.mirror.provider.ReflectionProvider;

/* loaded from: classes4.dex */
public final class MethodHandlerByName implements MethodHandler {
    private final Class<?> clazz;
    private final String methodName;
    private final ReflectionProvider provider;
    private final Object target;

    public MethodHandlerByName(ReflectionProvider reflectionProvider, Object obj, Class<?> cls, String str) {
        if (cls == null) {
            throw new IllegalArgumentException("clazz can't be null");
        }
        if (str == null || str.trim().length() == 0) {
            throw new IllegalArgumentException("methodName can't be null");
        }
        this.provider = reflectionProvider;
        this.target = obj;
        this.clazz = cls;
        this.methodName = str;
    }

    @Override // net.vidageek.mirror.invoke.dsl.MethodHandler
    public Object withoutArgs() {
        return withArgs(new Object[0]);
    }

    @Override // net.vidageek.mirror.invoke.dsl.MethodHandler
    public Object withArgs(Object... objArr) {
        return new MethodHandlerByMethod(this.provider, this.target, this.clazz, getMethod(objArr)).withArgs(objArr);
    }

    private Method getMethod(Object[] objArr) {
        int length = objArr == null ? 0 : objArr.length;
        Class<?>[] clsArr = new Class[length];
        for (int i = 0; i < length; i++) {
            if (objArr[i] == null) {
                throw new IllegalArgumentException("Cannot invoke a method by name if one of it's arguments is null. First reflect the method.");
            }
            clsArr[i] = objArr[i].getClass();
        }
        Method withArgs = new Mirror(this.provider).m78on((Class) this.clazz).reflect().method(this.methodName).withArgs(clsArr);
        if (withArgs != null) {
            return withArgs;
        }
        throw new MirrorException("Could not find method " + this.methodName + " on class " + this.clazz.getName());
    }
}
