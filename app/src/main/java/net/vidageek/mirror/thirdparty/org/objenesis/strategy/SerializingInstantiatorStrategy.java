package net.vidageek.mirror.thirdparty.org.objenesis.strategy;

import java.io.NotSerializableException;
import net.vidageek.mirror.thirdparty.org.objenesis.ObjenesisException;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.basic.ObjectStreamClassInstantiator;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.gcj.GCJSerializationInstantiator;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.perc.PercSerializationInstantiator;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.sun.Sun13SerializationInstantiator;

/* loaded from: classes4.dex */
public class SerializingInstantiatorStrategy extends BaseInstantiatorStrategy {
    static /* synthetic */ Class class$java$io$Serializable;

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // net.vidageek.mirror.thirdparty.org.objenesis.strategy.InstantiatorStrategy
    public ObjectInstantiator newInstantiatorOf(Class cls) {
        Class cls2 = class$java$io$Serializable;
        if (cls2 == null) {
            cls2 = class$("java.io.Serializable");
            class$java$io$Serializable = cls2;
        }
        if (!cls2.isAssignableFrom(cls)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(cls);
            stringBuffer.append(" not serializable");
            throw new ObjenesisException(new NotSerializableException(stringBuffer.toString()));
        }
        if (BaseInstantiatorStrategy.JVM_NAME.startsWith("Java HotSpot")) {
            if (BaseInstantiatorStrategy.VM_VERSION.startsWith("1.3")) {
                return new Sun13SerializationInstantiator(cls);
            }
        } else if (BaseInstantiatorStrategy.JVM_NAME.startsWith("GNU libgcj")) {
            return new GCJSerializationInstantiator(cls);
        } else {
            if (BaseInstantiatorStrategy.JVM_NAME.startsWith("PERC")) {
                return new PercSerializationInstantiator(cls);
            }
        }
        return new ObjectStreamClassInstantiator(cls);
    }
}
