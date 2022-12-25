package com.gen.p059mh.webapps.listener;

import java.util.ArrayList;
import java.util.List;

/* renamed from: com.gen.mh.webapps.listener.WebappLifecycleSubject */
/* loaded from: classes2.dex */
public class WebappLifecycleSubject {
    private List<WebappLifecycleObserver> observers = new ArrayList();

    public void add(WebappLifecycleObserver webappLifecycleObserver) {
        this.observers.add(webappLifecycleObserver);
    }

    public void onDestroy() {
        for (WebappLifecycleObserver webappLifecycleObserver : this.observers) {
            webappLifecycleObserver.onWebDestroy();
        }
    }

    public void onCreate() {
        for (WebappLifecycleObserver webappLifecycleObserver : this.observers) {
            webappLifecycleObserver.onWebCreate();
        }
    }

    public void onWebScrollerChange(int i, int i2) {
        for (WebappLifecycleObserver webappLifecycleObserver : this.observers) {
            webappLifecycleObserver.onWebScrollerChange(i, i2);
        }
    }

    public void onPause() {
        for (WebappLifecycleObserver webappLifecycleObserver : this.observers) {
            webappLifecycleObserver.onWebPause();
        }
    }

    public void onResume() {
        for (WebappLifecycleObserver webappLifecycleObserver : this.observers) {
            webappLifecycleObserver.onWebResume();
        }
    }

    public void remove(WebappLifecycleObserver webappLifecycleObserver) {
        this.observers.remove(webappLifecycleObserver);
    }
}
