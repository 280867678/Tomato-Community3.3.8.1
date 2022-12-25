package com.sensorsdata.analytics.android.sdk;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes3.dex */
public class TrackTaskManagerThread implements Runnable {
    private static final int POOL_SIZE = 1;
    private static final int SLEEP_TIME = 300;
    private boolean isStop = false;
    private ExecutorService mPool;
    private TrackTaskManager mTrackTaskManager;

    public TrackTaskManagerThread() {
        try {
            this.mTrackTaskManager = TrackTaskManager.getInstance();
            this.mPool = Executors.newFixedThreadPool(1);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        while (!this.isStop) {
            try {
                Runnable trackEventTask = this.mTrackTaskManager.getTrackEventTask();
                if (trackEventTask != null) {
                    this.mPool.execute(trackEventTask);
                } else {
                    try {
                        Thread.sleep(300L);
                    } catch (InterruptedException e) {
                        SALog.printStackTrace(e);
                    }
                }
            } catch (Exception e2) {
                SALog.printStackTrace(e2);
                return;
            }
        }
        if (this.isStop) {
            Runnable trackEventTask2 = this.mTrackTaskManager.getTrackEventTask();
            while (trackEventTask2 != null) {
                this.mPool.execute(trackEventTask2);
                trackEventTask2 = this.mTrackTaskManager.getTrackEventTask();
            }
            this.mPool.shutdown();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setStop(boolean z) {
        this.isStop = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isStopped() {
        return this.isStop;
    }
}
