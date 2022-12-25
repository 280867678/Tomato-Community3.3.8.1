package org.xutils.cache;

import android.text.TextUtils;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import org.xutils.C5540x;
import org.xutils.DbManager;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.common.util.FileUtil;
import org.xutils.common.util.IOUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.common.util.MD5;
import org.xutils.common.util.ProcessLock;
import org.xutils.config.DbConfigs;
import org.xutils.p148db.sqlite.WhereBuilder;
import org.xutils.p149ex.DbException;
import org.xutils.p149ex.FileLockedException;

/* loaded from: classes4.dex */
public final class LruDiskCache {
    private static final HashMap<String, LruDiskCache> DISK_CACHE_MAP = new HashMap<>(5);
    private boolean available;
    private File cacheDir;
    private long diskCacheSize = 104857600;
    private final Executor trimExecutor = new PriorityExecutor(1, true);
    private long lastTrimTime = 0;
    private final DbManager cacheDb = C5540x.getDb(DbConfigs.HTTP.getConfig());

    public static synchronized LruDiskCache getDiskCache(String str) {
        LruDiskCache lruDiskCache;
        synchronized (LruDiskCache.class) {
            if (TextUtils.isEmpty(str)) {
                str = "xUtils_cache";
            }
            lruDiskCache = DISK_CACHE_MAP.get(str);
            if (lruDiskCache == null) {
                lruDiskCache = new LruDiskCache(str);
                DISK_CACHE_MAP.put(str, lruDiskCache);
            }
        }
        return lruDiskCache;
    }

    private LruDiskCache(String str) {
        this.available = false;
        this.cacheDir = FileUtil.getCacheDir(str);
        File file = this.cacheDir;
        if (file != null && (file.exists() || this.cacheDir.mkdirs())) {
            this.available = true;
        }
        deleteNoIndexFiles();
    }

    public LruDiskCache setMaxSize(long j) {
        if (j > 0) {
            long diskAvailableSize = FileUtil.getDiskAvailableSize();
            if (diskAvailableSize > j) {
                this.diskCacheSize = j;
            } else {
                this.diskCacheSize = diskAvailableSize;
            }
        }
        return this;
    }

    public DiskCacheEntity get(String str) {
        final DiskCacheEntity diskCacheEntity;
        if (!this.available || TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            diskCacheEntity = (DiskCacheEntity) this.cacheDb.selector(DiskCacheEntity.class).where("key", SimpleComparison.EQUAL_TO_OPERATION, str).findFirst();
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
            diskCacheEntity = null;
        }
        if (diskCacheEntity != null) {
            if (diskCacheEntity.getExpires() < System.currentTimeMillis()) {
                return null;
            }
            this.trimExecutor.execute(new Runnable() { // from class: org.xutils.cache.LruDiskCache.1
                @Override // java.lang.Runnable
                public void run() {
                    DiskCacheEntity diskCacheEntity2 = diskCacheEntity;
                    diskCacheEntity2.setHits(diskCacheEntity2.getHits() + 1);
                    diskCacheEntity.setLastAccess(System.currentTimeMillis());
                    try {
                        LruDiskCache.this.cacheDb.update(diskCacheEntity, "hits", "lastAccess");
                    } catch (Throwable th2) {
                        LogUtil.m43e(th2.getMessage(), th2);
                    }
                }
            });
        }
        return diskCacheEntity;
    }

    public void put(DiskCacheEntity diskCacheEntity) {
        if (!this.available || diskCacheEntity == null || TextUtils.isEmpty(diskCacheEntity.getTextContent()) || diskCacheEntity.getExpires() < System.currentTimeMillis()) {
            return;
        }
        try {
            this.cacheDb.replace(diskCacheEntity);
        } catch (DbException e) {
            LogUtil.m43e(e.getMessage(), e);
        }
        trimSize();
    }

    public DiskCacheFile getDiskCacheFile(String str) throws InterruptedException {
        DiskCacheEntity diskCacheEntity;
        ProcessLock tryLock;
        if (!this.available || TextUtils.isEmpty(str) || (diskCacheEntity = get(str)) == null || !new File(diskCacheEntity.getPath()).exists() || (tryLock = ProcessLock.tryLock(diskCacheEntity.getPath(), false, 3000L)) == null || !tryLock.isValid()) {
            return null;
        }
        DiskCacheFile diskCacheFile = new DiskCacheFile(diskCacheEntity, diskCacheEntity.getPath(), tryLock);
        if (diskCacheFile.exists()) {
            return diskCacheFile;
        }
        try {
            this.cacheDb.delete(diskCacheEntity);
            return null;
        } catch (DbException e) {
            LogUtil.m43e(e.getMessage(), e);
            return null;
        }
    }

    public DiskCacheFile createDiskCacheFile(DiskCacheEntity diskCacheEntity) throws IOException {
        if (!this.available || diskCacheEntity == null) {
            return null;
        }
        diskCacheEntity.setPath(new File(this.cacheDir, MD5.md5(diskCacheEntity.getKey())).getAbsolutePath());
        String str = diskCacheEntity.getPath() + ".tmp";
        ProcessLock tryLock = ProcessLock.tryLock(str, true);
        if (tryLock != null && tryLock.isValid()) {
            DiskCacheFile diskCacheFile = new DiskCacheFile(diskCacheEntity, str, tryLock);
            if (!diskCacheFile.getParentFile().exists()) {
                diskCacheFile.mkdirs();
            }
            return diskCacheFile;
        }
        throw new FileLockedException(diskCacheEntity.getPath());
    }

    public void clearCacheFiles() {
        IOUtil.deleteFileOrDir(this.cacheDir);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00aa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public DiskCacheFile commitDiskCacheFile(DiskCacheFile diskCacheFile) throws IOException {
        ProcessLock processLock;
        DiskCacheFile diskCacheFile2;
        InterruptedException e;
        DiskCacheFile diskCacheFile3 = null;
        if (diskCacheFile != null && diskCacheFile.length() < 1) {
            IOUtil.closeQuietly(diskCacheFile);
            return null;
        } else if (!this.available || diskCacheFile == null) {
            return null;
        } else {
            DiskCacheEntity diskCacheEntity = diskCacheFile.cacheEntity;
            if (!diskCacheFile.getName().endsWith(".tmp")) {
                return diskCacheFile;
            }
            try {
                String path = diskCacheEntity.getPath();
                processLock = ProcessLock.tryLock(path, true, 3000L);
                if (processLock != null) {
                    try {
                        if (processLock.isValid()) {
                            diskCacheFile2 = new DiskCacheFile(diskCacheEntity, path, processLock);
                            try {
                                try {
                                    if (diskCacheFile.renameTo(diskCacheFile2)) {
                                        try {
                                            try {
                                                this.cacheDb.replace(diskCacheEntity);
                                            } catch (DbException e2) {
                                                LogUtil.m43e(e2.getMessage(), e2);
                                            }
                                            trimSize();
                                            IOUtil.closeQuietly(diskCacheFile);
                                            IOUtil.deleteFileOrDir(diskCacheFile);
                                            return diskCacheFile2;
                                        } catch (Throwable th) {
                                            th = th;
                                            diskCacheFile3 = diskCacheFile2;
                                            if (diskCacheFile3 != null) {
                                            }
                                            throw th;
                                        }
                                    }
                                    throw new IOException("rename:" + diskCacheFile.getAbsolutePath());
                                } catch (InterruptedException e3) {
                                    e = e3;
                                    try {
                                        LogUtil.m43e(e.getMessage(), e);
                                        if (diskCacheFile != null) {
                                            IOUtil.closeQuietly(diskCacheFile2);
                                            IOUtil.closeQuietly(processLock);
                                            IOUtil.deleteFileOrDir(diskCacheFile2);
                                            return diskCacheFile;
                                        }
                                        IOUtil.closeQuietly(diskCacheFile);
                                        IOUtil.deleteFileOrDir(diskCacheFile);
                                        return diskCacheFile;
                                    } catch (Throwable th2) {
                                        th = th2;
                                        diskCacheFile3 = diskCacheFile;
                                        if (diskCacheFile3 != null) {
                                            IOUtil.closeQuietly(diskCacheFile2);
                                            IOUtil.closeQuietly(processLock);
                                            IOUtil.deleteFileOrDir(diskCacheFile2);
                                        } else {
                                            IOUtil.closeQuietly(diskCacheFile);
                                            IOUtil.deleteFileOrDir(diskCacheFile);
                                        }
                                        throw th;
                                    }
                                }
                            } catch (Throwable th3) {
                                th = th3;
                            }
                        }
                    } catch (InterruptedException e4) {
                        e = e4;
                        diskCacheFile2 = null;
                        e = e;
                        LogUtil.m43e(e.getMessage(), e);
                        if (diskCacheFile != null) {
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        diskCacheFile2 = null;
                    }
                }
                throw new FileLockedException(path);
            } catch (InterruptedException e5) {
                e = e5;
                processLock = null;
                diskCacheFile2 = null;
            } catch (Throwable th5) {
                th = th5;
                processLock = null;
                diskCacheFile2 = null;
            }
        }
    }

    private void trimSize() {
        this.trimExecutor.execute(new Runnable() { // from class: org.xutils.cache.LruDiskCache.2
            @Override // java.lang.Runnable
            public void run() {
                List<DiskCacheEntity> findAll;
                if (LruDiskCache.this.available) {
                    long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - LruDiskCache.this.lastTrimTime < 1000) {
                        return;
                    }
                    LruDiskCache.this.lastTrimTime = currentTimeMillis;
                    LruDiskCache.this.deleteExpiry();
                    try {
                        int count = (int) LruDiskCache.this.cacheDb.selector(DiskCacheEntity.class).count();
                        if (count > 5010 && (findAll = LruDiskCache.this.cacheDb.selector(DiskCacheEntity.class).orderBy("lastAccess").orderBy("hits").limit(count - 5000).offset(0).findAll()) != null && findAll.size() > 0) {
                            for (DiskCacheEntity diskCacheEntity : findAll) {
                                try {
                                    LruDiskCache.this.cacheDb.delete(diskCacheEntity);
                                    String path = diskCacheEntity.getPath();
                                    if (!TextUtils.isEmpty(path)) {
                                        LruDiskCache.this.deleteFileWithLock(path);
                                        LruDiskCache lruDiskCache = LruDiskCache.this;
                                        lruDiskCache.deleteFileWithLock(path + ".tmp");
                                    }
                                } catch (DbException e) {
                                    LogUtil.m43e(e.getMessage(), e);
                                }
                            }
                        }
                    } catch (DbException e2) {
                        LogUtil.m43e(e2.getMessage(), e2);
                    }
                    while (FileUtil.getFileOrDirSize(LruDiskCache.this.cacheDir) > LruDiskCache.this.diskCacheSize) {
                        try {
                            List<DiskCacheEntity> findAll2 = LruDiskCache.this.cacheDb.selector(DiskCacheEntity.class).orderBy("lastAccess").orderBy("hits").limit(10).offset(0).findAll();
                            if (findAll2 != null && findAll2.size() > 0) {
                                for (DiskCacheEntity diskCacheEntity2 : findAll2) {
                                    try {
                                        LruDiskCache.this.cacheDb.delete(diskCacheEntity2);
                                        String path2 = diskCacheEntity2.getPath();
                                        if (!TextUtils.isEmpty(path2)) {
                                            LruDiskCache.this.deleteFileWithLock(path2);
                                            LruDiskCache lruDiskCache2 = LruDiskCache.this;
                                            lruDiskCache2.deleteFileWithLock(path2 + ".tmp");
                                        }
                                    } catch (DbException e3) {
                                        LogUtil.m43e(e3.getMessage(), e3);
                                    }
                                }
                            }
                        } catch (DbException e4) {
                            LogUtil.m43e(e4.getMessage(), e4);
                            return;
                        }
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteExpiry() {
        try {
            WhereBuilder m30b = WhereBuilder.m30b("expires", SimpleComparison.LESS_THAN_OPERATION, Long.valueOf(System.currentTimeMillis()));
            List<DiskCacheEntity> findAll = this.cacheDb.selector(DiskCacheEntity.class).where(m30b).findAll();
            this.cacheDb.delete(DiskCacheEntity.class, m30b);
            if (findAll == null || findAll.size() <= 0) {
                return;
            }
            for (DiskCacheEntity diskCacheEntity : findAll) {
                String path = diskCacheEntity.getPath();
                if (!TextUtils.isEmpty(path)) {
                    deleteFileWithLock(path);
                }
            }
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
        }
    }

    private void deleteNoIndexFiles() {
        this.trimExecutor.execute(new Runnable() { // from class: org.xutils.cache.LruDiskCache.3
            @Override // java.lang.Runnable
            public void run() {
                if (LruDiskCache.this.available) {
                    try {
                        File[] listFiles = LruDiskCache.this.cacheDir.listFiles();
                        if (listFiles == null) {
                            return;
                        }
                        for (File file : listFiles) {
                            if (LruDiskCache.this.cacheDb.selector(DiskCacheEntity.class).where("path", SimpleComparison.EQUAL_TO_OPERATION, file.getAbsolutePath()).count() < 1) {
                                IOUtil.deleteFileOrDir(file);
                            }
                        }
                    } catch (Throwable th) {
                        LogUtil.m43e(th.getMessage(), th);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean deleteFileWithLock(String str) {
        ProcessLock processLock;
        try {
            processLock = ProcessLock.tryLock(str, true);
            if (processLock != null) {
                try {
                    if (processLock.isValid()) {
                        boolean deleteFileOrDir = IOUtil.deleteFileOrDir(new File(str));
                        IOUtil.closeQuietly(processLock);
                        return deleteFileOrDir;
                    }
                } catch (Throwable th) {
                    th = th;
                    IOUtil.closeQuietly(processLock);
                    throw th;
                }
            }
            IOUtil.closeQuietly(processLock);
            return false;
        } catch (Throwable th2) {
            th = th2;
            processLock = null;
        }
    }
}
