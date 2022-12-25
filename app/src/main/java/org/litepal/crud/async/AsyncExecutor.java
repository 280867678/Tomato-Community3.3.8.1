package org.litepal.crud.async;

/* loaded from: classes4.dex */
public abstract class AsyncExecutor {
    private Runnable pendingTask;

    public void submit(Runnable runnable) {
        this.pendingTask = runnable;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void execute() {
        Runnable runnable = this.pendingTask;
        if (runnable != null) {
            new Thread(runnable).start();
        }
    }
}
