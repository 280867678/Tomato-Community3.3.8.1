package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.wifi;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.WxAPIConfig;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePairSharePluginImpl;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.wifi.WifiSwitchPairSharePluginImpl */
/* loaded from: classes3.dex */
public class WifiSwitchPairSharePluginImpl extends BasePairSharePluginImpl {
    public WifiSwitchPairSharePluginImpl(Boolean bool) {
        super(bool.booleanValue());
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, Object obj, Plugin.PluginCallback pluginCallback) throws Exception {
        WxAPIConfig.WiFi.isWifiStartCalled = this.isOn;
        responseSuccess(pluginCallback);
    }
}
