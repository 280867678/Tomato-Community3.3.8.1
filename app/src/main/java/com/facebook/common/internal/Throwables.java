package com.facebook.common.internal;

/* loaded from: classes2.dex */
public final class Throwables {
    public static <X extends Throwable> void propagateIfInstanceOf(Throwable th, Class<X> cls) throws Throwable {
        if (th == null || !cls.isInstance(th)) {
            return;
        }
        throw cls.cast(th);
    }

    public static void propagateIfPossible(Throwable th) {
        propagateIfInstanceOf(th, Error.class);
        propagateIfInstanceOf(th, RuntimeException.class);
    }

    public static RuntimeException propagate(Throwable th) {
        Preconditions.checkNotNull(th);
        propagateIfPossible(th);
        throw new RuntimeException(th);
    }
}
