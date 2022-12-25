package net.vidageek.mirror.provider.java;

import net.vidageek.mirror.exception.ReflectionProviderException;
import net.vidageek.mirror.provider.ConstructorBypassingReflectionProvider;
import net.vidageek.mirror.thirdparty.org.objenesis.ObjenesisException;
import net.vidageek.mirror.thirdparty.org.objenesis.ObjenesisStd;

/* loaded from: classes4.dex */
public final class ObjenesisConstructorBypassingReflectionProvider<T> implements ConstructorBypassingReflectionProvider<T> {
    private final Class<T> clazz;

    public ObjenesisConstructorBypassingReflectionProvider(Class<T> cls) {
        this.clazz = cls;
    }

    @Override // net.vidageek.mirror.provider.ConstructorBypassingReflectionProvider
    public T bypassConstructor() {
        try {
            return (T) new ObjenesisStd().getInstantiatorOf(this.clazz).newInstance();
        } catch (ObjenesisException e) {
            throw new ReflectionProviderException("could not instantiate without using a constructor. Maybe your VM is not supported by Objenesis. Please check http://code.google.com/p/objenesis/wiki/ListOfCurrentlySupportedVMs.", e);
        }
    }
}
