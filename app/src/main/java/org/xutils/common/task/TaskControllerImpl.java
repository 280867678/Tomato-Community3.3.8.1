package org.xutils.common.task;

import android.os.Looper;
import java.util.concurrent.atomic.AtomicInteger;
import org.xutils.C5540x;
import org.xutils.common.Callback;
import org.xutils.common.TaskController;
import org.xutils.common.util.LogUtil;

/* loaded from: classes4.dex */
public final class TaskControllerImpl implements TaskController {
    private static volatile TaskController instance;

    private TaskControllerImpl() {
    }

    public static void registerInstance() {
        if (instance == null) {
            synchronized (TaskController.class) {
                if (instance == null) {
                    instance = new TaskControllerImpl();
                }
            }
        }
        C5540x.Ext.setTaskController(instance);
    }

    @Override // org.xutils.common.TaskController
    public <T> AbsTask<T> start(AbsTask<T> absTask) {
        TaskProxy taskProxy;
        if (absTask instanceof TaskProxy) {
            taskProxy = (TaskProxy) absTask;
        } else {
            taskProxy = new TaskProxy(absTask);
        }
        try {
            taskProxy.doBackground();
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
        }
        return taskProxy;
    }

    @Override // org.xutils.common.TaskController
    public <T> T startSync(AbsTask<T> absTask) throws Throwable {
        T t = null;
        try {
            try {
                absTask.onWaiting();
                absTask.onStarted();
                t = absTask.doBackground();
                absTask.onSuccess(t);
            } finally {
                absTask.onFinished();
            }
        } catch (Callback.CancelledException e) {
            absTask.onCancelled(e);
        } catch (Throwable th) {
            absTask.onError(th, false);
            throw th;
        }
        return t;
    }

    @Override // org.xutils.common.TaskController
    public <T extends AbsTask<?>> Callback.Cancelable startTasks(final Callback.GroupCallback<T> groupCallback, final T... tArr) {
        if (tArr == null) {
            throw new IllegalArgumentException("task must not be null");
        }
        final Runnable runnable = new Runnable(this) { // from class: org.xutils.common.task.TaskControllerImpl.1
            private final AtomicInteger count = new AtomicInteger(0);
            private final int total;

            {
                this.total = tArr.length;
            }

            @Override // java.lang.Runnable
            public void run() {
                Callback.GroupCallback groupCallback2;
                if (this.count.incrementAndGet() != this.total || (groupCallback2 = groupCallback) == null) {
                    return;
                }
                groupCallback2.onAllFinished();
            }
        };
        for (final T t : tArr) {
            start(new TaskProxy(t) { // from class: org.xutils.common.task.TaskControllerImpl.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // org.xutils.common.task.TaskProxy, org.xutils.common.task.AbsTask
                public void onSuccess(Object obj) {
                    super.onSuccess(obj);
                    TaskControllerImpl.this.post(new Runnable() { // from class: org.xutils.common.task.TaskControllerImpl.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            C55132 c55132 = C55132.this;
                            Callback.GroupCallback groupCallback2 = groupCallback;
                            if (groupCallback2 != null) {
                                groupCallback2.onSuccess(t);
                            }
                        }
                    });
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // org.xutils.common.task.TaskProxy, org.xutils.common.task.AbsTask
                public void onCancelled(final Callback.CancelledException cancelledException) {
                    super.onCancelled(cancelledException);
                    TaskControllerImpl.this.post(new Runnable() { // from class: org.xutils.common.task.TaskControllerImpl.2.2
                        @Override // java.lang.Runnable
                        public void run() {
                            C55132 c55132 = C55132.this;
                            Callback.GroupCallback groupCallback2 = groupCallback;
                            if (groupCallback2 != null) {
                                groupCallback2.onCancelled(t, cancelledException);
                            }
                        }
                    });
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // org.xutils.common.task.TaskProxy, org.xutils.common.task.AbsTask
                public void onError(final Throwable th, final boolean z) {
                    super.onError(th, z);
                    TaskControllerImpl.this.post(new Runnable() { // from class: org.xutils.common.task.TaskControllerImpl.2.3
                        @Override // java.lang.Runnable
                        public void run() {
                            C55132 c55132 = C55132.this;
                            Callback.GroupCallback groupCallback2 = groupCallback;
                            if (groupCallback2 != null) {
                                groupCallback2.onError(t, th, z);
                            }
                        }
                    });
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // org.xutils.common.task.TaskProxy, org.xutils.common.task.AbsTask
                public void onFinished() {
                    super.onFinished();
                    TaskControllerImpl.this.post(new Runnable() { // from class: org.xutils.common.task.TaskControllerImpl.2.4
                        @Override // java.lang.Runnable
                        public void run() {
                            C55132 c55132 = C55132.this;
                            Callback.GroupCallback groupCallback2 = groupCallback;
                            if (groupCallback2 != null) {
                                groupCallback2.onFinished(t);
                            }
                            runnable.run();
                        }
                    });
                }
            });
        }
        return new Callback.Cancelable(this) { // from class: org.xutils.common.task.TaskControllerImpl.3
            @Override // org.xutils.common.Callback.Cancelable
            public void cancel() {
                for (AbsTask absTask : tArr) {
                    absTask.cancel();
                }
            }

            @Override // org.xutils.common.Callback.Cancelable
            public boolean isCancelled() {
                boolean z = true;
                for (AbsTask absTask : tArr) {
                    if (!absTask.isCancelled()) {
                        z = false;
                    }
                }
                return z;
            }
        };
    }

    @Override // org.xutils.common.TaskController
    public void autoPost(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            runnable.run();
        } else {
            TaskProxy.sHandler.post(runnable);
        }
    }

    @Override // org.xutils.common.TaskController
    public void post(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        TaskProxy.sHandler.post(runnable);
    }

    @Override // org.xutils.common.TaskController
    public void postDelayed(Runnable runnable, long j) {
        if (runnable == null) {
            return;
        }
        TaskProxy.sHandler.postDelayed(runnable, j);
    }

    @Override // org.xutils.common.TaskController
    public void run(Runnable runnable) {
        if (!TaskProxy.sDefaultExecutor.isBusy()) {
            TaskProxy.sDefaultExecutor.execute(runnable);
        } else {
            new Thread(runnable).start();
        }
    }

    @Override // org.xutils.common.TaskController
    public void removeCallbacks(Runnable runnable) {
        TaskProxy.sHandler.removeCallbacks(runnable);
    }
}
