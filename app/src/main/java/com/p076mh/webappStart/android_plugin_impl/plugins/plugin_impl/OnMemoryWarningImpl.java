package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl;

import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import com.gen.p059mh.webapp_extensions.WebApplication;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.utils.Logger;
import com.p076mh.webappStart.android_plugin_impl.callback.JsCallBackKeys;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.OnMemoryWarningImpl */
/* loaded from: classes3.dex */
public class OnMemoryWarningImpl extends BasePluginImpl<String> {
    private final Map hashMap1 = new HashMap();
    private final Map hashMap2 = new HashMap();

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, String str, Plugin.PluginCallback pluginCallback) {
        this.hashMap1.clear();
        WebApplication.getInstance().getApplication().registerComponentCallbacks(new ComponentCallbacks2() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.OnMemoryWarningImpl.1
            @Override // android.content.ComponentCallbacks
            public void onConfigurationChanged(Configuration configuration) {
            }

            @Override // android.content.ComponentCallbacks2
            public void onTrimMemory(int i) {
                String str2 = ((BasePluginImpl) OnMemoryWarningImpl.this).TAG;
                Logger.m4112i(str2, "onTrimMemory level: " + i);
                OnMemoryWarningImpl.this.hashMap2.clear();
                OnMemoryWarningImpl.this.hashMap2.put("level", Integer.valueOf(i));
                ((BasePluginImpl) OnMemoryWarningImpl.this).executor.executeEvent(JsCallBackKeys.ON_TRIM_MEMORY, OnMemoryWarningImpl.this.hashMap2, null);
                Logger.m4112i(((BasePluginImpl) OnMemoryWarningImpl.this).TAG, "onTrimMemory call js: ");
            }

            @Override // android.content.ComponentCallbacks
            public void onLowMemory() {
                Logger.m4112i(((BasePluginImpl) OnMemoryWarningImpl.this).TAG, "onLowMemory: ");
            }
        });
        responseSuccess(pluginCallback, this.hashMap1);
    }
}
