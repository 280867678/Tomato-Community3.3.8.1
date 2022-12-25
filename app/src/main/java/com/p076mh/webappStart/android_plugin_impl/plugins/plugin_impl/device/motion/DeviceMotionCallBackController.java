package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.motion;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.motion.DeviceMotionCallBackController */
/* loaded from: classes3.dex */
class DeviceMotionCallBackController {
    private static final DeviceMotionCallBackController ourInstance = new DeviceMotionCallBackController();
    private boolean isOn = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static DeviceMotionCallBackController getInstance() {
        return ourInstance;
    }

    private DeviceMotionCallBackController() {
    }

    public boolean isOn() {
        return this.isOn;
    }

    public void setOn(boolean z) {
        this.isOn = z;
    }
}
