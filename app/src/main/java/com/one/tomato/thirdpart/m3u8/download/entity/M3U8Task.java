package com.one.tomato.thirdpart.m3u8.download.entity;

import com.one.tomato.thirdpart.m3u8.download.utils.MUtils;
import com.one.tomato.utils.FileUtil;
import java.util.Objects;

/* loaded from: classes3.dex */
public class M3U8Task {
    private M3U8 m3U8;
    private String postId;
    private boolean preDownload;
    private float progress;
    private long speed;
    private int state = 0;
    private String title;
    private String url;
    private int videoView;

    public M3U8Task(String str) {
        this.url = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && M3U8Task.class == obj.getClass()) {
            return Objects.equals(MUtils.generatePrimaryKeyUrl(this.url), MUtils.generatePrimaryKeyUrl(((M3U8Task) obj).url));
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(MUtils.generatePrimaryKeyUrl(this.url));
    }

    public int getVideoView() {
        return this.videoView;
    }

    public void setVideoView(int i) {
        this.videoView = i;
    }

    public String getFormatSpeed() {
        if (this.speed == 0) {
            return "";
        }
        return FileUtil.formatFileSize(this.speed) + "/s";
    }

    public String getFormatTotalSize() {
        M3U8 m3u8 = this.m3U8;
        return m3u8 == null ? "" : m3u8.getFormatFileSize();
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int i) {
        this.state = i;
    }

    public long getSpeed() {
        return this.speed;
    }

    public void setSpeed(long j) {
        this.speed = j;
    }

    public float getProgress() {
        return this.progress;
    }

    public void setProgress(float f) {
        this.progress = f;
    }

    public M3U8 getM3U8() {
        return this.m3U8;
    }

    public void setM3U8(M3U8 m3u8) {
        this.m3U8 = m3u8;
    }

    public boolean isPreDownload() {
        return this.preDownload;
    }

    public void setPreDownload(boolean z) {
        this.preDownload = z;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getPostId() {
        return this.postId;
    }

    public void setPostId(String str) {
        this.postId = str;
    }
}
