package com.p065io.liquidlink.p070e;

import android.support.annotation.NonNull;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.io.liquidlink.e.b */
/* loaded from: classes3.dex */
class ThreadFactoryC2140b implements ThreadFactory {

    /* renamed from: a */
    AtomicInteger f1406a = new AtomicInteger(1);

    /* JADX INFO: Access modifiers changed from: package-private */
    public ThreadFactoryC2140b(HandlerC2139a handlerC2139a) {
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(@NonNull Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setName("pool-core-t" + this.f1406a.getAndIncrement());
        return thread;
    }
}
