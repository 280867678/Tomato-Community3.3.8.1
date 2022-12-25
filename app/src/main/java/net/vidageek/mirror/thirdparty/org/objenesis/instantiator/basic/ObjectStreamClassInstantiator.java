package net.vidageek.mirror.thirdparty.org.objenesis.instantiator.basic;

import java.io.ObjectStreamClass;
import java.lang.reflect.Method;
import net.vidageek.mirror.thirdparty.org.objenesis.ObjenesisException;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator;

/* loaded from: classes4.dex */
public class ObjectStreamClassInstantiator implements ObjectInstantiator {
    static /* synthetic */ Class class$java$io$ObjectStreamClass;
    private static Method newInstanceMethod;
    private final ObjectStreamClass objStreamClass;

    private static void initialize() {
        Class cls;
        if (newInstanceMethod == null) {
            try {
                if (class$java$io$ObjectStreamClass == null) {
                    cls = class$("java.io.ObjectStreamClass");
                    class$java$io$ObjectStreamClass = cls;
                } else {
                    cls = class$java$io$ObjectStreamClass;
                }
                newInstanceMethod = cls.getDeclaredMethod("newInstance", new Class[0]);
                newInstanceMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                throw new ObjenesisException(e);
            } catch (RuntimeException e2) {
                throw new ObjenesisException(e2);
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

    public ObjectStreamClassInstantiator(Class cls) {
        initialize();
        this.objStreamClass = ObjectStreamClass.lookup(cls);
    }

    @Override // net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator
    public Object newInstance() {
        try {
            return newInstanceMethod.invoke(this.objStreamClass, new Object[0]);
        } catch (Exception e) {
            throw new ObjenesisException(e);
        }
    }
}
