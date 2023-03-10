package com.googlecode.mp4parser.authoring;

/* loaded from: classes3.dex */
public class Edit {
    private double mediaRate;
    private long mediaTime;
    private double segmentDuration;
    private long timeScale;

    public Edit(long j, long j2, double d, double d2) {
        this.timeScale = j2;
        this.segmentDuration = d2;
        this.mediaTime = j;
        this.mediaRate = d;
    }

    public long getTimeScale() {
        return this.timeScale;
    }

    public double getSegmentDuration() {
        return this.segmentDuration;
    }

    public long getMediaTime() {
        return this.mediaTime;
    }

    public double getMediaRate() {
        return this.mediaRate;
    }
}
