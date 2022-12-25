package net.vidageek.mirror.thirdparty.org.objenesis.instantiator.gcj;

import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.vidageek.mirror.thirdparty.org.objenesis.ObjenesisException;

/* loaded from: classes4.dex */
public class GCJInstantiator extends GCJInstantiatorBase {
    static /* synthetic */ Class class$java$lang$Object;

    public GCJInstantiator(Class cls) {
        super(cls);
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // net.vidageek.mirror.thirdparty.org.objenesis.instantiator.gcj.GCJInstantiatorBase, net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator
    public Object newInstance() {
        Class cls;
        try {
            Method method = GCJInstantiatorBase.newObjectMethod;
            ObjectInputStream objectInputStream = GCJInstantiatorBase.dummyStream;
            Object[] objArr = new Object[2];
            objArr[0] = this.type;
            if (class$java$lang$Object == null) {
                cls = class$("java.lang.Object");
                class$java$lang$Object = cls;
            } else {
                cls = class$java$lang$Object;
            }
            objArr[1] = cls;
            return method.invoke(objectInputStream, objArr);
        } catch (IllegalAccessException e) {
            throw new ObjenesisException(e);
        } catch (RuntimeException e2) {
            throw new ObjenesisException(e2);
        } catch (InvocationTargetException e3) {
            throw new ObjenesisException(e3);
        }
    }
}
