package com.p076mh.webappStart.android_plugin_impl.beans;

/* renamed from: com.mh.webappStart.android_plugin_impl.beans.CameraType */
/* loaded from: classes3.dex */
public enum CameraType {
    CAMERA_TYPE_FRONT("front"),
    CAMERA_TYPE_BACK("back");
    
    private String desc;

    CameraType(String str) {
        this.desc = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.desc;
    }
}
