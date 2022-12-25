package com.facebook.common.executors;

import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public abstract class StatefulRunnable<T> implements Runnable {
    protected final AtomicInteger mState = new AtomicInteger(0);

    protected abstract void disposeResult(T t);

    /* renamed from: getResult */
    protected abstract T mo5961getResult() throws Exception;

    protected abstract void onCancellation();

    protected abstract void onFailure(Exception exc);

    protected abstract void onSuccess(T t);

    @Override // java.lang.Runnable
    public final void run() {
        if (!this.mState.compareAndSet(0, 1)) {
            return;
        }
        try {
            T mo5961getResult = mo5961getResult();
            this.mState.set(3);
            try {
                onSuccess(mo5961getResult);
            } finally {
                disposeResult(mo5961getResult);
            }
        } catch (Exception e) {
            this.mState.set(4);
            onFailure(e);
        }
    }

    public void cancel() {
        if (this.mState.compareAndSet(0, 2)) {
            onCancellation();
        }
    }
}
