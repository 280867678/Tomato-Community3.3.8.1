package com.one.tomato.entity.p079db;

import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.WebAppChannel */
/* loaded from: classes3.dex */
public class WebAppChannel extends LitePalSupport {
    private String appAlias;
    private String appChannelCode;
    private int appId;
    private String channelId;
    private String subChannelId;

    public String getAppAlias() {
        return this.appAlias;
    }

    public void setAppAlias(String str) {
        this.appAlias = str;
    }

    public String getAppChannelCode() {
        return this.appChannelCode;
    }

    public void setAppChannelCode(String str) {
        this.appChannelCode = str;
    }

    public int getAppId() {
        return this.appId;
    }

    public void setAppId(int i) {
        this.appId = i;
    }

    public String getSubChannelId() {
        return this.subChannelId;
    }

    public void setSubChannelId(String str) {
        this.subChannelId = str;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String str) {
        this.channelId = str;
    }
}
