package com.p076mh.webappStart.android_plugin_impl.beans;

import com.p076mh.webappStart.android_plugin_impl.beans.base.BasePluginParamsBean;
import java.util.Map;

/* renamed from: com.mh.webappStart.android_plugin_impl.beans.DownloadFileParamsBean */
/* loaded from: classes3.dex */
public class DownloadFileParamsBean extends BasePluginParamsBean {
    private String url;
    private Map<String, String> header = null;
    private String filePath = null;
    private String downloadID = null;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public Map<String, String> getHeader() {
        return this.header;
    }

    public void setHeader(Map<String, String> map) {
        this.header = map;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String str) {
        this.filePath = str;
    }

    public String getDownloadID() {
        return this.downloadID;
    }

    public void setDownloadID(String str) {
        this.downloadID = str;
    }

    public String toString() {
        return "DownloadFileParamsBean{url='" + this.url + "', header=" + this.header + ", filePath='" + this.filePath + "', downloadID='" + this.downloadID + "'}";
    }
}
