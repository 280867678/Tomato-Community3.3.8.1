package com.tencent.liteav.videoediter.ffmpeg.jni;

/* loaded from: classes3.dex */
public class FFDecodedFrame {
    public byte[] data;
    public int flags;
    public long pts;
    public int sampleRate;

    public String toString() {
        return "FFDecodedFrame{data size=" + this.data.length + ", pts=" + this.pts + ", flags=" + this.flags + '}';
    }
}
