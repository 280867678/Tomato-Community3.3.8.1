package net.vidageek.mirror.thirdparty.org.objenesis.strategy;

import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.gcj.GCJInstantiator;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.jrockit.JRockit131Instantiator;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.jrockit.JRockitLegacyInstantiator;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.perc.PercInstantiator;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.sun.Sun13Instantiator;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.sun.SunReflectionFactoryInstantiator;

/* loaded from: classes4.dex */
public class StdInstantiatorStrategy extends BaseInstantiatorStrategy {
    @Override // net.vidageek.mirror.thirdparty.org.objenesis.strategy.InstantiatorStrategy
    public ObjectInstantiator newInstantiatorOf(Class cls) {
        String str;
        if (BaseInstantiatorStrategy.JVM_NAME.startsWith("Java HotSpot")) {
            if (BaseInstantiatorStrategy.VM_VERSION.startsWith("1.3")) {
                return new Sun13Instantiator(cls);
            }
        } else if (BaseInstantiatorStrategy.JVM_NAME.startsWith("BEA")) {
            if (BaseInstantiatorStrategy.VM_VERSION.startsWith("1.3")) {
                return new JRockit131Instantiator(cls);
            }
            if (BaseInstantiatorStrategy.VM_VERSION.startsWith("1.4") && !BaseInstantiatorStrategy.VENDOR_VERSION.startsWith("R") && ((str = BaseInstantiatorStrategy.VM_INFO) == null || !str.startsWith("R25.1") || !BaseInstantiatorStrategy.VM_INFO.startsWith("R25.2"))) {
                return new JRockitLegacyInstantiator(cls);
            }
        } else if (BaseInstantiatorStrategy.JVM_NAME.startsWith("GNU libgcj")) {
            return new GCJInstantiator(cls);
        } else {
            if (BaseInstantiatorStrategy.JVM_NAME.startsWith("PERC")) {
                return new PercInstantiator(cls);
            }
        }
        return new SunReflectionFactoryInstantiator(cls);
    }
}
