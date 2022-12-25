package com.gen.p059mh.webapp_extensions.task;

import android.support.annotation.NonNull;
import com.gen.p059mh.webapp_extensions.task.Task;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* renamed from: com.gen.mh.webapp_extensions.task.TaskDispatcher */
/* loaded from: classes2.dex */
public class TaskDispatcher {
    private static TaskDispatcher instance;
    private ExecutorService executorService;
    private List<Task> queueTaskList = Collections.synchronizedList(new ArrayList());
    private List<Task> downloadedList = Collections.synchronizedList(new ArrayList());

    private TaskDispatcher() {
    }

    public static TaskDispatcher getInstance() {
        if (instance == null) {
            synchronized (TaskDispatcher.class) {
                if (instance == null) {
                    instance = new TaskDispatcher();
                }
            }
        }
        return instance;
    }

    private ExecutorService getExecutorService() {
        if (this.executorService == null) {
            this.executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue(), threadFactory("download dispatcher", false));
        }
        return this.executorService;
    }

    public synchronized boolean enqueue(Task task) {
        if (this.queueTaskList.contains(task)) {
            return false;
        }
        if (task == null) {
            return false;
        }
        if (this.queueTaskList.size() < 3) {
            this.queueTaskList.add(task);
            getExecutorService().execute(task);
        } else {
            task.setState(Task.STATE.PENDING);
            this.queueTaskList.add(task);
        }
        return true;
    }

    public synchronized void finished(Task task) {
        if (task != null) {
            if (task.getState() == Task.STATE.FINISHED && this.queueTaskList.remove(task)) {
                this.downloadedList.add(task);
                promoteSyncTask();
            }
        }
    }

    private synchronized void promoteSyncTask() {
        for (Task task : this.queueTaskList) {
            if (task.getState() == Task.STATE.PENDING) {
                getExecutorService().execute(task);
                return;
            }
        }
    }

    private ThreadFactory threadFactory(final String str, final boolean z) {
        return new ThreadFactory(this) { // from class: com.gen.mh.webapp_extensions.task.TaskDispatcher.1
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(@NonNull Runnable runnable) {
                Thread thread = new Thread(runnable, str);
                thread.setDaemon(z);
                return thread;
            }
        };
    }
}
