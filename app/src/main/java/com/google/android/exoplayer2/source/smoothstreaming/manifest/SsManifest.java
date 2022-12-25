package com.google.android.exoplayer2.source.smoothstreaming.manifest;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.offline.FilterableManifest;

/* loaded from: classes3.dex */
public class SsManifest implements FilterableManifest<SsManifest, Object> {
    public final long durationUs;
    public final long dvrWindowLengthUs;
    public final boolean isLive;
    public final ProtectionElement protectionElement;
    public final StreamElement[] streamElements;

    /* loaded from: classes.dex */
    public static class ProtectionElement {
        public final byte[] data;
    }

    /* loaded from: classes.dex */
    public static class StreamElement {
        public final int chunkCount;
        public final Format[] formats;
        public final long timescale;
        public final int type;

        public int getChunkIndex(long j) {
            throw null;
        }

        public long getStartTimeUs(int i) {
            throw null;
        }
    }
}
