package com.p076mh.webappStart.util.queue;

import android.util.Log;
import com.gen.p059mh.webapps.utils.Logger;
import java.util.concurrent.LinkedBlockingQueue;

/* renamed from: com.mh.webappStart.util.queue.AbsQueueTaskHelper */
/* loaded from: classes3.dex */
public class AbsQueueTaskHelper<T> {
    private static AbsQueueTaskHelper instance = new AbsQueueTaskHelper();
    private int alreadyFinishCount;
    private boolean isFinish;
    private boolean isTaskRunning;
    private OnTaskActivateListener onTaskActivateListener;
    private long startTime;
    protected final String TAG = getClass().getSimpleName();
    private LinkedBlockingQueue<T> queue = new LinkedBlockingQueue<>();

    /* renamed from: com.mh.webappStart.util.queue.AbsQueueTaskHelper$OnTaskActivateListener */
    /* loaded from: classes3.dex */
    public interface OnTaskActivateListener<T> {
        void onTaskActivate(T t);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AbsQueueTaskHelper() {
        this.isTaskRunning = false;
        this.alreadyFinishCount = 0;
        this.isFinish = true;
        this.isTaskRunning = false;
        this.alreadyFinishCount = 0;
        this.isFinish = true;
    }

    public static AbsQueueTaskHelper getInstance() {
        return instance;
    }

    public void init() {
        this.isTaskRunning = false;
        this.isFinish = false;
        this.queue.clear();
    }

    public void join(T t) {
        this.queue.offer(t);
    }

    public void activate() {
        activateTask();
    }

    public void next() {
        this.alreadyFinishCount++;
        this.isTaskRunning = false;
        activateTask();
    }

    private void activateTask() {
        if (!this.isTaskRunning) {
            if (this.queue.size() > 0) {
                this.isFinish = false;
                T poll = this.queue.poll();
                if (this.onTaskActivateListener == null) {
                    return;
                }
                this.isTaskRunning = true;
                if (this.alreadyFinishCount == 0) {
                    this.startTime = System.currentTimeMillis();
                }
                this.onTaskActivateListener.onTaskActivate(poll);
                return;
            }
            this.isFinish = true;
            Log.e(this.TAG, "队列为空");
            return;
        }
        Logger.m4116d(this.TAG, "try to activateTask but there is a task running now");
    }

    public void finish() {
        this.isFinish = true;
        this.queue.clear();
    }

    public void setOnTaskActivateListener(OnTaskActivateListener onTaskActivateListener) {
        this.onTaskActivateListener = onTaskActivateListener;
    }

    public int getQueueSize() {
        return this.queue.size();
    }

    public int getAlreadyFinishCount() {
        return this.alreadyFinishCount;
    }

    public long getUsedTimeMilliSeconds() {
        return System.currentTimeMillis() - this.startTime;
    }

    public boolean isFinish() {
        return this.isFinish;
    }
}
