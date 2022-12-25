package com.p065io.liquidlink.p073h;

import android.os.Handler;
import android.os.Looper;
import com.p089pm.liquidlink.p091b.C3049b;
import com.p089pm.liquidlink.p091b.EnumC3050c;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* renamed from: com.io.liquidlink.h.b */
/* loaded from: classes3.dex */
public class RunnableC2171b implements Runnable {

    /* renamed from: a */
    private ThreadPoolExecutor f1482a;

    /* renamed from: b */
    private Callable f1483b;

    /* renamed from: c */
    private AbstractC2170a f1484c;

    /* renamed from: d */
    private long f1485d = 10;

    /* renamed from: e */
    private Handler f1486e = new Handler(Looper.getMainLooper());

    public RunnableC2171b(ThreadPoolExecutor threadPoolExecutor, Callable callable, AbstractC2170a abstractC2170a) {
        this.f1482a = threadPoolExecutor;
        this.f1483b = callable;
        this.f1484c = abstractC2170a;
    }

    /* renamed from: a */
    public void m3923a(long j) {
        this.f1485d = j;
    }

    @Override // java.lang.Runnable
    public void run() {
        C3049b c3049b;
        Future submit = this.f1482a.submit(this.f1483b);
        try {
            c3049b = (C3049b) submit.get(this.f1485d, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            submit.cancel(true);
            C3049b c3049b2 = new C3049b(EnumC3050c.ERROR, -4);
            c3049b2.m3743b("request timeout : " + e.getMessage());
            c3049b = c3049b2;
        } catch (Exception e2) {
            c3049b = new C3049b(EnumC3050c.ERROR, -2);
            c3049b.m3743b("request error : " + e2.getMessage());
        }
        this.f1486e.post(new RunnableC2172c(this, c3049b));
    }
}
