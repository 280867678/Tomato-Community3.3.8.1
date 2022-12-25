package com.gen.p059mh.webapps;

import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.listener.JSResponseListener;

/* renamed from: com.gen.mh.webapps.Plugin */
/* loaded from: classes2.dex */
public abstract class Plugin {
    public Executor executor;
    private String name;
    public IWebFragmentController webViewFragment;

    /* renamed from: com.gen.mh.webapps.Plugin$Executor */
    /* loaded from: classes2.dex */
    public interface Executor {
        void executeEvent(String str, Object obj, JSResponseListener jSResponseListener);
    }

    /* renamed from: com.gen.mh.webapps.Plugin$PluginCallback */
    /* loaded from: classes2.dex */
    public interface PluginCallback {
        void response(Object obj);
    }

    public void onHide() {
    }

    public void onShow() {
    }

    public abstract void process(String str, PluginCallback pluginCallback);

    public void ready() {
    }

    public void unload() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Plugin(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public IWebFragmentController getWebViewFragment() {
        return this.webViewFragment;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Executor getExecutor() {
        return this.executor;
    }
}
