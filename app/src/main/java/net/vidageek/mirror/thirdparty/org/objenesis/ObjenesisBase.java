package net.vidageek.mirror.thirdparty.org.objenesis;

import java.util.HashMap;
import java.util.Map;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator;
import net.vidageek.mirror.thirdparty.org.objenesis.strategy.InstantiatorStrategy;

/* loaded from: classes4.dex */
public class ObjenesisBase implements Objenesis {
    protected Map cache;
    protected final InstantiatorStrategy strategy;

    public ObjenesisBase(InstantiatorStrategy instantiatorStrategy) {
        this(instantiatorStrategy, true);
    }

    public ObjenesisBase(InstantiatorStrategy instantiatorStrategy, boolean z) {
        if (instantiatorStrategy == null) {
            throw new IllegalArgumentException("A strategy can't be null");
        }
        this.strategy = instantiatorStrategy;
        this.cache = z ? new HashMap() : null;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getClass().getName());
        stringBuffer.append(" using ");
        stringBuffer.append(this.strategy.getClass().getName());
        stringBuffer.append(this.cache == null ? " without" : " with");
        stringBuffer.append(" caching");
        return stringBuffer.toString();
    }

    @Override // net.vidageek.mirror.thirdparty.org.objenesis.Objenesis
    public Object newInstance(Class cls) {
        return getInstantiatorOf(cls).newInstance();
    }

    @Override // net.vidageek.mirror.thirdparty.org.objenesis.Objenesis
    public synchronized ObjectInstantiator getInstantiatorOf(Class cls) {
        if (this.cache == null) {
            return this.strategy.newInstantiatorOf(cls);
        }
        ObjectInstantiator objectInstantiator = (ObjectInstantiator) this.cache.get(cls.getName());
        if (objectInstantiator == null) {
            objectInstantiator = this.strategy.newInstantiatorOf(cls);
            this.cache.put(cls.getName(), objectInstantiator);
        }
        return objectInstantiator;
    }
}
