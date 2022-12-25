package com.p076mh.webappStart.android_plugin_impl.beans;

import com.p076mh.webappStart.android_plugin_impl.beans.base.BasePluginParamsBean;

/* renamed from: com.mh.webappStart.android_plugin_impl.beans.ToastParamsBean */
/* loaded from: classes3.dex */
public class ToastParamsBean extends BasePluginParamsBean {
    private String title = "";
    private boolean mask = false;
    private String icon = "success";
    private String image = null;
    private int duration = 1500;

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

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String str) {
        this.icon = str;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String str) {
        this.image = str;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int i) {
        this.duration = i;
    }
}
