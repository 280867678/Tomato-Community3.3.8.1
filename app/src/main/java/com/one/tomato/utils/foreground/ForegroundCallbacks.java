package com.one.tomato.utils.foreground;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import com.one.tomato.mvp.base.AppManager;
import com.one.tomato.utils.LogUtil;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes3.dex */
public class ForegroundCallbacks implements Application.ActivityLifecycleCallbacks {
    private static ForegroundCallbacks instance;
    private Runnable check;
    private boolean foreground = false;
    private boolean paused = true;
    private Handler handler = new Handler();
    private List<Listener> listeners = new CopyOnWriteArrayList();

    /* loaded from: classes3.dex */
    public interface Listener {
        void onBecameBackground(Activity activity);

        void onBecameForeground(Activity activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }

    public static ForegroundCallbacks init(Application application) {
        if (instance == null) {
            instance = new ForegroundCallbacks();
            application.registerActivityLifecycleCallbacks(instance);
            ForegroundUtil.getInstance().addListener();
        }
        return instance;
    }

    public static ForegroundCallbacks get() {
        ForegroundCallbacks foregroundCallbacks = instance;
        if (foregroundCallbacks != null) {
            return foregroundCallbacks;
        }
        throw new IllegalStateException("Foreground is not initialised - invoke at least once with parameterised init/get");
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        this.paused = false;
        boolean z = !this.foreground;
        this.foreground = true;
        Runnable runnable = this.check;
        if (runnable != null) {
            this.handler.removeCallbacks(runnable);
        }
        WeakReference weakReference = new WeakReference(activity);
        if (z) {
            LogUtil.m3784i("went foreground");
            for (Listener listener : this.listeners) {
                try {
                    listener.onBecameForeground((Activity) weakReference.get());
                } catch (Exception e) {
                    LogUtil.m3786e("Listener threw exception!:" + e.toString());
                }
            }
            return;
        }
        LogUtil.m3788d("still foreground");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        this.paused = true;
        Runnable runnable = this.check;
        if (runnable != null) {
            this.handler.removeCallbacks(runnable);
        }
        final WeakReference weakReference = new WeakReference(activity);
        Handler handler = this.handler;
        Runnable runnable2 = new Runnable() { // from class: com.one.tomato.utils.foreground.ForegroundCallbacks.1
            @Override // java.lang.Runnable
            public void run() {
                if (ForegroundCallbacks.this.foreground && ForegroundCallbacks.this.paused) {
                    ForegroundCallbacks.this.foreground = false;
                    LogUtil.m3784i("went background");
                    for (Listener listener : ForegroundCallbacks.this.listeners) {
                        try {
                            listener.onBecameBackground((Activity) weakReference.get());
                        } catch (Exception e) {
                            LogUtil.m3786e("Listener threw exception!:" + e.toString());
                        }
                    }
                    return;
                }
                LogUtil.m3784i("still foreground");
            }
        };
        this.check = runnable2;
        handler.postDelayed(runnable2, 500L);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        AppManager.getAppManager().addActivity(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        AppManager.getAppManager().removeActivity(activity);
    }
}
