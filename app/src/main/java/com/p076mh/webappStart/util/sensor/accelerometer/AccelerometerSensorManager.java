package com.p076mh.webappStart.util.sensor.accelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.gen.p059mh.webapp_extensions.WebApplication;
import com.gen.p059mh.webapps.utils.Logger;
import com.p076mh.webappStart.util.sensor.SensorUtil;

/* renamed from: com.mh.webappStart.util.sensor.accelerometer.AccelerometerSensorManager */
/* loaded from: classes3.dex */
public class AccelerometerSensorManager implements SensorEventListener {
    private static final AccelerometerSensorManager ourInstance = new AccelerometerSensorManager();
    private Sensor accelerometer_sensor;
    private AccelerometerSensorEventValueListener orientationSensorEventValueListener;
    private SensorManager sensorManager;

    /* renamed from: com.mh.webappStart.util.sensor.accelerometer.AccelerometerSensorManager$AccelerometerSensorEventValueListener */
    /* loaded from: classes3.dex */
    public interface AccelerometerSensorEventValueListener {
        void onSensorValueChanged(float[] fArr);
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public static AccelerometerSensorManager getInstance() {
        return ourInstance;
    }

    private AccelerometerSensorManager() {
    }

    public boolean startListen(AccelerometerSensorEventValueListener accelerometerSensorEventValueListener, String str) {
        this.orientationSensorEventValueListener = accelerometerSensorEventValueListener;
        this.sensorManager = (SensorManager) WebApplication.getInstance().getApplication().getSystemService("sensor");
        this.accelerometer_sensor = this.sensorManager.getDefaultSensor(1);
        Sensor sensor = this.accelerometer_sensor;
        if (sensor == null) {
            Logger.m4112i("OrientationSensorManage", "当前手机不支持加速度传感器: ");
            return false;
        }
        this.sensorManager.registerListener(this, sensor, SensorUtil.getSensorDelayValue(str));
        return true;
    }

    public void stopListen() {
        Sensor sensor;
        SensorManager sensorManager = this.sensorManager;
        if (sensorManager != null && (sensor = this.accelerometer_sensor) != null) {
            sensorManager.unregisterListener(this, sensor);
        }
        this.orientationSensorEventValueListener = null;
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        AccelerometerSensorEventValueListener accelerometerSensorEventValueListener;
        if (sensorEvent.sensor.getType() != 1 || (accelerometerSensorEventValueListener = this.orientationSensorEventValueListener) == null) {
            return;
        }
        accelerometerSensorEventValueListener.onSensorValueChanged(sensorEvent.values);
    }
}
