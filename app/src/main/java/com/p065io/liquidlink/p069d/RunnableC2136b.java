package com.p065io.liquidlink.p069d;

import com.p065io.liquidlink.p070e.HandlerC2154p;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.io.liquidlink.d.b */
/* loaded from: classes3.dex */
public class RunnableC2136b implements Runnable {

    /* renamed from: a */
    final /* synthetic */ C2135a f1400a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RunnableC2136b(C2135a c2135a) {
        this.f1400a = c2135a;
    }

    /* JADX WARN: Incorrect condition in loop: B:2:0x0006 */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void run() {
        boolean z;
        LinkedBlockingQueue linkedBlockingQueue;
        HandlerC2154p handlerC2154p;
        while (z) {
            try {
                linkedBlockingQueue = this.f1400a.f1393a;
                linkedBlockingQueue.poll(10L, TimeUnit.SECONDS);
                handlerC2154p = this.f1400a.f1399h;
                handlerC2154p.m3999g();
            } catch (InterruptedException unused) {
            }
        }
    }
}
