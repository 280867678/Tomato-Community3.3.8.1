package com.google.android.exoplayer2.extractor;

/* loaded from: classes2.dex */
public interface ExtractorOutput {
    void endTracks();

    void seekMap(SeekMap seekMap);

    TrackOutput track(int i, int i2);
}
