package com.p076mh.webappStart.android_plugin_impl.beans;

import com.p076mh.webappStart.android_plugin_impl.beans.base.BasePluginParamsBean;

/* renamed from: com.mh.webappStart.android_plugin_impl.beans.CompressImageParamsBean */
/* loaded from: classes3.dex */
public class CompressImageParamsBean extends BasePluginParamsBean {
    private int quality = 80;
    private String src;

    public String getSrc() {
        return this.src;
    }

    public void setSrc(String str) {
        this.src = str;
    }

    public int getQuality() {
        return this.quality;
    }

    public void setQuality(int i) {
        this.quality = i;
    }
}
