package com.danikula.videocache.file;

/* loaded from: classes2.dex */
public class TotalSizeLruDiskUsage extends LruDiskUsage {
    public TotalSizeLruDiskUsage(long j) {
        if (j > 0) {
            return;
        }
        throw new IllegalArgumentException("Max size must be positive number!");
    }
}
