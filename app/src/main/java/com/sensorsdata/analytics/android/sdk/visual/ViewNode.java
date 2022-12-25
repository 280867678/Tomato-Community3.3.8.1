package com.sensorsdata.analytics.android.sdk.visual;

import java.io.Serializable;

/* loaded from: classes3.dex */
public class ViewNode implements Serializable {
    private static final long serialVersionUID = -1242947408632673572L;
    private String viewContent;
    private String viewOriginalPath;
    private String viewPath;
    private String viewPosition;
    private String viewType;

    public ViewNode(String str, String str2) {
        this(null, null, null, str, str2);
    }

    public ViewNode(String str, String str2, String str3) {
        this(str, str2, str3, null, null);
    }

    public ViewNode(String str, String str2, String str3, String str4, String str5) {
        this.viewPosition = str;
        this.viewOriginalPath = str2;
        this.viewPath = str3;
        this.viewContent = str4;
        this.viewType = str5;
    }

    public String getViewPosition() {
        return this.viewPosition;
    }

    public void setViewPosition(String str) {
        this.viewPosition = str;
    }

    public String getViewOriginalPath() {
        return this.viewOriginalPath;
    }

    public void setViewOriginalPath(String str) {
        this.viewOriginalPath = str;
    }

    public String getViewPath() {
        return this.viewPath;
    }

    public void setViewPath(String str) {
        this.viewPath = str;
    }

    public String getViewContent() {
        return this.viewContent;
    }

    public void setViewContent(String str) {
        this.viewContent = str;
    }

    public String getViewType() {
        return this.viewType;
    }

    public void setViewType(String str) {
        this.viewType = str;
    }
}
