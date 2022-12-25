package org.xutils.common.task;

import java.util.concurrent.Executor;
import org.xutils.common.Callback;

/* loaded from: classes4.dex */
public abstract class AbsTask<ResultType> implements Callback.Cancelable {
    private final Callback.Cancelable cancelHandler;
    private volatile boolean isCancelled;
    private ResultType result;
    private volatile State state;
    private TaskProxy taskProxy;

    protected void cancelWorks() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract ResultType doBackground() throws Throwable;

    public Executor getExecutor() {
        return null;
    }

    public Priority getPriority() {
        return null;
    }

    protected boolean isCancelFast() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCancelled(Callback.CancelledException cancelledException) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void onError(Throwable th, boolean z);

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFinished() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onStarted() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void onSuccess(ResultType resulttype);

    /* JADX INFO: Access modifiers changed from: protected */
    public void onUpdate(int i, Object... objArr) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWaiting() {
    }

    public AbsTask() {
        this(null);
    }

    public AbsTask(Callback.Cancelable cancelable) {
        this.taskProxy = null;
        this.isCancelled = false;
        this.state = State.IDLE;
        this.cancelHandler = cancelable;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void update(int i, Object... objArr) {
        TaskProxy taskProxy = this.taskProxy;
        if (taskProxy != null) {
            taskProxy.onUpdate(i, objArr);
        }
    }

    @Override // org.xutils.common.Callback.Cancelable
    public final synchronized void cancel() {
        if (!this.isCancelled) {
            this.isCancelled = true;
            cancelWorks();
            if (this.cancelHandler != null && !this.cancelHandler.isCancelled()) {
                this.cancelHandler.cancel();
            }
            if (this.state == State.WAITING || (this.state == State.STARTED && isCancelFast())) {
                if (this.taskProxy != null) {
                    this.taskProxy.onCancelled(new Callback.CancelledException("cancelled by user"));
                    this.taskProxy.onFinished();
                } else if (this instanceof TaskProxy) {
                    onCancelled(new Callback.CancelledException("cancelled by user"));
                    onFinished();
                }
            }
        }
    }

    @Override // org.xutils.common.Callback.Cancelable
    public final boolean isCancelled() {
        Callback.Cancelable cancelable;
        return this.isCancelled || this.state == State.CANCELLED || ((cancelable = this.cancelHandler) != null && cancelable.isCancelled());
    }

    public final boolean isFinished() {
        return this.state.value() > State.STARTED.value();
    }

    public final State getState() {
        return this.state;
    }

    public final ResultType getResult() {
        return this.result;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setState(State state) {
        this.state = state;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void setTaskProxy(TaskProxy taskProxy) {
        this.taskProxy = taskProxy;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void setResult(ResultType resulttype) {
        this.result = resulttype;
    }

    /* loaded from: classes4.dex */
    public enum State {
        IDLE(0),
        WAITING(1),
        STARTED(2),
        SUCCESS(3),
        CANCELLED(4),
        ERROR(5);
        
        private final int value;

        State(int i) {
            this.value = i;
        }

        public int value() {
            return this.value;
        }
    }
}
