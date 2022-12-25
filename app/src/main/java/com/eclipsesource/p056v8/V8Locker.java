package com.eclipsesource.p056v8;

/* renamed from: com.eclipsesource.v8.V8Locker */
/* loaded from: classes2.dex */
public class V8Locker {
    private C1257V8 runtime;
    private Thread thread = null;
    private boolean released = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    public V8Locker(C1257V8 c1257v8) {
        this.runtime = c1257v8;
        acquire();
    }

    public Thread getThread() {
        return this.thread;
    }

    public synchronized void acquire() {
        if (this.thread != null && this.thread != Thread.currentThread()) {
            throw new Error("Invalid V8 thread access: current thread is " + Thread.currentThread() + " while the locker has thread " + this.thread);
        }
        if (this.thread == Thread.currentThread()) {
            return;
        }
        this.runtime.acquireLock(this.runtime.getV8RuntimePtr());
        this.thread = Thread.currentThread();
        this.released = false;
    }

    public synchronized boolean tryAcquire() {
        if (this.thread == null || this.thread == Thread.currentThread()) {
            if (this.thread == Thread.currentThread()) {
                return true;
            }
            this.runtime.acquireLock(this.runtime.getV8RuntimePtr());
            this.thread = Thread.currentThread();
            this.released = false;
            return true;
        }
        return false;
    }

    public synchronized void release() {
        if ((!this.released || this.thread != null) && !this.runtime.isReleased()) {
            checkThread();
            this.runtime.releaseLock(this.runtime.getV8RuntimePtr());
            this.thread = null;
            this.released = true;
        }
    }

    public void checkThread() {
        if (this.released && this.thread == null) {
            throw new Error("Invalid V8 thread access: the locker has been released!");
        }
        if (this.thread == Thread.currentThread()) {
            return;
        }
        throw new Error("Invalid V8 thread access: current thread is " + Thread.currentThread() + " while the locker has thread " + this.thread);
    }

    public boolean hasLock() {
        return this.thread == Thread.currentThread();
    }
}
