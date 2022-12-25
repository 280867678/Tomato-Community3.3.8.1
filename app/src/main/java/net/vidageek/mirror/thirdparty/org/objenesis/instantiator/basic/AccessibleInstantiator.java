package net.vidageek.mirror.thirdparty.org.objenesis.instantiator.basic;

import java.lang.reflect.Constructor;

/* loaded from: classes4.dex */
public class AccessibleInstantiator extends ConstructorInstantiator {
    public AccessibleInstantiator(Class cls) {
        super(cls);
        Constructor constructor = this.constructor;
        if (constructor != null) {
            constructor.setAccessible(true);
        }
    }
}
