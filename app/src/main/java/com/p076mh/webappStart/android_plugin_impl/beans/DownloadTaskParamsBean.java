package com.p076mh.webappStart.android_plugin_impl.beans;

import com.p076mh.webappStart.android_plugin_impl.beans.base.BasePluginParamsBean;

/* renamed from: com.mh.webappStart.android_plugin_impl.beans.DownloadTaskParamsBean */
/* loaded from: classes3.dex */
public class DownloadTaskParamsBean extends BasePluginParamsBean {
    private String downloadID;

    public String getDownloadID() {
        return this.downloadID;
    }

    public void setDownloadID(String str) {
        this.downloadID = str;
    }

    public String toString() {
        return "DownloadTaskParamsBean{downloadID='" + this.downloadID + "'}";
    }
}
