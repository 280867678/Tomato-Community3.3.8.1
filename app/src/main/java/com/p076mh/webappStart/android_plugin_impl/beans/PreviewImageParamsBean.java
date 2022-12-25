package com.p076mh.webappStart.android_plugin_impl.beans;

import com.p076mh.webappStart.android_plugin_impl.beans.base.BasePluginParamsBean;
import java.util.List;

/* renamed from: com.mh.webappStart.android_plugin_impl.beans.PreviewImageParamsBean */
/* loaded from: classes3.dex */
public class PreviewImageParamsBean extends BasePluginParamsBean {
    private String current;
    private List<String> urls;

    public List<String> getUrls() {
        return this.urls;
    }

    public void setUrls(List<String> list) {
        this.urls = list;
    }

    public String getCurrent() {
        return this.current;
    }

    public void setCurrent(String str) {
        this.current = str;
    }

    public String toString() {
        return "PreviewImageParamsBean{urls=" + this.urls + ", current='" + this.current + "'}";
    }
}
