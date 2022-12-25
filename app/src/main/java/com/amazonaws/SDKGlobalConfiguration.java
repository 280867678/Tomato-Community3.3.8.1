package com.amazonaws;

import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class SDKGlobalConfiguration {
    private static final AtomicInteger GLOBAL_TIME_OFFSET = new AtomicInteger(0);

    public static void setGlobalTimeOffset(int i) {
        GLOBAL_TIME_OFFSET.set(i);
    }

    public static int getGlobalTimeOffset() {
        return GLOBAL_TIME_OFFSET.get();
    }
}
