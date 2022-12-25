package net.vidageek.mirror.provider.java;

import java.lang.reflect.ParameterizedType;
import net.vidageek.mirror.exception.MirrorException;
import net.vidageek.mirror.provider.GenericTypeAccessor;
import net.vidageek.mirror.provider.ParameterizedElementReflectionProvider;

/* loaded from: classes4.dex */
public class PureJavaParameterizedElementReflectionProvider implements ParameterizedElementReflectionProvider {
    private final GenericTypeAccessor accessor;

    public PureJavaParameterizedElementReflectionProvider(GenericTypeAccessor genericTypeAccessor) {
        this.accessor = genericTypeAccessor;
    }

    @Override // net.vidageek.mirror.provider.ParameterizedElementReflectionProvider
    public Class<?> getTypeAtPosition(int i) {
        try {
            try {
                return (Class) ((ParameterizedType) this.accessor.getGenericTypes()).getActualTypeArguments()[i];
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new MirrorException(String.format("No type declared at position %d.", Integer.valueOf(i)));
            }
        } catch (ClassCastException e) {
            throw new MirrorException("Element is not parameterized with a generic type.", e);
        }
    }
}
