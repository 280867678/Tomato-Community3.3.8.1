package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.accelerometer;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.accelerometer.DeviceAccelerometerCallBackController */
/* loaded from: classes3.dex */
class DeviceAccelerometerCallBackController {
    private static final DeviceAccelerometerCallBackController ourInstance = new DeviceAccelerometerCallBackController();
    private boolean isOn = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static DeviceAccelerometerCallBackController getInstance() {
        return ourInstance;
    }

    private DeviceAccelerometerCallBackController() {
    }

    public boolean isOn() {
        return this.isOn;
    }

    public void setOn(boolean z) {
        this.isOn = z;
    }
}
