package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl */
/* loaded from: classes3.dex */
public abstract class BasePluginImpl<T> {
    protected final String TAG = getClass().getSimpleName();
    protected Plugin.Executor executor;

    public abstract void action(IWebFragmentController iWebFragmentController, T t, Plugin.PluginCallback pluginCallback) throws Exception;

    public void setExecutor(Plugin.Executor executor) {
        this.executor = executor;
    }

    public void responseSuccess(Plugin.PluginCallback pluginCallback) {
        HashMap hashMap = new HashMap();
        hashMap.put("success", true);
        hashMap.put("complete", true);
        pluginCallback.response(hashMap);
    }

    public void responseSuccess(Plugin.PluginCallback pluginCallback, Map map) {
        map.put("success", true);
        map.put("complete", true);
        pluginCallback.response(map);
    }

    public void responseFailure(Plugin.PluginCallback pluginCallback) {
        HashMap hashMap = new HashMap();
        hashMap.put("success", false);
        hashMap.put("complete", true);
        pluginCallback.response(hashMap);
    }

    public void responseFailure(Plugin.PluginCallback pluginCallback, Map map) {
        map.clear();
        map.put("success", false);
        map.put("complete", true);
        pluginCallback.response(map);
    }
}
