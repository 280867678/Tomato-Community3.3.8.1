package com.one.tomato.thirdpart.m3u8.download.entity;

/* loaded from: classes3.dex */
public class M3U8Ts implements Comparable<M3U8Ts> {
    private long fileSize;
    private float seconds;
    private String url;

    public M3U8Ts(String str, float f) {
        this.url = str;
        this.seconds = f;
    }

    @Override // java.lang.Comparable
    public int compareTo(M3U8Ts m3U8Ts) {
        return this.url.compareTo(m3U8Ts.url);
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(long j) {
        this.fileSize = j;
    }

    public float getSeconds() {
        return this.seconds;
    }

    public void setSeconds(float f) {
        this.seconds = f;
    }

    public String toString() {
        return "url = " + this.url;
    }
}
