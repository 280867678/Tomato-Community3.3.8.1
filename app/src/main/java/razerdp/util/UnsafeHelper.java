package razerdp.util;

import java.lang.reflect.Field;

/* loaded from: classes4.dex */
public class UnsafeHelper {
    private static Object unSafe;

    static {
        creteUnSafe();
    }

    private static void creteUnSafe() {
        try {
            Field declaredField = Class.forName("sun.misc.Unsafe").getDeclaredField("theUnsafe");
            declaredField.setAccessible(true);
            unSafe = declaredField.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void checkUnSafe() throws NullPointerException {
        if (unSafe != null) {
            return;
        }
        throw new NullPointerException("unsafe对象为空");
    }

    public static long objectFieldOffset(Field field) throws Exception {
        checkUnSafe();
        return ((Long) unSafe.getClass().getMethod("objectFieldOffset", Field.class).invoke(unSafe, field)).longValue();
    }

    public static void putObject(Object obj, long j, Object obj2) throws Exception {
        checkUnSafe();
        unSafe.getClass().getMethod("putObject", Object.class, Long.TYPE, Object.class).invoke(unSafe, obj, Long.valueOf(j), obj2);
    }
}
