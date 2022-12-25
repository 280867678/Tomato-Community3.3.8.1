package com.p076mh.webappStart.util.video_compress;

/* renamed from: com.mh.webappStart.util.video_compress.VideoInfo */
/* loaded from: classes3.dex */
public class VideoInfo {
    private int duration;
    private int height;
    private long size;
    private int width;

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int i) {
        this.duration = i;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long j) {
        this.size = j;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public String toString() {
        return "VideoInfo{duration=" + this.duration + ", size=" + this.size + ", height=" + this.height + ", width=" + this.width + '}';
    }
}
