package com.google.android.exoplayer2.source;

import java.io.IOException;

/* loaded from: classes.dex */
public final class ClippingMediaSource$IllegalClippingException extends IOException {
    public static final int REASON_INVALID_PERIOD_COUNT = 0;
    public static final int REASON_NOT_SEEKABLE_TO_START = 1;
    public static final int REASON_START_EXCEEDS_END = 2;
    public final int reason;

    private static String getReasonDescription(int i) {
        return i != 0 ? i != 1 ? i != 2 ? "unknown" : "start exceeds end" : "not seekable to start" : "invalid period count";
    }

    public ClippingMediaSource$IllegalClippingException(int i) {
        super("Illegal clipping: " + getReasonDescription(i));
        this.reason = i;
    }
}
