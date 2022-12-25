package com.one.tomato.entity;

import java.io.Serializable;

/* loaded from: classes3.dex */
public class HtmlConfig implements Serializable {
    private boolean isDynamicTitle;
    private String title;
    private String url;
    private Class clazz = null;
    private boolean isPay = false;
    private boolean isInstallPay = false;

    public boolean isInstallPay() {
        return this.isInstallPay;
    }

    public void setInstallPay(boolean z) {
        this.isInstallPay = z;
    }

    public boolean isPay() {
        return this.isPay;
    }

    public void setPay(boolean z) {
        this.isPay = z;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public boolean isDynamicTitle() {
        return this.isDynamicTitle;
    }

    public void setDynamicTitle(boolean z) {
        this.isDynamicTitle = z;
    }

    public Class getClazz() {
        return this.clazz;
    }

    public void setClazz(Class cls) {
        this.clazz = cls;
    }
}
