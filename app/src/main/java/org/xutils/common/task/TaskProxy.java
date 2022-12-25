package org.xutils.common.task;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.concurrent.Executor;
import org.xutils.C5540x;
import org.xutils.common.Callback;
import org.xutils.common.task.AbsTask;
import org.xutils.common.util.LogUtil;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class TaskProxy<ResultType> extends AbsTask<ResultType> {
    private volatile boolean callOnCanceled = false;
    private volatile boolean callOnFinished = false;
    private final Executor executor;
    private final AbsTask<ResultType> task;
    static final InternalHandler sHandler = new InternalHandler();
    static final PriorityExecutor sDefaultExecutor = new PriorityExecutor(true);

    /* JADX INFO: Access modifiers changed from: package-private */
    public TaskProxy(AbsTask<ResultType> absTask) {
        super(absTask);
        this.task = absTask;
        this.task.setTaskProxy(this);
        setTaskProxy(null);
        Executor executor = absTask.getExecutor();
        this.executor = executor == null ? sDefaultExecutor : executor;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.xutils.common.task.AbsTask
    public final ResultType doBackground() throws Throwable {
        onWaiting();
        this.executor.execute(new PriorityRunnable(this.task.getPriority(), new Runnable() { // from class: org.xutils.common.task.TaskProxy.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                try {
                    try {
                    } finally {
                        TaskProxy.this.onFinished();
                    }
                } catch (Callback.CancelledException e) {
                    TaskProxy.this.onCancelled(e);
                    return;
                } catch (Throwable th) {
                    TaskProxy.this.onError(th, false);
                    return;
                }
                if (TaskProxy.this.callOnCanceled || TaskProxy.this.isCancelled()) {
                    throw new Callback.CancelledException("");
                }
                TaskProxy.this.onStarted();
                if (!TaskProxy.this.isCancelled()) {
                    TaskProxy.this.task.setResult(TaskProxy.this.task.doBackground());
                    TaskProxy.this.setResult(TaskProxy.this.task.getResult());
                    if (!TaskProxy.this.isCancelled()) {
                        TaskProxy.this.onSuccess(TaskProxy.this.task.getResult());
                        return;
                    }
                    throw new Callback.CancelledException("");
                }
                throw new Callback.CancelledException("");
            }
        }));
        return null;
    }

    @Override // org.xutils.common.task.AbsTask
    protected void onWaiting() {
        setState(AbsTask.State.WAITING);
        sHandler.obtainMessage(1000000001, this).sendToTarget();
    }

    @Override // org.xutils.common.task.AbsTask
    protected void onStarted() {
        setState(AbsTask.State.STARTED);
        sHandler.obtainMessage(1000000002, this).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.xutils.common.task.AbsTask
    public void onSuccess(ResultType resulttype) {
        setState(AbsTask.State.SUCCESS);
        sHandler.obtainMessage(1000000003, this).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.xutils.common.task.AbsTask
    public void onError(Throwable th, boolean z) {
        setState(AbsTask.State.ERROR);
        sHandler.obtainMessage(1000000004, new ArgsObj(this, th)).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.xutils.common.task.AbsTask
    public void onUpdate(int i, Object... objArr) {
        sHandler.obtainMessage(1000000005, i, i, new ArgsObj(this, objArr)).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.xutils.common.task.AbsTask
    public void onCancelled(Callback.CancelledException cancelledException) {
        setState(AbsTask.State.CANCELLED);
        sHandler.obtainMessage(1000000006, new ArgsObj(this, cancelledException)).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.xutils.common.task.AbsTask
    public void onFinished() {
        sHandler.obtainMessage(1000000007, this).sendToTarget();
    }

    @Override // org.xutils.common.task.AbsTask
    final void setState(AbsTask.State state) {
        super.setState(state);
        this.task.setState(state);
    }

    @Override // org.xutils.common.task.AbsTask
    public final Priority getPriority() {
        return this.task.getPriority();
    }

    @Override // org.xutils.common.task.AbsTask
    public final Executor getExecutor() {
        return this.executor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class ArgsObj {
        final Object[] args;
        final TaskProxy taskProxy;

        public ArgsObj(TaskProxy taskProxy, Object... objArr) {
            this.taskProxy = taskProxy;
            this.args = objArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class InternalHandler extends Handler {
        private InternalHandler() {
            super(Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Object[] objArr;
            Object obj = message.obj;
            if (obj == null) {
                throw new IllegalArgumentException("msg must not be null");
            }
            TaskProxy taskProxy = null;
            if (obj instanceof TaskProxy) {
                taskProxy = (TaskProxy) obj;
                objArr = null;
            } else if (obj instanceof ArgsObj) {
                ArgsObj argsObj = (ArgsObj) obj;
                taskProxy = argsObj.taskProxy;
                objArr = argsObj.args;
            } else {
                objArr = null;
            }
            if (taskProxy != null) {
                try {
                    switch (message.what) {
                        case 1000000001:
                            taskProxy.task.onWaiting();
                            break;
                        case 1000000002:
                            taskProxy.task.onStarted();
                            break;
                        case 1000000003:
                            taskProxy.task.onSuccess(taskProxy.getResult());
                            break;
                        case 1000000004:
                            Throwable th = (Throwable) objArr[0];
                            LogUtil.m45d(th.getMessage(), th);
                            taskProxy.task.onError(th, false);
                            break;
                        case 1000000005:
                            taskProxy.task.onUpdate(message.arg1, objArr);
                            break;
                        case 1000000006:
                            if (!taskProxy.callOnCanceled) {
                                taskProxy.callOnCanceled = true;
                                taskProxy.task.onCancelled((Callback.CancelledException) objArr[0]);
                                break;
                            } else {
                                return;
                            }
                        case 1000000007:
                            if (!taskProxy.callOnFinished) {
                                taskProxy.callOnFinished = true;
                                taskProxy.task.onFinished();
                                break;
                            } else {
                                return;
                            }
                    }
                    return;
                } catch (Throwable th2) {
                    taskProxy.setState(AbsTask.State.ERROR);
                    if (message.what != 1000000004) {
                        taskProxy.task.onError(th2, true);
                        return;
                    } else if (C5540x.isDebug()) {
                        throw new RuntimeException(th2);
                    } else {
                        return;
                    }
                }
            }
            throw new RuntimeException("msg.obj not instanceof TaskProxy");
        }
    }
}
