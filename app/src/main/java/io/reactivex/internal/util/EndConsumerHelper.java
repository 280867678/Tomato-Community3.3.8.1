package io.reactivex.internal.util;

import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.ProtocolViolationException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class EndConsumerHelper {
    public static boolean validate(Disposable disposable, Disposable disposable2, Class<?> cls) {
        ObjectHelper.requireNonNull(disposable2, "next is null");
        if (disposable != null) {
            disposable2.dispose();
            if (disposable == DisposableHelper.DISPOSED) {
                return false;
            }
            reportDoubleSubscription(cls);
            return false;
        }
        return true;
    }

    public static boolean setOnce(AtomicReference<Disposable> atomicReference, Disposable disposable, Class<?> cls) {
        ObjectHelper.requireNonNull(disposable, "next is null");
        if (!atomicReference.compareAndSet(null, disposable)) {
            disposable.dispose();
            if (atomicReference.get() == DisposableHelper.DISPOSED) {
                return false;
            }
            reportDoubleSubscription(cls);
            return false;
        }
        return true;
    }

    public static String composeMessage(String str) {
        return "It is not allowed to subscribe with a(n) " + str + " multiple times. Please create a fresh instance of " + str + " and subscribe that to the target source instead.";
    }

    public static void reportDoubleSubscription(Class<?> cls) {
        RxJavaPlugins.onError(new ProtocolViolationException(composeMessage(cls.getName())));
    }
}