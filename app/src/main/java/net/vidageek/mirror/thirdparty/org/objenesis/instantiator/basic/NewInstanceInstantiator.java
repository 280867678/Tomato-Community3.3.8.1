package net.vidageek.mirror.thirdparty.org.objenesis.instantiator.basic;

import net.vidageek.mirror.thirdparty.org.objenesis.ObjenesisException;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator;

/* loaded from: classes4.dex */
public class NewInstanceInstantiator implements ObjectInstantiator {
    private final Class type;

    public NewInstanceInstantiator(Class cls) {
        this.type = cls;
    }

    @Override // net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator
    public Object newInstance() {
        try {
            return this.type.newInstance();
        } catch (Exception e) {
            throw new ObjenesisException(e);
        }
    }
}
