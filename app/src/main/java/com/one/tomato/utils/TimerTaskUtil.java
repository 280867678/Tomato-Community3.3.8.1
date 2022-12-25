package com.one.tomato.utils;

import android.os.Handler;

/* loaded from: classes3.dex */
public class TimerTaskUtil {
    private static TimerTaskUtil timerTaskUtil;
    private int num = 0;
    private Handler handler = null;
    private Runnable runnable = null;

    /* loaded from: classes3.dex */
    public interface TimeTask {
        void position(int i);

        void stop();
    }

    static /* synthetic */ int access$010(TimerTaskUtil timerTaskUtil2) {
        int i = timerTaskUtil2.num;
        timerTaskUtil2.num = i - 1;
        return i;
    }

    public static TimerTaskUtil getInstance() {
        if (timerTaskUtil == null) {
            timerTaskUtil = new TimerTaskUtil();
        }
        return timerTaskUtil;
    }

    public void onStart(int i, final TimeTask timeTask) {
        this.num = i;
        if (this.handler == null) {
            this.handler = new Handler();
        }
        if (this.runnable == null) {
            this.runnable = new Runnable() { // from class: com.one.tomato.utils.TimerTaskUtil.1
                @Override // java.lang.Runnable
                public void run() {
                    if (TimerTaskUtil.this.num != 0) {
                        timeTask.position(TimerTaskUtil.this.num);
                        TimerTaskUtil.access$010(TimerTaskUtil.this);
                        if (TimerTaskUtil.this.handler == null) {
                            return;
                        }
                        TimerTaskUtil.this.handler.postDelayed(this, 1000L);
                        return;
                    }
                    timeTask.stop();
                    TimerTaskUtil.this.onStop();
                }
            };
        }
        this.handler.postDelayed(this.runnable, 1000L);
    }

    public void onStop() {
        Handler handler = this.handler;
        if (handler != null) {
            handler.removeCallbacks(this.runnable);
            this.runnable = null;
            this.handler = null;
        }
    }
}
