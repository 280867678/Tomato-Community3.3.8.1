package com.facebook.imagepipeline.transcoder;

/* loaded from: classes2.dex */
public class ImageTranscodeResult {
    private final int mTranscodeStatus;

    public ImageTranscodeResult(int i) {
        this.mTranscodeStatus = i;
    }

    public int getTranscodeStatus() {
        return this.mTranscodeStatus;
    }

    public String toString() {
        return String.format(null, "Status: %d", Integer.valueOf(this.mTranscodeStatus));
    }
}
