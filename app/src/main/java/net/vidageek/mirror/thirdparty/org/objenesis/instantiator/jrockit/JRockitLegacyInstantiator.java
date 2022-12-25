package net.vidageek.mirror.thirdparty.org.objenesis.instantiator.jrockit;

import java.lang.reflect.Method;
import net.vidageek.mirror.thirdparty.org.objenesis.ObjenesisException;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator;

/* loaded from: classes4.dex */
public class JRockitLegacyInstantiator implements ObjectInstantiator {
    static /* synthetic */ Class class$java$lang$Class;
    private static Method safeAllocObjectMethod;
    private final Class type;

    private static void initialize() {
        Class<?> cls;
        if (safeAllocObjectMethod == null) {
            try {
                Class<?> cls2 = Class.forName("jrockit.vm.MemSystem");
                Class<?>[] clsArr = new Class[1];
                if (class$java$lang$Class == null) {
                    cls = class$("java.lang.Class");
                    class$java$lang$Class = cls;
                } else {
                    cls = class$java$lang$Class;
                }
                clsArr[0] = cls;
                safeAllocObjectMethod = cls2.getDeclaredMethod("safeAllocObject", clsArr);
                safeAllocObjectMethod.setAccessible(true);
            } catch (ClassNotFoundException e) {
                throw new ObjenesisException(e);
            } catch (NoSuchMethodException e2) {
                throw new ObjenesisException(e2);
            } catch (RuntimeException e3) {
                throw new ObjenesisException(e3);
            }
        }
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public JRockitLegacyInstantiator(Class cls) {
        initialize();
        this.type = cls;
    }

    @Override // net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator
    public Object newInstance() {
        try {
            return safeAllocObjectMethod.invoke(null, this.type);
        } catch (Exception e) {
            throw new ObjenesisException(e);
        }
    }
}
