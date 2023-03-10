package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;

/* loaded from: classes2.dex */
public class MemoryChunkUtil {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static int adjustByteCount(int i, int i2, int i3) {
        return Math.min(Math.max(0, i3 - i), i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void checkBounds(int i, int i2, int i3, int i4, int i5) {
        boolean z = true;
        Preconditions.checkArgument(i4 >= 0);
        Preconditions.checkArgument(i >= 0);
        Preconditions.checkArgument(i3 >= 0);
        Preconditions.checkArgument(i + i4 <= i5);
        if (i3 + i4 > i2) {
            z = false;
        }
        Preconditions.checkArgument(z);
    }
}
