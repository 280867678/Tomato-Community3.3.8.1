package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.compass;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePairSharePluginImpl;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.compass.DeviceCompassListeningImpl */
/* loaded from: classes3.dex */
public class DeviceCompassListeningImpl extends BasePairSharePluginImpl {
    public DeviceCompassListeningImpl(Boolean bool) {
        super(bool.booleanValue());
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, Object obj, Plugin.PluginCallback pluginCallback) {
        DeviceCompassCallBackController.getInstance().setOn(this.isOn);
        responseSuccess(pluginCallback);
    }
}
