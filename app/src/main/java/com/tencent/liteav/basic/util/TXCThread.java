package com.tencent.liteav.basic.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/* renamed from: com.tencent.liteav.basic.util.c */
/* loaded from: classes3.dex */
public class TXCThread {

    /* renamed from: a */
    private Handler f2752a;

    /* renamed from: b */
    private Looper f2753b;

    /* renamed from: c */
    private boolean f2754c = true;

    /* renamed from: d */
    private Thread f2755d;

    public TXCThread(String str) {
        HandlerThread handlerThread = new HandlerThread(str);
        handlerThread.start();
        this.f2753b = handlerThread.getLooper();
        this.f2752a = new Handler(this.f2753b);
        this.f2755d = handlerThread;
    }

    /* renamed from: a */
    public Handler m2868a() {
        return this.f2752a;
    }

    protected void finalize() throws Throwable {
        if (this.f2754c) {
            this.f2752a.getLooper().quit();
        }
        super.finalize();
    }

    /* renamed from: a */
    public void m2866a(final Runnable runnable) {
        final boolean[] zArr = new boolean[1];
        if (Thread.currentThread().equals(this.f2755d)) {
            runnable.run();
            return;
        }
        synchronized (this.f2752a) {
            zArr[0] = false;
            this.f2752a.post(new Runnable() { // from class: com.tencent.liteav.basic.util.c.1
                @Override // java.lang.Runnable
                public void run() {
                    runnable.run();
                    zArr[0] = true;
                    synchronized (TXCThread.this.f2752a) {
                        TXCThread.this.f2752a.notifyAll();
                    }
                }
            });
            while (!zArr[0]) {
                try {
                    this.f2752a.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* renamed from: b */
    public void m2864b(Runnable runnable) {
        this.f2752a.post(runnable);
    }

    /* renamed from: a */
    public void m2865a(Runnable runnable, long j) {
        this.f2752a.postDelayed(runnable, j);
    }
}
