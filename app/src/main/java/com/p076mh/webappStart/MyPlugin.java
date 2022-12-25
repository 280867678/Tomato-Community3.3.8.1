package com.p076mh.webappStart;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.utils.Logger;
import com.p076mh.webappStart.android_plugin_impl.beans.ShowLoadingParamsBean;
import com.p076mh.webappStart.android_plugin_impl.beans.base.BasePluginParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.p078ui.LoadingImpl;
import com.p076mh.webappStart.util.GsonUtil;

/* renamed from: com.mh.webappStart.MyPlugin */
/* loaded from: classes3.dex */
public class MyPlugin extends Plugin {
    private LoadingImpl loadingImpl;

    public MyPlugin() {
        super("demo");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        Logger.m4112i("MyPlugin", "process: " + str);
        String call = ((BasePluginParamsBean) GsonUtil.getInstance().fromJson(str, (Class<Object>) BasePluginParamsBean.class)).getCall();
        if ("test1".equals(call)) {
            test1(pluginCallback);
        } else if (!"test2".equals(call)) {
        } else {
            test2(pluginCallback);
        }
    }

    private void test2(Plugin.PluginCallback pluginCallback) {
        testHideLoading(pluginCallback);
    }

    private void test1(Plugin.PluginCallback pluginCallback) {
        testShowLoading(pluginCallback);
    }

    private void testShowLoading(Plugin.PluginCallback pluginCallback) {
        ShowLoadingParamsBean showLoadingParamsBean = new ShowLoadingParamsBean();
        showLoadingParamsBean.setTitle("万达附近啊");
        this.loadingImpl = new LoadingImpl(true);
        this.loadingImpl.action(getWebViewFragment(), showLoadingParamsBean, pluginCallback);
    }

    private void testHideLoading(Plugin.PluginCallback pluginCallback) {
        LoadingImpl loadingImpl = this.loadingImpl;
        if (loadingImpl != null) {
            loadingImpl.setOn(false);
            this.loadingImpl.action(getWebViewFragment(), null, pluginCallback);
        }
    }
}
