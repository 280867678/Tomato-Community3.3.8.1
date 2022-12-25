package com.tencent.rtmp.downloader;

import com.tencent.liteav.network.TXCVodPlayerNetApi;

/* loaded from: classes3.dex */
public class TXVodDownloadMediaInfo {
    protected TXVodDownloadDataSource dataSource;
    protected int downloadSize;
    protected int duration;
    protected boolean isStop;
    protected TXCVodPlayerNetApi netApi;
    protected String playPath;
    protected float progress;
    protected int size;
    protected int tid = -1;
    protected String url;

    public TXVodDownloadDataSource getDataSource() {
        return this.dataSource;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getSize() {
        return this.size;
    }

    public int getDownloadSize() {
        return this.downloadSize;
    }

    public float getProgress() {
        int i = this.size;
        if (i > 0) {
            return this.downloadSize / i;
        }
        return 0.0f;
    }

    public String getPlayPath() {
        return this.playPath;
    }

    public int getTaskId() {
        return this.tid;
    }

    public String getUrl() {
        return this.url;
    }
}
