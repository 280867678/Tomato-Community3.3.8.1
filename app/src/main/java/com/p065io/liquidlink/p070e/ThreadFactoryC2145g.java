package com.p065io.liquidlink.p070e;

import android.support.annotation.NonNull;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.io.liquidlink.e.g */
/* loaded from: classes3.dex */
class ThreadFactoryC2145g implements ThreadFactory {

    /* renamed from: a */
    AtomicInteger f1413a = new AtomicInteger(1);

    /* JADX INFO: Access modifiers changed from: package-private */
    public ThreadFactoryC2145g(HandlerC2139a handlerC2139a) {
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(@NonNull Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setName("pool-core-f" + this.f1413a.getAndIncrement());
        return thread;
    }
}
