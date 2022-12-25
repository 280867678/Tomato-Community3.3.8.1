package com.p076mh.webappStart.android_plugin_impl.beans;

import com.p076mh.webappStart.android_plugin_impl.beans.base.BasePluginParamsBean;
import java.util.List;

/* renamed from: com.mh.webappStart.android_plugin_impl.beans.ChooseVideoParamsBean */
/* loaded from: classes3.dex */
public class ChooseVideoParamsBean extends BasePluginParamsBean {
    private List<String> sourceType;
    private boolean compressed = true;
    private int maxDuration = 60;
    private String camera = "back";

    public List<String> getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(List<String> list) {
        this.sourceType = list;
    }

    public boolean isCompressed() {
        return this.compressed;
    }

    public void setCompressed(boolean z) {
        this.compressed = z;
    }

    public int getMaxDuration() {
        return this.maxDuration;
    }

    public void setMaxDuration(int i) {
        this.maxDuration = i;
    }

    public String getCamera() {
        return this.camera;
    }

    public void setCamera(String str) {
        this.camera = str;
    }

    public String toString() {
        return "ChooseVideoParamsBean{sourceType=" + this.sourceType + ", compressed=" + this.compressed + ", maxDuration=" + this.maxDuration + ", camera='" + this.camera + "'}";
    }
}
