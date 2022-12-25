package com.p076mh.webappStart.util.sensor.compass;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.gen.p059mh.webapp_extensions.WebApplication;
import com.gen.p059mh.webapps.utils.Logger;
import com.p076mh.webappStart.util.sensor.SensorUtil;

/* renamed from: com.mh.webappStart.util.sensor.compass.CompassManager */
/* loaded from: classes3.dex */
public class CompassManager {
    private static final CompassManager ourInstance = new CompassManager();
    private Sensor aSensor;
    private CompassEventValueListener compassEventValueListener;
    private Sensor mSensor;
    private SensorManager sensorManager;
    private int curAccuracy = -1;
    private float[] accelerometerValues = new float[3];
    private float[] magneticFieldValues = new float[3];
    final SensorEventListener myListener = new SensorEventListener() { // from class: com.mh.webappStart.util.sensor.compass.CompassManager.1
        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == 2) {
                CompassManager.this.magneticFieldValues = sensorEvent.values;
            }
            if (sensorEvent.sensor.getType() == 1) {
                CompassManager.this.accelerometerValues = sensorEvent.values;
            }
            CompassManager.this.calculateOrientation();
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i) {
            CompassManager.this.curAccuracy = i;
        }
    };

    /* renamed from: com.mh.webappStart.util.sensor.compass.CompassManager$CompassEventValueListener */
    /* loaded from: classes3.dex */
    public interface CompassEventValueListener {
        void onSensorValueChanged(float f, int i);
    }

    private float calRealValue(float f) {
        return f >= 0.0f ? f : f + 360.0f;
    }

    public static CompassManager getInstance() {
        return ourInstance;
    }

    private CompassManager() {
    }

    public boolean startListen(CompassEventValueListener compassEventValueListener, String str) {
        this.compassEventValueListener = compassEventValueListener;
        this.sensorManager = (SensorManager) WebApplication.getInstance().getApplication().getSystemService("sensor");
        this.aSensor = this.sensorManager.getDefaultSensor(1);
        this.mSensor = this.sensorManager.getDefaultSensor(2);
        Sensor sensor = this.aSensor;
        if (sensor == null || this.mSensor == null) {
            Logger.m4112i("OrientationSensorManage", "当前手机不支持罗盘: ");
            return false;
        }
        this.sensorManager.registerListener(this.myListener, sensor, SensorUtil.getSensorDelayValue(str));
        this.sensorManager.registerListener(this.myListener, this.mSensor, SensorUtil.getSensorDelayValue(str));
        return true;
    }

    public void stopListen() {
        SensorEventListener sensorEventListener;
        Sensor sensor;
        SensorManager sensorManager = this.sensorManager;
        if (sensorManager != null && (sensorEventListener = this.myListener) != null && (sensor = this.aSensor) != null && this.mSensor != null) {
            sensorManager.unregisterListener(sensorEventListener, sensor);
            this.sensorManager.unregisterListener(this.myListener, this.mSensor);
        }
        this.compassEventValueListener = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void calculateOrientation() {
        float[] fArr = new float[9];
        SensorManager.getRotationMatrix(fArr, null, this.accelerometerValues, this.magneticFieldValues);
        SensorManager.getOrientation(fArr, r0);
        float[] fArr2 = {(float) Math.toDegrees(fArr2[0])};
        Logger.m4112i("OrientationSensorManage", "罗盘方向:" + calRealValue(fArr2[0]) + "");
        CompassEventValueListener compassEventValueListener = this.compassEventValueListener;
        if (compassEventValueListener != null) {
            compassEventValueListener.onSensorValueChanged(calRealValue(fArr2[0]), this.curAccuracy);
        }
    }
}
