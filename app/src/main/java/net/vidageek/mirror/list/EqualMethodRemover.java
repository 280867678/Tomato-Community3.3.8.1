package net.vidageek.mirror.list;

import java.lang.reflect.Method;
import net.vidageek.mirror.list.dsl.Matcher;

/* loaded from: classes4.dex */
public final class EqualMethodRemover implements Matcher<Method> {
    private final Method method;

    public EqualMethodRemover(Method method) {
        this.method = method;
    }

    @Override // net.vidageek.mirror.list.dsl.Matcher
    public boolean accepts(Method method) {
        return !sameMethodName(method) && !sameArgs(method);
    }

    private boolean sameArgs(Method method) {
        if (method.getParameterTypes().length != this.method.getParameterTypes().length) {
            return false;
        }
        int i = 0;
        for (Class<?> cls : this.method.getParameterTypes()) {
            if (method.getParameterTypes()[i] != cls) {
                return false;
            }
            i++;
        }
        return true;
    }

    private boolean sameMethodName(Method method) {
        return method.getName().equals(this.method);
    }
}
