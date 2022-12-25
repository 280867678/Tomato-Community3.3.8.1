package com.gen.p059mh.webapps.listener;

/* renamed from: com.gen.mh.webapps.listener.WebappLifecycleObserver */
/* loaded from: classes2.dex */
public interface WebappLifecycleObserver {
    void onWebCreate();

    void onWebDestroy();

    void onWebPause();

    void onWebResume();

    void onWebScrollerChange(int i, int i2);
}
