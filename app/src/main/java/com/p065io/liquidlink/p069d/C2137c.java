package com.p065io.liquidlink.p069d;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.SystemClock;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.io.liquidlink.d.c */
/* loaded from: classes3.dex */
public class C2137c implements Application.ActivityLifecycleCallbacks {

    /* renamed from: a */
    long f1401a = 0;

    /* renamed from: b */
    final /* synthetic */ C2135a f1402b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public C2137c(C2135a c2135a) {
        this.f1402b = c2135a;
    }

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
        int i;
        i = this.f1402b.f1397f;
        if (i == 0) {
            this.f1401a = SystemClock.elapsedRealtime();
        }
        C2135a.m4026e(this.f1402b);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        int i;
        C2135a.m4025f(this.f1402b);
        i = this.f1402b.f1397f;
        if (i == 0) {
            this.f1402b.m4033a((SystemClock.elapsedRealtime() - this.f1401a) / 1000);
        }
    }
}
