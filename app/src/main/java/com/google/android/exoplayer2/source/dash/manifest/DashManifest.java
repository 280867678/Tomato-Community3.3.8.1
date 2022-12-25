package com.google.android.exoplayer2.source.dash.manifest;

import com.google.android.exoplayer2.offline.FilterableManifest;

/* loaded from: classes3.dex */
public class DashManifest implements FilterableManifest<DashManifest, Object> {
    public final long availabilityStartTimeMs;
    public final boolean dynamic;

    public final Period getPeriod(int i) {
        throw null;
    }

    public final int getPeriodCount() {
        throw null;
    }

    public final long getPeriodDurationUs(int i) {
        throw null;
    }
}
