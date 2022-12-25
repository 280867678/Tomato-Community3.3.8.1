package com.p076mh.webappStart.util.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.gen.p059mh.webapp_extensions.WebApplication;
import com.gen.p059mh.webapps.utils.Logger;

/* renamed from: com.mh.webappStart.util.sensor.OrientationSensorManager */
/* loaded from: classes3.dex */
public class OrientationSensorManager implements SensorEventListener {
    private static final OrientationSensorManager ourInstance = new OrientationSensorManager();
    private int curAccuracy = -1;
    private OrientationSensorEventValueListener orientationSensorEventValueListener;
    private Sensor orientation_sensor;
    private SensorManager sensorManager;

    /* renamed from: com.mh.webappStart.util.sensor.OrientationSensorManager$OrientationSensorEventValueListener */
    /* loaded from: classes3.dex */
    public interface OrientationSensorEventValueListener {
        void onSensorValueChanged(float[] fArr, int i);
    }

    public static OrientationSensorManager getInstance() {
        return ourInstance;
    }

    private OrientationSensorManager() {
    }

    public boolean startListen(OrientationSensorEventValueListener orientationSensorEventValueListener, String str) {
        this.orientationSensorEventValueListener = orientationSensorEventValueListener;
        this.sensorManager = (SensorManager) WebApplication.getInstance().getApplication().getSystemService("sensor");
        this.orientation_sensor = this.sensorManager.getDefaultSensor(3);
        Sensor sensor = this.orientation_sensor;
        if (sensor == null) {
            Logger.m4112i("OrientationSensorManage", "当前手机不支持方向传感器: ");
            return false;
        }
        this.sensorManager.registerListener(this, sensor, SensorUtil.getSensorDelayValue(str));
        return true;
    }

    public void stopListen() {
        Sensor sensor;
        SensorManager sensorManager = this.sensorManager;
        if (sensorManager != null && (sensor = this.orientation_sensor) != null) {
            sensorManager.unregisterListener(this, sensor);
        }
        this.orientationSensorEventValueListener = null;
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        OrientationSensorEventValueListener orientationSensorEventValueListener;
        if (sensorEvent.sensor.getType() != 3 || (orientationSensorEventValueListener = this.orientationSensorEventValueListener) == null) {
            return;
        }
        orientationSensorEventValueListener.onSensorValueChanged(sensorEvent.values, this.curAccuracy);
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i) {
        this.curAccuracy = i;
    }
}
