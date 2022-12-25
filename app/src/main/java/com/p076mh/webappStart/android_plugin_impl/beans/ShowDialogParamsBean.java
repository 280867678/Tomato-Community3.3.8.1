package com.p076mh.webappStart.android_plugin_impl.beans;

import com.p076mh.webappStart.android_plugin_impl.beans.base.BasePluginParamsBean;

/* renamed from: com.mh.webappStart.android_plugin_impl.beans.ShowDialogParamsBean */
/* loaded from: classes3.dex */
public class ShowDialogParamsBean extends BasePluginParamsBean {
    private String cancelColor;
    private String cancelText;
    private String confirmColor;
    private String confirmText;
    private String content;
    private boolean showCancel;
    private String title;

    public ShowDialogParamsBean() {
        this.title = "";
        this.showCancel = true;
        this.content = "";
        this.cancelText = "取消";
        this.cancelColor = "#000000";
        this.confirmText = "确定";
        this.confirmColor = "#576B95";
    }

    public ShowDialogParamsBean(String str, boolean z, String str2) {
        this.title = "";
        this.showCancel = true;
        this.content = "";
        this.cancelText = "取消";
        this.cancelColor = "#000000";
        this.confirmText = "确定";
        this.confirmColor = "#576B95";
        this.title = str;
        this.showCancel = z;
        this.content = str2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public Boolean getShowCancel() {
        return Boolean.valueOf(this.showCancel);
    }

    public void setShowCancel(Boolean bool) {
        this.showCancel = bool.booleanValue();
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public String getCancelText() {
        return this.cancelText;
    }

    public void setCancelText(String str) {
        this.cancelText = str;
    }

    public String getCancelColor() {
        return this.cancelColor;
    }

    public void setCancelColor(String str) {
        this.cancelColor = str;
    }

    public String getConfirmText() {
        return this.confirmText;
    }

    public void setConfirmText(String str) {
        this.confirmText = str;
    }

    public String getConfirmColor() {
        return this.confirmColor;
    }

    public void setConfirmColor(String str) {
        this.confirmColor = str;
    }
}
