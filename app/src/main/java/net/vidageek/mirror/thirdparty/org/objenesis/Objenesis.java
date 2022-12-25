package net.vidageek.mirror.thirdparty.org.objenesis;

import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator;

/* loaded from: classes4.dex */
public interface Objenesis {
    ObjectInstantiator getInstantiatorOf(Class cls);

    Object newInstance(Class cls);
}
