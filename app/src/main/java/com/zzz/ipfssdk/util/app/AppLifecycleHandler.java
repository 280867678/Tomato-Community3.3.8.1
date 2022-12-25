package com.zzz.ipfssdk.util.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.zzz.ipfssdk.LogUtil;

/* loaded from: classes4.dex */
public class AppLifecycleHandler implements Application.ActivityLifecycleCallbacks {

    /* renamed from: a */
    public int f5978a = 0;

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        if (this.f5978a == 0) {
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, ">>>>>>>>>>>>>>>>>>>App切到前台");
        }
        this.f5978a++;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        this.f5978a--;
        if (this.f5978a == 0) {
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, ">>>>>>>>>>>>>>>>>>>App切到后台");
        }
    }
}
