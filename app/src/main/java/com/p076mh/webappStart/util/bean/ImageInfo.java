package com.p076mh.webappStart.util.bean;

import com.p076mh.webappStart.android_plugin_impl.beans.base.BasePluginParamsBean;

/* renamed from: com.mh.webappStart.util.bean.ImageInfo */
/* loaded from: classes3.dex */
public class ImageInfo extends BasePluginParamsBean {
    private int height;
    private String orientation;
    private String path;
    private String type;
    private int width;

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public String getOrientation() {
        return this.orientation;
    }

    public void setOrientation(String str) {
        this.orientation = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String toString() {
        return "ImageInfo{width=" + this.width + ", height=" + this.height + ", path='" + this.path + "', orientation='" + this.orientation + "', type='" + this.type + "'}";
    }
}
