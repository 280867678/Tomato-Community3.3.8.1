package com.tencent.rtmp.downloader;

import com.tencent.rtmp.TXPlayerAuthBuilder;

/* loaded from: classes3.dex */
public class TXVodDownloadDataSource {
    public static final int QUALITY_2K = 5;
    public static final int QUALITY_4K = 6;
    public static final int QUALITY_FHD = 4;
    public static final int QUALITY_FLU = 1;
    public static final int QUALITY_HD = 3;
    public static final int QUALITY_OD = 0;
    public static final int QUALITY_SD = 2;
    protected TXPlayerAuthBuilder authBuilder;
    protected int quality;
    protected String token;

    /* JADX INFO: Access modifiers changed from: protected */
    public static String qualityToId(int i) {
        return i == 1 ? "FLU" : i == 2 ? "SD" : i == 3 ? "HD" : i == 4 ? "FHD" : i == 5 ? "2K" : i == 6 ? "4K" : "";
    }

    public TXVodDownloadDataSource(TXPlayerAuthBuilder tXPlayerAuthBuilder, int i) {
        this.authBuilder = tXPlayerAuthBuilder;
        this.quality = i;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public TXPlayerAuthBuilder getAuthBuilder() {
        return this.authBuilder;
    }

    public int getQuality() {
        return this.quality;
    }

    public String getToken() {
        return this.token;
    }
}
