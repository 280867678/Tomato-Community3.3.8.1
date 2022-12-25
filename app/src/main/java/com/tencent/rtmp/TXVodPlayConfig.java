package com.tencent.rtmp;

import java.util.Map;

/* loaded from: classes3.dex */
public class TXVodPlayConfig {
    String mCacheFolderPath;
    Map<String, String> mHeaders;
    int mMaxCacheItems;
    int mPlayerType;
    int progressInterval;
    int mConnectRetryCount = 3;
    int mConnectRetryInterval = 3;
    int mTimeout = 10;
    boolean enableAccurateSeek = true;
    boolean autoRotate = true;
    boolean smoothSwitchBitrate = false;
    String cacheMp4ExtName = "mp4";

    public void setConnectRetryCount(int i) {
        this.mConnectRetryCount = i;
    }

    public void setConnectRetryInterval(int i) {
        this.mConnectRetryInterval = i;
    }

    public void setTimeout(int i) {
        this.mTimeout = i;
    }

    public void setCacheFolderPath(String str) {
        this.mCacheFolderPath = str;
    }

    public void setMaxCacheItems(int i) {
        this.mMaxCacheItems = i;
    }

    public void setPlayerType(int i) {
        this.mPlayerType = i;
    }

    public void setHeaders(Map<String, String> map) {
        this.mHeaders = map;
    }

    public void setEnableAccurateSeek(boolean z) {
        this.enableAccurateSeek = z;
    }

    public void setAutoRotate(boolean z) {
        this.autoRotate = z;
    }

    public void setSmoothSwitchBitrate(boolean z) {
        this.smoothSwitchBitrate = z;
    }

    public void setCacheMp4ExtName(String str) {
        this.cacheMp4ExtName = str;
    }

    public void setProgressInterval(int i) {
        this.progressInterval = i;
    }
}
