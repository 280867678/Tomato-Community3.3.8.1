package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.accelerometer;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.utils.Logger;
import com.p076mh.webappStart.android_plugin_impl.beans.StartDeviceAccelerometerParamsBean;
import com.p076mh.webappStart.android_plugin_impl.callback.JsCallBackKeys;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePairSharePluginImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import com.p076mh.webappStart.util.sensor.accelerometer.AccelerometerSensorManager;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.accelerometer.DeviceAccelerometerSwitchImpl */
/* loaded from: classes3.dex */
public class DeviceAccelerometerSwitchImpl extends BasePairSharePluginImpl {
    private final Map map = new HashMap();

    public DeviceAccelerometerSwitchImpl(Boolean bool) {
        super(bool.booleanValue());
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, Object obj, Plugin.PluginCallback pluginCallback) {
        if (this.isOn && obj != null) {
            if (AccelerometerSensorManager.getInstance().startListen(new AccelerometerSensorManager.AccelerometerSensorEventValueListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.accelerometer.DeviceAccelerometerSwitchImpl.1
                @Override // com.p076mh.webappStart.util.sensor.accelerometer.AccelerometerSensorManager.AccelerometerSensorEventValueListener
                public void onSensorValueChanged(float[] fArr) {
                    if (DeviceAccelerometerCallBackController.getInstance().isOn()) {
                        DeviceAccelerometerSwitchImpl.this.map.clear();
                        DeviceAccelerometerSwitchImpl.this.map.put("x", Float.valueOf(fArr[0] / (-10.0f)));
                        DeviceAccelerometerSwitchImpl.this.map.put("y", Float.valueOf(fArr[1] / (-10.0f)));
                        DeviceAccelerometerSwitchImpl.this.map.put("z", Float.valueOf(fArr[2] / (-10.0f)));
                        DeviceAccelerometerSwitchImpl.this.map.put("success", true);
                        DeviceAccelerometerSwitchImpl.this.map.put("complete", true);
                        String str = ((BasePluginImpl) DeviceAccelerometerSwitchImpl.this).TAG;
                        Logger.m4112i(str, "onSensorValueChanged: x " + fArr[0]);
                        String str2 = ((BasePluginImpl) DeviceAccelerometerSwitchImpl.this).TAG;
                        Logger.m4112i(str2, "onSensorValueChanged: y " + fArr[1]);
                        String str3 = ((BasePluginImpl) DeviceAccelerometerSwitchImpl.this).TAG;
                        Logger.m4112i(str3, "onSensorValueChanged: z " + fArr[2]);
                        ((BasePluginImpl) DeviceAccelerometerSwitchImpl.this).executor.executeEvent(JsCallBackKeys.ON_ACCE_LEROMETER, DeviceAccelerometerSwitchImpl.this.map, null);
                    }
                }
            }, ((StartDeviceAccelerometerParamsBean) obj).getInterval())) {
                responseSuccess(pluginCallback);
                return;
            } else {
                responseFailure(pluginCallback);
                return;
            }
        }
        AccelerometerSensorManager.getInstance().stopListen();
        responseSuccess(pluginCallback);
    }
}
