package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.compass;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.utils.Logger;
import com.p076mh.webappStart.android_plugin_impl.callback.JsCallBackKeys;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePairSharePluginImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import com.p076mh.webappStart.util.sensor.SensorUtil;
import com.p076mh.webappStart.util.sensor.compass.CompassManager;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.compass.DeviceCompassSwitchImpl */
/* loaded from: classes3.dex */
public class DeviceCompassSwitchImpl extends BasePairSharePluginImpl {
    private final Map map = new HashMap();

    public DeviceCompassSwitchImpl(Boolean bool) {
        super(bool.booleanValue());
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, Object obj, Plugin.PluginCallback pluginCallback) {
        if (this.isOn) {
            if (CompassManager.getInstance().startListen(new CompassManager.CompassEventValueListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.compass.DeviceCompassSwitchImpl.1
                @Override // com.p076mh.webappStart.util.sensor.compass.CompassManager.CompassEventValueListener
                public void onSensorValueChanged(float f, int i) {
                    if (DeviceCompassCallBackController.getInstance().isOn()) {
                        DeviceCompassSwitchImpl.this.map.clear();
                        DeviceCompassSwitchImpl.this.map.put("direction", Float.valueOf(f));
                        DeviceCompassSwitchImpl.this.map.put("accuracy", SensorUtil.getAccuracyEnumString(i));
                        DeviceCompassSwitchImpl.this.map.put("success", true);
                        DeviceCompassSwitchImpl.this.map.put("complete", true);
                        String str = ((BasePluginImpl) DeviceCompassSwitchImpl.this).TAG;
                        Logger.m4112i(str, "onSensorValueChanged: direction " + f + ",accuracy = " + SensorUtil.getAccuracyEnumString(i));
                        ((BasePluginImpl) DeviceCompassSwitchImpl.this).executor.executeEvent(JsCallBackKeys.ON_COMPASS, DeviceCompassSwitchImpl.this.map, null);
                    }
                }
            }, null)) {
                responseSuccess(pluginCallback);
                return;
            } else {
                responseFailure(pluginCallback);
                return;
            }
        }
        CompassManager.getInstance().stopListen();
        responseSuccess(pluginCallback);
    }
}
