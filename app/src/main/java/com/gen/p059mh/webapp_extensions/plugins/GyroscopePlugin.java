package com.gen.p059mh.webapp_extensions.plugins;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.gen.p059mh.webapps.Plugin;
import com.google.gson.Gson;
import com.one.tomato.entity.C2516Ad;
import com.tomatolive.library.utils.LogConstants;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.plugins.GyroscopePlugin */
/* loaded from: classes2.dex */
public class GyroscopePlugin extends Plugin implements SensorEventListener {
    private boolean isRunning = false;
    private SensorManager sensorManager;

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public GyroscopePlugin() {
        super("gyroscope");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        Map map = (Map) new Gson().fromJson(str, (Class<Object>) Map.class);
        String str2 = (String) map.get(LogConstants.FOLLOW_OPERATION_TYPE);
        if (C2516Ad.TYPE_START.equals(str2)) {
            startGyro((String) map.get("interval"));
        } else if ("stop".equals(str2)) {
            stopGyro();
        }
        HashMap hashMap = new HashMap();
        hashMap.put("code", 0);
        pluginCallback.response(hashMap);
    }

    private void startGyro(String str) {
        int i;
        if (!this.isRunning && getWebViewFragment().getContext() != null) {
            if ("game".equals(str)) {
                i = 1;
            } else {
                i = "ui".equals(str) ? 2 : 3;
            }
            if (this.sensorManager == null) {
                this.sensorManager = (SensorManager) getWebViewFragment().getContext().getSystemService("sensor");
            }
            this.isRunning = true;
            SensorManager sensorManager = this.sensorManager;
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(4), i);
        }
    }

    private void stopGyro() {
        if (!this.isRunning) {
            return;
        }
        this.isRunning = false;
        this.sensorManager.unregisterListener(this);
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void unload() {
        stopGyro();
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == 4) {
            float[] fArr = sensorEvent.values;
            HashMap hashMap = new HashMap();
            hashMap.put("x", Float.valueOf(fArr[0]));
            hashMap.put("y", Float.valueOf(fArr[1]));
            hashMap.put("z", Float.valueOf(fArr[2]));
            this.executor.executeEvent("gyroscope", hashMap, null);
        }
    }
}
