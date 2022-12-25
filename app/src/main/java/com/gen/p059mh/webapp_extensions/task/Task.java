package com.gen.p059mh.webapp_extensions.task;

import android.os.Handler;
import android.os.Message;

/* renamed from: com.gen.mh.webapp_extensions.task.Task */
/* loaded from: classes2.dex */
public abstract class Task implements Runnable {
    String key;
    protected StateListener onStateListener;
    private STATE state = STATE.IDLE;

    /* renamed from: com.gen.mh.webapp_extensions.task.Task$STATE */
    /* loaded from: classes2.dex */
    public enum STATE {
        IDLE,
        PENDING,
        LOADING,
        FAILED,
        FINISHED,
        UNKNOWN
    }

    /* renamed from: com.gen.mh.webapp_extensions.task.Task$StateListener */
    /* loaded from: classes2.dex */
    public interface StateListener {
        void onComplete(int i, int i2);
    }

    public abstract void startTask();

    public Task(String str) {
        new Handler(this) { // from class: com.gen.mh.webapp_extensions.task.Task.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what;
            }
        };
        this.key = str;
    }

    public void addEvent() {
        TaskDispatcher.getInstance().enqueue(this);
    }

    @Override // java.lang.Runnable
    public void run() {
        start();
    }

    private void start() {
        STATE state = this.state;
        STATE state2 = STATE.LOADING;
        if (state == state2) {
            return;
        }
        this.state = state2;
        startTask();
    }

    public void finish() {
        this.state = STATE.FINISHED;
        TaskDispatcher.getInstance().finished(this);
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public STATE getState() {
        return this.state;
    }

    public void setStateListener(StateListener stateListener) {
        this.onStateListener = stateListener;
    }

    public String toString() {
        return "Task{key=" + this.key + '}';
    }
}
