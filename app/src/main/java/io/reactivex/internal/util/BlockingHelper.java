package io.reactivex.internal.util;

import io.reactivex.internal.schedulers.NonBlockingThread;
import io.reactivex.plugins.RxJavaPlugins;

/* loaded from: classes4.dex */
public final class BlockingHelper {
    public static void verifyNonBlocking() {
        if (RxJavaPlugins.isFailOnNonBlockingScheduler()) {
            if (!(Thread.currentThread() instanceof NonBlockingThread) && !RxJavaPlugins.onBeforeBlocking()) {
                return;
            }
            throw new IllegalStateException("Attempt to block on a Scheduler " + Thread.currentThread().getName() + " that doesn't support blocking operators as they may lead to deadlock");
        }
    }
}
