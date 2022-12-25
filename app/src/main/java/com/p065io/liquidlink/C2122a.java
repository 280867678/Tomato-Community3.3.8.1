package com.p065io.liquidlink;

import com.p089pm.liquidlink.p090a.C3043a;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* renamed from: com.io.liquidlink.a */
/* loaded from: classes3.dex */
public class C2122a {

    /* renamed from: a */
    private volatile C3043a f1366a = null;

    /* renamed from: b */
    private CountDownLatch f1367b = new CountDownLatch(1);

    /* renamed from: c */
    private LinkedBlockingQueue f1368c = new LinkedBlockingQueue(1);

    /* renamed from: d */
    private Object f1369d = new Object();

    /* renamed from: a */
    public void m4104a(C3043a c3043a) {
        this.f1366a = c3043a;
    }

    /* renamed from: a */
    public boolean m4106a() {
        return m4105a(10L);
    }

    /* renamed from: a */
    public boolean m4105a(long j) {
        if (this.f1366a == null || this.f1366a == C3043a.f1801a || this.f1366a == C3043a.f1802b) {
            this.f1368c.offer(this.f1369d);
            try {
                this.f1367b.await(j + 1, TimeUnit.SECONDS);
            } catch (InterruptedException unused) {
            }
        }
        return this.f1366a == C3043a.f1803c;
    }

    /* renamed from: b */
    public C3043a m4103b() {
        return this.f1366a;
    }

    /* renamed from: b */
    public Object m4102b(long j) {
        return this.f1368c.poll(j, TimeUnit.SECONDS);
    }

    /* renamed from: c */
    public void m4101c() {
        this.f1367b.countDown();
    }
}
