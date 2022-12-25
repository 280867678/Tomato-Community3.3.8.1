package com.tomatolive.library.utils.live;

import com.sensorsdata.analytics.android.sdk.ThreadNameConstants;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class LiveDataTimer {
    private static LiveDataTimer instance;
    private ScheduledExecutorService mScheduledExecutorService;

    private LiveDataTimer() {
    }

    public static LiveDataTimer getInstance() {
        if (instance == null) {
            instance = new LiveDataTimer();
        }
        return instance;
    }

    public void timer(Runnable runnable, long j, long j2) {
        if (isShutdown()) {
            this.mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryWithName(ThreadNameConstants.THREAD_APP_END_DATA_SAVE_TIMER));
            this.mScheduledExecutorService.scheduleAtFixedRate(runnable, j, j2, TimeUnit.MILLISECONDS);
        }
    }

    public void shutdownTimerTask() {
        ScheduledExecutorService scheduledExecutorService = this.mScheduledExecutorService;
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
        }
    }

    private boolean isShutdown() {
        ScheduledExecutorService scheduledExecutorService = this.mScheduledExecutorService;
        return scheduledExecutorService == null || scheduledExecutorService.isShutdown();
    }

    /* loaded from: classes4.dex */
    static class ThreadFactoryWithName implements ThreadFactory {
        private final String name;

        ThreadFactoryWithName(String str) {
            this.name = str;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, this.name);
        }
    }
}
