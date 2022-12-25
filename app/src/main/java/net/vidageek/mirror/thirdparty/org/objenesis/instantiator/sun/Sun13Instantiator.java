package net.vidageek.mirror.thirdparty.org.objenesis.instantiator.sun;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.vidageek.mirror.thirdparty.org.objenesis.ObjenesisException;

/* loaded from: classes4.dex */
public class Sun13Instantiator extends Sun13InstantiatorBase {
    static /* synthetic */ Class class$java$lang$Object;

    public Sun13Instantiator(Class cls) {
        super(cls);
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // net.vidageek.mirror.thirdparty.org.objenesis.instantiator.sun.Sun13InstantiatorBase, net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator
    public Object newInstance() {
        Class cls;
        try {
            Method method = Sun13InstantiatorBase.allocateNewObjectMethod;
            Object[] objArr = new Object[2];
            objArr[0] = this.type;
            if (class$java$lang$Object == null) {
                cls = class$("java.lang.Object");
                class$java$lang$Object = cls;
            } else {
                cls = class$java$lang$Object;
            }
            objArr[1] = cls;
            return method.invoke(null, objArr);
        } catch (IllegalAccessException e) {
            throw new ObjenesisException(e);
        } catch (RuntimeException e2) {
            throw new ObjenesisException(e2);
        } catch (InvocationTargetException e3) {
            throw new ObjenesisException(e3);
        }
    }
}
