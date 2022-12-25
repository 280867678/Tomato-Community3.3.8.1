package net.vidageek.mirror.reflect;

import net.vidageek.mirror.provider.GenericTypeAccessor;
import net.vidageek.mirror.provider.ReflectionProvider;
import net.vidageek.mirror.reflect.dsl.ParameterizedElementHandler;

/* loaded from: classes4.dex */
public class DefaultParameterizedElementHandler implements ParameterizedElementHandler {
    private final GenericTypeAccessor accessor;
    private final ReflectionProvider provider;

    public DefaultParameterizedElementHandler(ReflectionProvider reflectionProvider, GenericTypeAccessor genericTypeAccessor) {
        if (genericTypeAccessor == null) {
            throw new IllegalArgumentException("Argument accessor cannot be null");
        }
        this.provider = reflectionProvider;
        this.accessor = genericTypeAccessor;
    }

    @Override // net.vidageek.mirror.reflect.dsl.ParameterizedElementHandler
    public Class<?> atPosition(int i) {
        return this.provider.getParameterizedElementProvider(this.accessor).getTypeAtPosition(i);
    }
}
