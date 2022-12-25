package com.SecShell.SecShell;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import java.lang.reflect.Field;

/* renamed from: com.SecShell.SecShell.AW */
/* loaded from: classes5.dex */
public class ApplicationC0832AW extends Application {

    /* renamed from: a */
    public static Application f733a;

    static {
        System.loadLibrary("SecShell");
        C0833H.m4912i();
    }

    @Override // android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        try {
            if (!"".equals(C0833H.APPNAME)) {
                f733a = (Application) getClassLoader().loadClass(C0833H.APPNAME).newInstance();
            }
        } catch (Exception unused) {
            f733a = null;
        }
        C0833H.attach(f733a, context);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Context getApplicationContext() {
        Application application = f733a;
        return application != null ? application : super.getApplicationContext();
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Application application = f733a;
        if (application != null) {
            application.onConfigurationChanged(configuration);
        }
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        Application application = f733a;
        if (application != null) {
            C0833H.attach(application, null);
            f733a.onCreate();
            LayoutInflater from = LayoutInflater.from(getApplicationContext());
            try {
                Field declaredField = LayoutInflater.class.getDeclaredField("mContext");
                declaredField.setAccessible(true);
                declaredField.set(from, f733a);
            } catch (Exception unused) {
            }
        }
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        Application application = f733a;
        if (application != null) {
            application.onLowMemory();
        }
    }

    @Override // android.app.Application
    public void onTerminate() {
        super.onTerminate();
        Application application = f733a;
        if (application != null) {
            application.onTerminate();
        }
    }

    @Override // android.app.Application, android.content.ComponentCallbacks2
    @TargetApi(14)
    public void onTrimMemory(int i) {
        try {
            super.onTrimMemory(i);
            if (f733a == null) {
                return;
            }
            f733a.onTrimMemory(i);
        } catch (Exception unused) {
        }
    }

    @Override // android.app.Application
    @TargetApi(14)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        super.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
        Application application = f733a;
        if (application != null) {
            application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
        }
    }
}
