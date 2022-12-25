package com.p076mh.webappStart.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.gen.p059mh.webapps.utils.Logger;

/* renamed from: com.mh.webappStart.util.TimeCounter */
/* loaded from: classes3.dex */
public class TimeCounter {
    private boolean isWorking;
    private TimeCallBack timeCallBack;
    private long delayTime = 1000;
    private final Runnable recordTimeTask = new Runnable() { // from class: com.mh.webappStart.util.TimeCounter.1
        @Override // java.lang.Runnable
        public void run() {
            TimeCounter.access$008(TimeCounter.this);
            if (TimeCounter.this.timeCallBack != null) {
                TimeCounter.this.timeCallBack.onTime(TimeCounter.this.passedCount, TimeCounter.this.delayTime);
            }
            if (TimeCounter.this.isWorking) {
                TimeCounter.this.recordTimeHandler.sendEmptyMessage(0);
            }
        }
    };
    private final Handler recordTimeHandler = new Handler(Looper.getMainLooper()) { // from class: com.mh.webappStart.util.TimeCounter.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                TimeCounter.this.recordTimeHandler.postDelayed(TimeCounter.this.recordTimeTask, TimeCounter.this.delayTime);
            } else if (i == 1) {
                TimeCounter.this.recordTimeHandler.removeMessages(0);
            } else if (i != 2) {
            } else {
                TimeCounter.this.passedCount = 0;
                TimeCounter.this.recordTimeHandler.removeCallbacks(TimeCounter.this.recordTimeTask);
                TimeCounter.this.recordTimeHandler.removeMessages(0);
            }
        }
    };
    private int passedCount = 0;

    /* renamed from: com.mh.webappStart.util.TimeCounter$TimeCallBack */
    /* loaded from: classes3.dex */
    public interface TimeCallBack {
        void onTime(int i, long j);
    }

    static /* synthetic */ int access$008(TimeCounter timeCounter) {
        int i = timeCounter.passedCount;
        timeCounter.passedCount = i + 1;
        return i;
    }

    public TimeCounter() {
        this.isWorking = false;
        this.isWorking = false;
    }

    public void startCount() {
        Logger.m4112i("TimeCounter", "startCount: ");
        startCount(true);
    }

    public void startCount(boolean z) {
        this.isWorking = true;
        if (z) {
            this.passedCount = 0;
        }
        this.recordTimeHandler.sendEmptyMessage(0);
    }

    public void setTimeCallBack(TimeCallBack timeCallBack) {
        this.timeCallBack = timeCallBack;
    }

    public void stop() {
        Logger.m4112i("TimeCounter", "stop");
        this.recordTimeHandler.removeCallbacksAndMessages(null);
        this.isWorking = false;
    }

    public void setDelayTime(long j) {
        this.delayTime = j;
    }
}
