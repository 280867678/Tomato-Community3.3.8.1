package com.tencent.liteav.videoediter.ffmpeg.jni;

/* loaded from: classes3.dex */
public class FFMediaInfo {
    public long audioBitrate;
    public long audioDuration;
    public int channels;
    public float fps;
    public int height;
    public int rotation;
    public int sampleRate;
    public long videoBitrate;
    public long videoDuration;
    public int width;

    public String toString() {
        return "FFMediaInfo{rotation=" + this.rotation + ", width=" + this.width + ", height=" + this.height + ", fps=" + this.fps + ", videoBitrate=" + this.videoBitrate + ", videoDuration=" + this.videoDuration + ", sampleRate=" + this.sampleRate + ", channels=" + this.channels + ", audioBitrate=" + this.audioBitrate + ", audioDuration=" + this.audioDuration + '}';
    }
}
