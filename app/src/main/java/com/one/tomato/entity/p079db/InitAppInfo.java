package com.one.tomato.entity.p079db;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.InitAppInfo */
/* loaded from: classes3.dex */
public class InitAppInfo extends LitePalSupport {
    private String channelId;
    private String credential;
    private String keyApp;
    private String liveToken;
    @SerializedName(DatabaseFieldConfigLoader.FIELD_NAME_ID)
    private int memberId;
    private String subChannelId;
    private String token;
    private int userType;

    public String getKeyApp() {
        return this.keyApp;
    }

    public void setKeyApp(String str) {
        this.keyApp = str;
    }

    public int getMemberId() {
        return this.memberId;
    }

    public void setMemberId(int i) {
        this.memberId = i;
    }

    public String getCredential() {
        return this.credential;
    }

    public void setCredential(String str) {
        this.credential = str;
    }

    public void setChannelId(String str) {
        this.channelId = str;
    }

    public void setSubChannelId(String str) {
        this.subChannelId = str;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public String getSubChannelId() {
        return this.subChannelId;
    }

    public String getLiveToken() {
        return this.liveToken;
    }

    public String getToken() {
        return this.token;
    }

    public void setLiveToken(String str) {
        this.liveToken = str;
    }

    public int getUserType() {
        return this.userType;
    }

    public void setUserType(int i) {
        this.userType = i;
    }
}
