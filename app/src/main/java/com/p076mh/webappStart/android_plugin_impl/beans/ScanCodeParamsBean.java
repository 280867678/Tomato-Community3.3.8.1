package com.p076mh.webappStart.android_plugin_impl.beans;

import java.util.List;

/* renamed from: com.mh.webappStart.android_plugin_impl.beans.ScanCodeParamsBean */
/* loaded from: classes3.dex */
public class ScanCodeParamsBean {
    private boolean onlyFromCamera = false;
    private List<String> scanType;

    public boolean isOnlyFromCamera() {
        return this.onlyFromCamera;
    }

    public void setOnlyFromCamera(boolean z) {
        this.onlyFromCamera = z;
    }

    public List<String> getScanType() {
        return this.scanType;
    }

    public void setScanType(List<String> list) {
        this.scanType = list;
    }
}
