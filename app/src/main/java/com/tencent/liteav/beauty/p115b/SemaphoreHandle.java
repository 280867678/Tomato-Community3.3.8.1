package com.tencent.liteav.beauty.p115b;

/* renamed from: com.tencent.liteav.beauty.b.a */
/* loaded from: classes3.dex */
public class SemaphoreHandle {

    /* renamed from: a */
    private boolean f2911a = false;

    public synchronized void a() {
        this.f2911a = true;
        notify();
    }

    public synchronized void b() throws InterruptedException {
        while (!this.f2911a) {
            wait();
        }
        this.f2911a = false;
    }
}
