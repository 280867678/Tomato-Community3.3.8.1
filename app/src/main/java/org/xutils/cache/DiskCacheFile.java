package org.xutils.cache;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import org.xutils.common.util.IOUtil;
import org.xutils.common.util.ProcessLock;

/* loaded from: classes4.dex */
public final class DiskCacheFile extends File implements Closeable {
    DiskCacheEntity cacheEntity;
    ProcessLock lock;

    /* JADX INFO: Access modifiers changed from: package-private */
    public DiskCacheFile(DiskCacheEntity diskCacheEntity, String str, ProcessLock processLock) {
        super(str);
        this.cacheEntity = diskCacheEntity;
        this.lock = processLock;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        IOUtil.closeQuietly(this.lock);
    }

    public DiskCacheFile commit() throws IOException {
        return getDiskCache().commitDiskCacheFile(this);
    }

    public LruDiskCache getDiskCache() {
        return LruDiskCache.getDiskCache(getParentFile().getName());
    }

    public DiskCacheEntity getCacheEntity() {
        return this.cacheEntity;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }
}
