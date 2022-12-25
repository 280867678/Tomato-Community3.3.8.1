package net.vidageek.mirror.thirdparty.org.objenesis.instantiator.gcj;

import net.vidageek.mirror.thirdparty.org.objenesis.ObjenesisException;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.SerializationInstantiatorHelper;

/* loaded from: classes4.dex */
public class GCJSerializationInstantiator extends GCJInstantiatorBase {
    private Class superType;

    public GCJSerializationInstantiator(Class cls) {
        super(cls);
        this.superType = SerializationInstantiatorHelper.getNonSerializableSuperClass(cls);
    }

    @Override // net.vidageek.mirror.thirdparty.org.objenesis.instantiator.gcj.GCJInstantiatorBase, net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator
    public Object newInstance() {
        try {
            return GCJInstantiatorBase.newObjectMethod.invoke(GCJInstantiatorBase.dummyStream, this.type, this.superType);
        } catch (Exception e) {
            throw new ObjenesisException(e);
        }
    }
}
