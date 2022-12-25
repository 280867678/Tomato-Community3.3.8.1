package com.one.tomato.entity;

import android.app.Activity;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public class LockScreenRecodeInfo {
    private long backgroundTime;
    private WeakReference<Activity> currentActivity;
    private long foregroundTime;
    private boolean needLock = false;

    public boolean isNeedLock() {
        return this.needLock;
    }

    public void setNeedLock(boolean z) {
        this.needLock = z;
    }

    public Activity getCurrentActivity() {
        return this.currentActivity.get();
    }

    public void setCurrentActivity(Activity activity) {
        this.currentActivity = new WeakReference<>(activity);
    }

    public long getBackgroundTime() {
        return this.backgroundTime;
    }

    public void setBackgroundTime(long j) {
        this.backgroundTime = j;
    }

    public long getForegroundTime() {
        return this.foregroundTime;
    }

    public void setForegroundTime(long j) {
        this.foregroundTime = j;
    }
}
