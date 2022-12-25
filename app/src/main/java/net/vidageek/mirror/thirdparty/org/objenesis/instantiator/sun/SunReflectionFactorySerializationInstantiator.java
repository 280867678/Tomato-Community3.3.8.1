package net.vidageek.mirror.thirdparty.org.objenesis.instantiator.sun;

import java.io.NotSerializableException;
import java.lang.reflect.Constructor;
import net.vidageek.mirror.thirdparty.org.objenesis.ObjenesisException;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.SerializationInstantiatorHelper;
import sun.reflect.ReflectionFactory;

/* loaded from: classes4.dex */
public class SunReflectionFactorySerializationInstantiator implements ObjectInstantiator {
    private final Constructor mungedConstructor;

    public SunReflectionFactorySerializationInstantiator(Class cls) {
        try {
            this.mungedConstructor = ReflectionFactory.getReflectionFactory().newConstructorForSerialization(cls, SerializationInstantiatorHelper.getNonSerializableSuperClass(cls).getConstructor(null));
            this.mungedConstructor.setAccessible(true);
        } catch (NoSuchMethodException unused) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(cls);
            stringBuffer.append(" has no suitable superclass constructor");
            throw new ObjenesisException(new NotSerializableException(stringBuffer.toString()));
        }
    }

    @Override // net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator
    public Object newInstance() {
        try {
            return this.mungedConstructor.newInstance(null);
        } catch (Exception e) {
            throw new ObjenesisException(e);
        }
    }
}
