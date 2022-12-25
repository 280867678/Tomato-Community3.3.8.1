package net.vidageek.mirror.thirdparty.org.objenesis.instantiator.sun;

import java.lang.reflect.Constructor;
import net.vidageek.mirror.thirdparty.org.objenesis.ObjenesisException;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator;
import sun.reflect.ReflectionFactory;

/* loaded from: classes4.dex */
public class SunReflectionFactoryInstantiator implements ObjectInstantiator {
    static /* synthetic */ Class class$java$lang$Object;
    private final Constructor mungedConstructor;

    public SunReflectionFactoryInstantiator(Class cls) {
        Class cls2;
        ReflectionFactory reflectionFactory = ReflectionFactory.getReflectionFactory();
        try {
            if (class$java$lang$Object == null) {
                cls2 = class$("java.lang.Object");
                class$java$lang$Object = cls2;
            } else {
                cls2 = class$java$lang$Object;
            }
            this.mungedConstructor = reflectionFactory.newConstructorForSerialization(cls, cls2.getConstructor(null));
            this.mungedConstructor.setAccessible(true);
        } catch (NoSuchMethodException unused) {
            throw new Error("Cannot find constructor for java.lang.Object!");
        }
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
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
