package com.p076mh.webappStart.android_plugin_impl.beans;

import com.p076mh.webappStart.android_plugin_impl.beans.base.BasePluginParamsBean;

/* renamed from: com.mh.webappStart.android_plugin_impl.beans.ShowLoadingParamsBean */
/* loaded from: classes3.dex */
public class ShowLoadingParamsBean extends BasePluginParamsBean {
    private String title = "";
    private boolean mask = false;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public boolean isMask() {
        return this.mask;
    }

    public void setMask(boolean z) {
        this.mask = z;
    }
}
