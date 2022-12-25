package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.compass;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.compass.DeviceCompassCallBackController */
/* loaded from: classes3.dex */
class DeviceCompassCallBackController {
    private static final DeviceCompassCallBackController ourInstance = new DeviceCompassCallBackController();
    private boolean isOn = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static DeviceCompassCallBackController getInstance() {
        return ourInstance;
    }

    private DeviceCompassCallBackController() {
    }

    public boolean isOn() {
        return this.isOn;
    }

    public void setOn(boolean z) {
        this.isOn = z;
    }
}
