package com.google.android.exoplayer2.util;

import java.io.IOException;

/* loaded from: classes.dex */
public final class PriorityTaskManager {
    public void add(int i) {
        throw null;
    }

    public void remove(int i) {
        throw null;
    }

    /* loaded from: classes.dex */
    public static class PriorityTooLowException extends IOException {
        public PriorityTooLowException(int i, int i2) {
            super("Priority too low [priority=" + i + ", highest=" + i2 + "]");
        }
    }
}
