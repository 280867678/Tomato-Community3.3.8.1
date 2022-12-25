package net.vidageek.mirror.thirdparty.org.objenesis.instantiator.sun;

import net.vidageek.mirror.thirdparty.org.objenesis.ObjenesisException;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.SerializationInstantiatorHelper;

/* loaded from: classes4.dex */
public class Sun13SerializationInstantiator extends Sun13InstantiatorBase {
    private final Class superType;

    public Sun13SerializationInstantiator(Class cls) {
        super(cls);
        this.superType = SerializationInstantiatorHelper.getNonSerializableSuperClass(cls);
    }

    @Override // net.vidageek.mirror.thirdparty.org.objenesis.instantiator.sun.Sun13InstantiatorBase, net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator
    public Object newInstance() {
        try {
            return Sun13InstantiatorBase.allocateNewObjectMethod.invoke(null, this.type, this.superType);
        } catch (Exception e) {
            throw new ObjenesisException(e);
        }
    }
}
