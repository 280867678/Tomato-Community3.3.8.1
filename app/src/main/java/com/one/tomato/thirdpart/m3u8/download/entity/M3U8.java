package com.one.tomato.thirdpart.m3u8.download.entity;

import android.text.TextUtils;
import com.one.tomato.thirdpart.m3u8.download.utils.MUtils;
import com.one.tomato.utils.FileUtil;
import com.zzbwuhan.beard.BCrypto;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public class M3U8 {
    private String baseUrl;
    private String dirFilePath;
    private long fileSize;
    private String m3u8Key;
    private int m3u8KeyIndex;
    private boolean preDownload;
    private long totalTime;
    private List<M3U8Ts> tsList = new ArrayList();
    private String url;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public void setBaseUrl(String str) {
        this.baseUrl = str;
    }

    public String getDirFilePath() {
        return this.dirFilePath;
    }

    public void setDirFilePath(String str) {
        this.dirFilePath = str;
    }

    public long getTotalTime() {
        this.totalTime = 0L;
        for (M3U8Ts m3U8Ts : this.tsList) {
            this.totalTime += (int) (m3U8Ts.getSeconds() * 1000.0f);
        }
        return this.totalTime;
    }

    public void setTotalTime(long j) {
        this.totalTime = j;
    }

    public long getFileSize() {
        this.fileSize = 0L;
        for (M3U8Ts m3U8Ts : this.tsList) {
            this.fileSize += m3U8Ts.getFileSize();
        }
        return this.fileSize;
    }

    public String getFormatFileSize() {
        this.fileSize = getFileSize();
        long j = this.fileSize;
        return j == 0 ? "" : FileUtil.formatFileSize(j);
    }

    public void setFileSize(long j) {
        this.fileSize = j;
    }

    public List<M3U8Ts> getTsList() {
        return this.tsList;
    }

    public void setTsList(List<M3U8Ts> list) {
        this.tsList = list;
    }

    public void addTs(M3U8Ts m3U8Ts) {
        this.tsList.add(m3U8Ts);
    }

    public boolean isPreDownload() {
        return this.preDownload;
    }

    public void setPreDownload(boolean z) {
        this.preDownload = z;
    }

    public String getM3u8Key() {
        return this.m3u8Key;
    }

    public void setM3u8Key(String str) {
        this.m3u8Key = str;
    }

    public int getM3u8KeyIndex() {
        return this.m3u8KeyIndex;
    }

    public void setM3u8KeyIndex(int i) {
        this.m3u8KeyIndex = i;
    }

    public Long getKeyPtr() {
        if (TextUtils.isEmpty(this.m3u8Key)) {
            return 0L;
        }
        return Long.valueOf(BCrypto.decodeKey(this.m3u8Key, this.m3u8KeyIndex));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && M3U8.class == obj.getClass()) {
            return Objects.equals(MUtils.generatePrimaryKeyUrl(this.url), MUtils.generatePrimaryKeyUrl(((M3U8) obj).url));
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(MUtils.generatePrimaryKeyUrl(this.url));
    }
}
