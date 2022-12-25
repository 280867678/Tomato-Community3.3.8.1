package net.vidageek.mirror.thirdparty.org.objenesis.instantiator.perc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.vidageek.mirror.thirdparty.org.objenesis.ObjenesisException;
import net.vidageek.mirror.thirdparty.org.objenesis.instantiator.ObjectInstantiator;

/* loaded from: classes4.dex */
public class PercSerializationInstantiator implements ObjectInstantiator {
    static /* synthetic */ Class class$java$io$ObjectInputStream;
    static /* synthetic */ Class class$java$io$Serializable;
    static /* synthetic */ Class class$java$lang$Class;
    static /* synthetic */ Class class$java$lang$Object;
    static /* synthetic */ Class class$java$lang$String;
    private final Method newInstanceMethod;
    private Object[] typeArgs;

    /* JADX WARN: Multi-variable type inference failed */
    public PercSerializationInstantiator(Class cls) {
        Class cls2;
        Class<?> cls3;
        Class<?> cls4;
        Class<?> cls5;
        Class<?> cls6;
        Class cls7 = cls;
        while (true) {
            Class cls8 = class$java$io$Serializable;
            Class cls9 = cls8;
            if (cls8 == null) {
                Class class$ = class$("java.io.Serializable");
                class$java$io$Serializable = class$;
                cls9 = class$;
            }
            if (cls9.isAssignableFrom(cls7)) {
                cls7 = cls7.getSuperclass();
            } else {
                try {
                    break;
                } catch (ClassNotFoundException e) {
                    throw new ObjenesisException(e);
                } catch (IllegalAccessException e2) {
                    throw new ObjenesisException(e2);
                } catch (NoSuchMethodException e3) {
                    throw new ObjenesisException(e3);
                } catch (InvocationTargetException e4) {
                    throw new ObjenesisException(e4);
                }
            }
        }
        Class<?> cls10 = Class.forName("COM.newmonics.PercClassLoader.Method");
        if (class$java$io$ObjectInputStream == null) {
            cls2 = class$("java.io.ObjectInputStream");
            class$java$io$ObjectInputStream = cls2;
        } else {
            cls2 = class$java$io$ObjectInputStream;
        }
        Class<?>[] clsArr = new Class[3];
        if (class$java$lang$Class == null) {
            cls3 = class$("java.lang.Class");
            class$java$lang$Class = cls3;
        } else {
            cls3 = class$java$lang$Class;
        }
        clsArr[0] = cls3;
        if (class$java$lang$Object == null) {
            cls4 = class$("java.lang.Object");
            class$java$lang$Object = cls4;
        } else {
            cls4 = class$java$lang$Object;
        }
        clsArr[1] = cls4;
        clsArr[2] = cls10;
        this.newInstanceMethod = cls2.getDeclaredMethod("noArgConstruct", clsArr);
        this.newInstanceMethod.setAccessible(true);
        Class<?> cls11 = Class.forName("COM.newmonics.PercClassLoader.PercClass");
        Class<?>[] clsArr2 = new Class[1];
        if (class$java$lang$Class == null) {
            cls5 = class$("java.lang.Class");
            class$java$lang$Class = cls5;
        } else {
            cls5 = class$java$lang$Class;
        }
        clsArr2[0] = cls5;
        Object invoke = cls11.getDeclaredMethod("getPercClass", clsArr2).invoke(null, cls7);
        Class<?> cls12 = invoke.getClass();
        Class<?>[] clsArr3 = new Class[1];
        if (class$java$lang$String == null) {
            cls6 = class$("java.lang.String");
            class$java$lang$String = cls6;
        } else {
            cls6 = class$java$lang$String;
        }
        clsArr3[0] = cls6;
        this.typeArgs = new Object[]{cls7, cls, cls12.getDeclaredMethod("findMethod", clsArr3).invoke(invoke, "<init>()V")};
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
        } catch (IllegalAccessException e) {
            throw new ObjenesisException(e);
        } catch (InvocationTargetException e2) {
            throw new ObjenesisException(e2);
        }
    }
}
