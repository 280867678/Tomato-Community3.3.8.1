package com.sensorsdata.analytics.android.sdk;

import java.util.LinkedList;

/* loaded from: classes3.dex */
public class TrackTaskManager {
    private static TrackTaskManager trackTaskManager;
    private final LinkedList<Runnable> mTrackEventTasks = new LinkedList<>();

    private TrackTaskManager() {
    }

    public static synchronized TrackTaskManager getInstance() {
        TrackTaskManager trackTaskManager2;
        synchronized (TrackTaskManager.class) {
            try {
                if (trackTaskManager == null) {
                    trackTaskManager = new TrackTaskManager();
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
            trackTaskManager2 = trackTaskManager;
        }
        return trackTaskManager2;
    }

    public void addTrackEventTask(Runnable runnable) {
        try {
            synchronized (this.mTrackEventTasks) {
                this.mTrackEventTasks.addLast(runnable);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public Runnable getTrackEventTask() {
        try {
            synchronized (this.mTrackEventTasks) {
                if (this.mTrackEventTasks.size() <= 0) {
                    return null;
                }
                return this.mTrackEventTasks.removeFirst();
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }
}
