package net.vidageek.mirror.thirdparty.org.objenesis.instantiator;

/* loaded from: classes4.dex */
public class SerializationInstantiatorHelper {
    static /* synthetic */ Class class$java$io$Serializable;

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Class getNonSerializableSuperClass(Class cls) {
        do {
            Class cls2 = class$java$io$Serializable;
            Class cls3 = cls2;
            if (cls2 == null) {
                Class class$ = class$("java.io.Serializable");
                class$java$io$Serializable = class$;
                cls3 = class$;
            }
            if (!cls3.isAssignableFrom(cls)) {
                return cls;
            }
            cls = cls.getSuperclass();
        } while (cls != null);
        throw new Error("Bad class hierarchy: No non-serializable parents");
    }
}
