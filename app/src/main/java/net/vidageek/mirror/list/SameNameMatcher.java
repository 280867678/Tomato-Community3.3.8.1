package net.vidageek.mirror.list;

import java.lang.reflect.Method;
import net.vidageek.mirror.list.dsl.Matcher;

/* loaded from: classes4.dex */
public final class SameNameMatcher implements Matcher<Method> {
    private final String methodName;

    public SameNameMatcher(String str) {
        this.methodName = str;
    }

    @Override // net.vidageek.mirror.list.dsl.Matcher
    public boolean accepts(Method method) {
        return method.getName().equals(this.methodName);
    }
}
