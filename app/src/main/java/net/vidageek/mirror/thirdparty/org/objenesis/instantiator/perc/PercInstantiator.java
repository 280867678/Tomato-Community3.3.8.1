package net.vidageek.mirror.thirdparty.org.objenesis.instantiator.perc;

import java.lang.reflect.Method;
import net.vidageek.mirror.thirdparty.org.objenesis.ObjenesisException;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator;

/* loaded from: classes4.dex */
public class PercInstantiator implements ObjectInstantiator {
    static /* synthetic */ Class class$java$io$ObjectInputStream;
    static /* synthetic */ Class class$java$lang$Class;
    private final Method newInstanceMethod;
    private final Object[] typeArgs = {null, Boolean.FALSE};

    public PercInstantiator(Class cls) {
        Class cls2;
        Class<?> cls3;
        this.typeArgs[0] = cls;
        try {
            if (class$java$io$ObjectInputStream == null) {
                cls2 = class$("java.io.ObjectInputStream");
                class$java$io$ObjectInputStream = cls2;
            } else {
                cls2 = class$java$io$ObjectInputStream;
            }
            Class<?>[] clsArr = new Class[2];
            if (class$java$lang$Class == null) {
                cls3 = class$("java.lang.Class");
                class$java$lang$Class = cls3;
            } else {
                cls3 = class$java$lang$Class;
            }
            clsArr[0] = cls3;
            clsArr[1] = Boolean.TYPE;
            this.newInstanceMethod = cls2.getDeclaredMethod("newInstance", clsArr);
            this.newInstanceMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new ObjenesisException(e);
        } catch (RuntimeException e2) {
            throw new ObjenesisException(e2);
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
            return this.newInstanceMethod.invoke(null, this.typeArgs);
        } catch (Exception e) {
            throw new ObjenesisException(e);
        }
    }
}
