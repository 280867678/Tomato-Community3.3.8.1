package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.motion;

import android.util.Log;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.beans.StartDeviceMotionListeningParamsBean;
import com.p076mh.webappStart.android_plugin_impl.callback.JsCallBackKeys;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePairSharePluginImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import com.p076mh.webappStart.util.sensor.OrientationSensorManager;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.motion.DeviceMotionSwitchImpl */
/* loaded from: classes3.dex */
public class DeviceMotionSwitchImpl extends BasePairSharePluginImpl {
    private final Map map = new HashMap();

    public DeviceMotionSwitchImpl(Boolean bool) {
        super(bool.booleanValue());
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, Object obj, Plugin.PluginCallback pluginCallback) {
        if (this.isOn && obj != null) {
            if (OrientationSensorManager.getInstance().startListen(new OrientationSensorManager.OrientationSensorEventValueListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.motion.DeviceMotionSwitchImpl.1
                @Override // com.p076mh.webappStart.util.sensor.OrientationSensorManager.OrientationSensorEventValueListener
                public void onSensorValueChanged(float[] fArr, int i) {
                    if (DeviceMotionCallBackController.getInstance().isOn()) {
                        DeviceMotionSwitchImpl.this.map.clear();
                        DeviceMotionSwitchImpl.this.map.put("alpha", Float.valueOf(fArr[0]));
                        DeviceMotionSwitchImpl.this.map.put("beta", Float.valueOf(fArr[1]));
                        DeviceMotionSwitchImpl.this.map.put("gamma", Float.valueOf(fArr[2]));
                        DeviceMotionSwitchImpl.this.map.put("success", true);
                        DeviceMotionSwitchImpl.this.map.put("complete", true);
                        String str = ((BasePluginImpl) DeviceMotionSwitchImpl.this).TAG;
                        Log.e(str, "onSensorValueChanged: alpha " + fArr[0]);
                        String str2 = ((BasePluginImpl) DeviceMotionSwitchImpl.this).TAG;
                        Log.e(str2, "onSensorValueChanged: beta " + fArr[1]);
                        String str3 = ((BasePluginImpl) DeviceMotionSwitchImpl.this).TAG;
                        Log.e(str3, "onSensorValueChanged: gamma" + fArr[2]);
                        ((BasePluginImpl) DeviceMotionSwitchImpl.this).executor.executeEvent(JsCallBackKeys.ON_DEVICE, DeviceMotionSwitchImpl.this.map, null);
                    }
                }
            }, ((StartDeviceMotionListeningParamsBean) obj).getInterval())) {
                responseSuccess(pluginCallback);
                return;
            } else {
                responseFailure(pluginCallback);
                return;
            }
        }
        OrientationSensorManager.getInstance().stopListen();
        responseSuccess(pluginCallback);
    }
}
