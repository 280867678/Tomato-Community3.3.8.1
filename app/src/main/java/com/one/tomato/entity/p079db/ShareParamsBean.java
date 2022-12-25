package com.one.tomato.entity.p079db;

import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.ShareParamsBean */
/* loaded from: classes3.dex */
public class ShareParamsBean extends LitePalSupport {
    private String keyApp;
    private String postShare;
    private String shareAddress;
    private String shareDomain;
    private String videoShare;

    public void setShareAddress(String str) {
        this.shareAddress = str;
    }

    public String getShareAddress() {
        return this.shareAddress;
    }

    public void setPostShare(String str) {
        this.postShare = str;
    }

    public void setVideoShare(String str) {
        this.videoShare = str;
    }

    public String getPostShare() {
        return this.postShare;
    }

    public String getVideoShare() {
        return this.videoShare;
    }

    public String getKeyApp() {
        return this.keyApp;
    }

    public void setKeyApp(String str) {
        this.keyApp = str;
    }

    public String getShareDomain() {
        return this.shareDomain;
    }

    public void setShareDomain(String str) {
        this.shareDomain = str;
    }
}
