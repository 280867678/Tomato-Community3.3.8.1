package com.google.android.exoplayer2.source.hls;

import android.support.annotation.Nullable;
import java.io.IOException;

/* loaded from: classes.dex */
public final class SampleQueueMappingException extends IOException {
    public SampleQueueMappingException(@Nullable String str) {
        super("Unable to bind a sample queue to TrackGroup with mime type " + str + ".");
    }
}
