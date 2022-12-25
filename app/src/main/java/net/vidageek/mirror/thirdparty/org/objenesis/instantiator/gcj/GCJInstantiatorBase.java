package net.vidageek.mirror.thirdparty.org.objenesis.instantiator.gcj;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import net.vidageek.mirror.thirdparty.org.objenesis.ObjenesisException;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator;

/* loaded from: classes4.dex */
public abstract class GCJInstantiatorBase implements ObjectInstantiator {
    static /* synthetic */ Class class$java$io$ObjectInputStream;
    static /* synthetic */ Class class$java$lang$Class;
    protected static ObjectInputStream dummyStream;
    protected static Method newObjectMethod;
    protected final Class type;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class DummyStream extends ObjectInputStream {
    }

    @Override // net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator
    public abstract Object newInstance();

    private static void initialize() {
        Class cls;
        Class<?> cls2;
        Class<?> cls3;
        if (newObjectMethod == null) {
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
                newObjectMethod = cls.getDeclaredMethod("newObject", clsArr);
                newObjectMethod.setAccessible(true);
                dummyStream = new DummyStream();
            } catch (IOException e) {
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

    public GCJInstantiatorBase(Class cls) {
        this.type = cls;
        initialize();
    }
}
