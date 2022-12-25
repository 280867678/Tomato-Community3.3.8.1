package com.scwang.smartrefresh.layout.util;

/* loaded from: classes3.dex */
public class DelayedRunnable implements Runnable {
    public long delayMillis;
    private Runnable runnable;

    public DelayedRunnable(Runnable runnable, long j) {
        this.runnable = runnable;
        this.delayMillis = j;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (this.runnable == null) {
                return;
            }
            this.runnable.run();
            this.runnable = null;
        } catch (Throwable th) {
            if (th instanceof NoClassDefFoundError) {
                return;
            }
            th.printStackTrace();
        }
    }
}
