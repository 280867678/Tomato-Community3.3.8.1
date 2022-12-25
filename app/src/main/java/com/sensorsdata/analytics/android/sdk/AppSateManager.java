package com.sensorsdata.analytics.android.sdk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import java.lang.ref.WeakReference;

@SuppressLint({"NewApi"})
/* loaded from: classes3.dex */
public class AppSateManager implements Application.ActivityLifecycleCallbacks {
    private static volatile AppSateManager mSingleton;
    private int mActivityCount;
    private WeakReference<Activity> mForeGroundActivity = new WeakReference<>(null);
    private int mCurrentRootWindowsHashCode = -1;

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    private AppSateManager() {
    }

    public static AppSateManager getInstance() {
        if (mSingleton == null) {
            synchronized (AppSateManager.class) {
                if (mSingleton == null) {
                    mSingleton = new AppSateManager();
                }
            }
        }
        return mSingleton;
    }

    public Activity getForegroundActivity() {
        return this.mForeGroundActivity.get();
    }

    public void setForegroundActivity(Activity activity) {
        this.mForeGroundActivity = new WeakReference<>(activity);
    }

    public boolean isInBackground() {
        return this.mActivityCount <= 0;
    }

    public int getCurrentRootWindowsHashCode() {
        WeakReference<Activity> weakReference;
        if (this.mCurrentRootWindowsHashCode == -1 && (weakReference = this.mForeGroundActivity) != null && weakReference.get() != null) {
            this.mCurrentRootWindowsHashCode = this.mForeGroundActivity.get().getWindow().getDecorView().hashCode();
        }
        return this.mCurrentRootWindowsHashCode;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        setForegroundActivity(activity);
        if (!activity.isChild()) {
            this.mCurrentRootWindowsHashCode = -1;
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        this.mActivityCount++;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        setForegroundActivity(activity);
        if (!activity.isChild()) {
            this.mCurrentRootWindowsHashCode = activity.getWindow().getDecorView().hashCode();
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        if (!activity.isChild()) {
            this.mCurrentRootWindowsHashCode = -1;
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        this.mActivityCount--;
    }
}
