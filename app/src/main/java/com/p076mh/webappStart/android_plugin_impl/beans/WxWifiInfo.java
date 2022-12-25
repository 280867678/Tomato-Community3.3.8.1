package com.p076mh.webappStart.android_plugin_impl.beans;

/* renamed from: com.mh.webappStart.android_plugin_impl.beans.WxWifiInfo */
/* loaded from: classes3.dex */
public class WxWifiInfo {
    private String BSSID;
    private String SSID;
    private boolean secure;
    private float signalStrength;

    public String getSSID() {
        return this.SSID;
    }

    public void setSSID(String str) {
        this.SSID = str;
    }

    public String getBSSID() {
        return this.BSSID;
    }

    public void setBSSID(String str) {
        this.BSSID = str;
    }

    public boolean isSecure() {
        return this.secure;
    }

    public void setSecure(boolean z) {
        this.secure = z;
    }

    public float getSignalStrength() {
        return this.signalStrength;
    }

    public void setSignalStrength(float f) {
        this.signalStrength = f;
    }
}
