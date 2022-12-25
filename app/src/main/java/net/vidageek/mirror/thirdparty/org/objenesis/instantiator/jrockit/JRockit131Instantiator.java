package net.vidageek.mirror.thirdparty.org.objenesis.instantiator.jrockit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import net.vidageek.mirror.thirdparty.org.objenesis.ObjenesisException;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator;

/* loaded from: classes4.dex */
public class JRockit131Instantiator implements ObjectInstantiator {
    static /* synthetic */ Class class$java$lang$Class;
    static /* synthetic */ Class class$java$lang$Object;
    static /* synthetic */ Class class$java$lang$reflect$Constructor;
    private static Method newConstructorForSerializationMethod;
    private Constructor mungedConstructor;

    private static void initialize() {
        Class<?> cls;
        Class<?> cls2;
        if (newConstructorForSerializationMethod == null) {
            try {
                Class<?> cls3 = Class.forName("COM.jrockit.reflect.MemberAccess");
                Class<?>[] clsArr = new Class[2];
                if (class$java$lang$reflect$Constructor == null) {
                    cls = class$("java.lang.reflect.Constructor");
                    class$java$lang$reflect$Constructor = cls;
                } else {
                    cls = class$java$lang$reflect$Constructor;
                }
                clsArr[0] = cls;
                if (class$java$lang$Class == null) {
                    cls2 = class$("java.lang.Class");
                    class$java$lang$Class = cls2;
                } else {
                    cls2 = class$java$lang$Class;
                }
                clsArr[1] = cls2;
                newConstructorForSerializationMethod = cls3.getDeclaredMethod("newConstructorForSerialization", clsArr);
                newConstructorForSerializationMethod.setAccessible(true);
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

    public JRockit131Instantiator(Class cls) {
        Class cls2;
        initialize();
        if (newConstructorForSerializationMethod != null) {
            try {
                if (class$java$lang$Object == null) {
                    cls2 = class$("java.lang.Object");
                    class$java$lang$Object = cls2;
                } else {
                    cls2 = class$java$lang$Object;
                }
                try {
                    this.mungedConstructor = (Constructor) newConstructorForSerializationMethod.invoke(null, cls2.getConstructor(null), cls);
                } catch (Exception e) {
                    throw new ObjenesisException(e);
                }
            } catch (NoSuchMethodException unused) {
                throw new Error("Cannot find constructor for java.lang.Object!");
            }
        }
    }

    @Override // net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator
    public Object newInstance() {
        try {
            return this.mungedConstructor.newInstance(null);
        } catch (Exception e) {
            throw new ObjenesisException(e);
        }
    }
}
