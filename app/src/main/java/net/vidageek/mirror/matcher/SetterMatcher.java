package net.vidageek.mirror.matcher;

import java.lang.reflect.Method;
import net.vidageek.mirror.list.dsl.Matcher;

/* loaded from: classes4.dex */
public final class SetterMatcher implements Matcher<Method> {
    @Override // net.vidageek.mirror.list.dsl.Matcher
    public boolean accepts(Method method) {
        return method.getName().startsWith("set") && method.getParameterTypes().length == 1 && method.getReturnType().equals(Void.TYPE);
    }
}
