package com.king.zxing;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import com.king.zxing.camera.CameraManager;
import com.king.zxing.camera.FrontLightMode;

/* loaded from: classes3.dex */
final class AmbientLightManager implements SensorEventListener {
    private CameraManager cameraManager;
    private final Context context;
    private Sensor lightSensor;
    private float tooDarkLux = 45.0f;
    private float brightEnoughLux = 100.0f;

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AmbientLightManager(Context context) {
        this.context = context;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void start(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
        if (FrontLightMode.readPref(PreferenceManager.getDefaultSharedPreferences(this.context)) == FrontLightMode.AUTO) {
            SensorManager sensorManager = (SensorManager) this.context.getSystemService("sensor");
            this.lightSensor = sensorManager.getDefaultSensor(5);
            Sensor sensor = this.lightSensor;
            if (sensor == null) {
                return;
            }
            sensorManager.registerListener(this, sensor, 3);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void stop() {
        if (this.lightSensor != null) {
            ((SensorManager) this.context.getSystemService("sensor")).unregisterListener(this);
            this.cameraManager = null;
            this.lightSensor = null;
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        float f = sensorEvent.values[0];
        CameraManager cameraManager = this.cameraManager;
        if (cameraManager != null) {
            if (f <= this.tooDarkLux) {
                cameraManager.sensorChanged(true, f);
            } else if (f < this.brightEnoughLux) {
            } else {
                cameraManager.sensorChanged(false, f);
            }
        }
    }

    public void setTooDarkLux(float f) {
        this.tooDarkLux = f;
    }

    public void setBrightEnoughLux(float f) {
        this.brightEnoughLux = f;
    }
}
