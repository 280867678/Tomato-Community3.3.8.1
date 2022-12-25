package com.gen.p059mh.webapps.webEngine;

import com.gen.p059mh.webapps.webEngine.impl.DefaultWebEngine;
import com.gen.p059mh.webapps.webEngine.impl.DefaultWebEngineImpl;

/* renamed from: com.gen.mh.webapps.webEngine.WebEngineManager */
/* loaded from: classes2.dex */
public class WebEngineManager {
    public static boolean WebViewDebug;
    static WebEngineManager instance;
    private Object webEngine = new DefaultWebEngine();

    private WebEngineManager() {
    }

    public static WebEngineManager getInstance() {
        if (instance == null) {
            instance = new WebEngineManager();
        }
        return instance;
    }

    public WebEngine initWebEngine() {
        return new DefaultWebEngineImpl();
    }

    public void setWebEngine(Object obj) {
        this.webEngine = obj;
    }
}
