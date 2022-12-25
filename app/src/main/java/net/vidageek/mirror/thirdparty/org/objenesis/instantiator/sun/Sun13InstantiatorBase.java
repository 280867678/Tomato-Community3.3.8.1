package net.vidageek.mirror.thirdparty.org.objenesis.instantiator.sun;

import java.lang.reflect.Method;
import net.vidageek.mirror.thirdparty.org.objenesis.ObjenesisException;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator;

/* loaded from: classes4.dex */
public abstract class Sun13InstantiatorBase implements ObjectInstantiator {
    protected static Method allocateNewObjectMethod;
    static /* synthetic */ Class class$java$io$ObjectInputStream;
    static /* synthetic */ Class class$java$lang$Class;
    protected final Class type;

    @Override // net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator
    public abstract Object newInstance();

    private static void initialize() {
        Class cls;
        Class<?> cls2;
        Class<?> cls3;
        if (allocateNewObjectMethod == null) {
            try {
                if (class$java$io$ObjectInputStream == null) {
                    cls = class$("java.io.ObjectInputStream");
                    class$java$io$ObjectInputStream = cls;
                } else {
                    cls = class$java$io$ObjectInputStream;
                }
                Class<?>[] clsArr = new Class[2];
                if (class$java$lang$Class == null) {
                    cls2 = class$("java.lang.Class");
                    class$java$lang$Class = cls2;
                } else {
                    cls2 = class$java$lang$Class;
                }
                clsArr[0] = cls2;
                if (class$java$lang$Class == null) {
                    cls3 = class$("java.lang.Class");
                    class$java$lang$Class = cls3;
                } else {
                    cls3 = class$java$lang$Class;
                }
                clsArr[1] = cls3;
                allocateNewObjectMethod = cls.getDeclaredMethod("allocateNewObject", clsArr);
                allocateNewObjectMethod.setAccessible(true);
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

    public Sun13InstantiatorBase(Class cls) {
        this.type = cls;
        initialize();
    }
}
