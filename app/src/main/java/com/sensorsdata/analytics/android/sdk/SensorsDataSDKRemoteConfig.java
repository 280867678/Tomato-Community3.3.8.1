package com.sensorsdata.analytics.android.sdk;

import org.json.JSONObject;

/* loaded from: classes3.dex */
public class SensorsDataSDKRemoteConfig {
    static final int REMOTE_EVENT_TYPE_NO_USE = -1;
    private int mAutoTrackEventType;

    /* renamed from: v */
    private String f1883v;
    private boolean disableDebugMode = false;
    private boolean disableSDK = false;
    private int autoTrackMode = -1;

    String getV() {
        return this.f1883v;
    }

    public void setV(String str) {
        this.f1883v = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isDisableDebugMode() {
        return this.disableDebugMode;
    }

    public void setDisableDebugMode(boolean z) {
        this.disableDebugMode = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isDisableSDK() {
        return this.disableSDK;
    }

    public void setDisableSDK(boolean z) {
        this.disableSDK = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getAutoTrackMode() {
        return this.autoTrackMode;
    }

    public void setAutoTrackMode(int i) {
        this.autoTrackMode = i;
        int i2 = this.autoTrackMode;
        if (i2 == -1 || i2 == 0) {
            this.mAutoTrackEventType = 0;
            return;
        }
        if ((i2 & 1) == 1) {
            this.mAutoTrackEventType |= 1;
        }
        if ((this.autoTrackMode & 2) == 2) {
            this.mAutoTrackEventType |= 2;
        }
        if ((this.autoTrackMode & 4) == 4) {
            this.mAutoTrackEventType |= 4;
        }
        if ((this.autoTrackMode & 8) != 8) {
            return;
        }
        this.mAutoTrackEventType |= 8;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getAutoTrackEventType() {
        return this.mAutoTrackEventType;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isAutoTrackEventTypeIgnored(int i) {
        int i2 = this.autoTrackMode;
        if (i2 == -1) {
            return false;
        }
        if (i2 == 0) {
            return true;
        }
        int i3 = this.mAutoTrackEventType;
        return (i | i3) != i3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("v", this.f1883v);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("disableDebugMode", this.disableDebugMode);
            jSONObject2.put("autoTrackMode", this.autoTrackMode);
            jSONObject2.put("disableSDK", this.disableSDK);
            jSONObject.put("configs", jSONObject2);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        return jSONObject;
    }

    public String toString() {
        return "{ v=" + this.f1883v + ", disableDebugMode=" + this.disableDebugMode + ", disableSDK=" + this.disableSDK + ", autoTrackMode=" + this.autoTrackMode + "}";
    }
}
