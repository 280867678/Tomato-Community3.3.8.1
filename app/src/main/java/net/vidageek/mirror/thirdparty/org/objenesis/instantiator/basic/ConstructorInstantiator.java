package net.vidageek.mirror.thirdparty.org.objenesis.instantiator.basic;

import java.lang.reflect.Constructor;
import net.vidageek.mirror.thirdparty.org.objenesis.ObjenesisException;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator;

/* loaded from: classes4.dex */
public class ConstructorInstantiator implements ObjectInstantiator {
    protected Constructor constructor;

    public ConstructorInstantiator(Class cls) {
        try {
            this.constructor = cls.getDeclaredConstructor(null);
        } catch (Exception e) {
            throw new ObjenesisException(e);
        }
    }

    @Override // net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator
    public Object newInstance() {
        try {
            return this.constructor.newInstance(null);
        } catch (Exception e) {
            throw new ObjenesisException(e);
        }
    }
}
