package com.eclipsesource.p056v8.utils;

import com.eclipsesource.p056v8.C1257V8;

/* renamed from: com.eclipsesource.v8.utils.V8Thread */
/* loaded from: classes2.dex */
public class V8Thread extends Thread {
    private C1257V8 runtime;
    private final V8Runnable target;

    public V8Thread(V8Runnable v8Runnable) {
        this.target = v8Runnable;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        this.runtime = C1257V8.createV8Runtime();
        try {
            this.target.run(this.runtime);
            synchronized (this) {
                if (this.runtime.getLocker().hasLock()) {
                    this.runtime.close();
                    this.runtime = null;
                }
            }
        } catch (Throwable th) {
            synchronized (this) {
                if (this.runtime.getLocker().hasLock()) {
                    this.runtime.close();
                    this.runtime = null;
                }
                throw th;
            }
        }
    }
}
