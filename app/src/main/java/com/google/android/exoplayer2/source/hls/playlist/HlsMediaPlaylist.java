package com.google.android.exoplayer2.source.hls.playlist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.drm.DrmInitData;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public final class HlsMediaPlaylist extends HlsPlaylist {
    public final int discontinuitySequence;
    public final DrmInitData drmInitData;
    public final long durationUs;
    public final boolean hasEndTag;
    public final boolean hasIndependentSegmentsTag;
    public final long mediaSequence;
    public final List<Segment> segments;
    public final long startTimeUs;

    /* loaded from: classes.dex */
    public static final class Segment implements Comparable<Long> {
        public final long byterangeLength;
        public final long byterangeOffset;
        public final long durationUs;
        public final String encryptionIV;
        public final String fullSegmentEncryptionKeyUri;
        public final boolean hasGapTag;
        @Nullable
        public final Segment initializationSegment;
        public final int relativeDiscontinuitySequence;
        public final long relativeStartTimeUs;
        public final String url;

        public Segment(String str, long j, long j2) {
            this(str, null, 0L, -1, -9223372036854775807L, null, null, j, j2, false);
        }

        public Segment(String str, Segment segment, long j, int i, long j2, String str2, String str3, long j3, long j4, boolean z) {
            this.url = str;
            this.initializationSegment = segment;
            this.durationUs = j;
            this.relativeDiscontinuitySequence = i;
            this.relativeStartTimeUs = j2;
            this.fullSegmentEncryptionKeyUri = str2;
            this.encryptionIV = str3;
            this.byterangeOffset = j3;
            this.byterangeLength = j4;
            this.hasGapTag = z;
        }

        @Override // java.lang.Comparable
        public int compareTo(@NonNull Long l) {
            if (this.relativeStartTimeUs > l.longValue()) {
                return 1;
            }
            return this.relativeStartTimeUs < l.longValue() ? -1 : 0;
        }
    }

    public HlsMediaPlaylist(int i, String str, List<String> list, long j, long j2, boolean z, int i2, long j3, int i3, long j4, boolean z2, boolean z3, boolean z4, DrmInitData drmInitData, List<Segment> list2) {
        super(str, list);
        this.startTimeUs = j2;
        this.discontinuitySequence = i2;
        this.mediaSequence = j3;
        this.hasIndependentSegmentsTag = z2;
        this.hasEndTag = z3;
        this.drmInitData = drmInitData;
        this.segments = Collections.unmodifiableList(list2);
        if (!list2.isEmpty()) {
            Segment segment = list2.get(list2.size() - 1);
            this.durationUs = segment.relativeStartTimeUs + segment.durationUs;
        } else {
            this.durationUs = 0L;
        }
        if (j == -9223372036854775807L) {
            return;
        }
        int i4 = (j > 0L ? 1 : (j == 0L ? 0 : -1));
    }

    public long getEndTimeUs() {
        return this.startTimeUs + this.durationUs;
    }
}
