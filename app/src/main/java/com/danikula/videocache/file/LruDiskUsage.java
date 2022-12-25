package com.danikula.videocache.file;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import org.slf4j.LoggerFactory;

/* loaded from: classes2.dex */
public abstract class LruDiskUsage implements DiskUsage {
    @Override // com.danikula.videocache.file.DiskUsage
    public void touch(File file) throws IOException {
    }

    public LruDiskUsage() {
        Executors.newSingleThreadExecutor();
    }

    static {
        LoggerFactory.getLogger("LruDiskUsage");
    }
}
