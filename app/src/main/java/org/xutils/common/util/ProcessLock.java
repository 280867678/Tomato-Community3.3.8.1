package org.xutils.common.util;

import android.text.TextUtils;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.xutils.C5540x;

/* loaded from: classes4.dex */
public final class ProcessLock implements Closeable {
    private final File mFile;
    private final FileLock mFileLock;
    private final String mLockName;
    private final Closeable mStream;
    private final boolean mWriteMode;
    private static final DoubleKeyValueMap<String, Integer, ProcessLock> LOCK_MAP = new DoubleKeyValueMap<>();
    private static final DecimalFormat FORMAT = new DecimalFormat("0.##################");

    static {
        IOUtil.deleteFileOrDir(C5540x.app().getDir("process_lock", 0));
    }

    private ProcessLock(String str, File file, FileLock fileLock, Closeable closeable, boolean z) {
        this.mLockName = str;
        this.mFileLock = fileLock;
        this.mFile = file;
        this.mStream = closeable;
        this.mWriteMode = z;
    }

    public static ProcessLock tryLock(String str, boolean z) {
        return tryLockInternal(str, customHash(str), z);
    }

    public static ProcessLock tryLock(String str, boolean z, long j) throws InterruptedException {
        long currentTimeMillis = System.currentTimeMillis() + j;
        String customHash = customHash(str);
        ProcessLock processLock = null;
        while (System.currentTimeMillis() < currentTimeMillis && (processLock = tryLockInternal(str, customHash, z)) == null) {
            try {
                Thread.sleep(1L);
            } catch (InterruptedException e) {
                throw e;
            } catch (Throwable unused) {
            }
        }
        return processLock;
    }

    public boolean isValid() {
        return isValid(this.mFileLock);
    }

    public void release() {
        release(this.mLockName, this.mFileLock, this.mFile, this.mStream);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        release();
    }

    private static boolean isValid(FileLock fileLock) {
        return fileLock != null && fileLock.isValid();
    }

    private static void release(String str, FileLock fileLock, File file, Closeable closeable) {
        synchronized (LOCK_MAP) {
            if (fileLock != null) {
                LOCK_MAP.remove(str, Integer.valueOf(fileLock.hashCode()));
                ConcurrentHashMap<Integer, ProcessLock> concurrentHashMap = LOCK_MAP.get(str);
                if (concurrentHashMap == null || concurrentHashMap.isEmpty()) {
                    IOUtil.deleteFileOrDir(file);
                }
                if (fileLock.channel().isOpen()) {
                    fileLock.release();
                }
                IOUtil.closeQuietly(fileLock.channel());
            }
            IOUtil.closeQuietly(closeable);
        }
    }

    private static String customHash(String str) {
        if (TextUtils.isEmpty(str)) {
            return "0";
        }
        double d = 0.0d;
        byte[] bytes = str.getBytes();
        for (int i = 0; i < str.length(); i++) {
            d = ((d * 255.0d) + bytes[i]) * 0.005d;
        }
        return FORMAT.format(d);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static ProcessLock tryLockInternal(String str, String str2, boolean z) {
        Throwable th;
        FileInputStream fileInputStream;
        FileChannel fileChannel;
        synchronized (LOCK_MAP) {
            ConcurrentHashMap<Integer, ProcessLock> concurrentHashMap = LOCK_MAP.get(str);
            if (concurrentHashMap != null && !concurrentHashMap.isEmpty()) {
                Iterator<Map.Entry<Integer, ProcessLock>> it2 = concurrentHashMap.entrySet().iterator();
                while (it2.hasNext()) {
                    ProcessLock value = it2.next().getValue();
                    if (value != null) {
                        if (!value.isValid()) {
                            it2.remove();
                        } else if (z) {
                            return null;
                        } else {
                            if (value.mWriteMode) {
                                return null;
                            }
                        }
                    } else {
                        it2.remove();
                    }
                }
            }
            try {
                File file = new File(C5540x.app().getDir("process_lock", 0), str2);
                if (file.exists() || file.createNewFile()) {
                    if (z) {
                        FileOutputStream fileOutputStream = new FileOutputStream(file, false);
                        fileChannel = fileOutputStream.getChannel();
                        fileInputStream = fileOutputStream;
                    } else {
                        FileInputStream fileInputStream2 = new FileInputStream(file);
                        fileChannel = fileInputStream2.getChannel();
                        fileInputStream = fileInputStream2;
                    }
                    try {
                        if (fileChannel != null) {
                            FileLock tryLock = fileChannel.tryLock(0L, Long.MAX_VALUE, !z);
                            if (isValid(tryLock)) {
                                ProcessLock processLock = new ProcessLock(str, file, tryLock, fileInputStream, z);
                                LOCK_MAP.put(str, Integer.valueOf(tryLock.hashCode()), processLock);
                                return processLock;
                            }
                            release(str, tryLock, file, fileInputStream);
                        } else {
                            throw new IOException("can not get file channel:" + file.getAbsolutePath());
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        LogUtil.m46d("tryLock: " + str + ", " + th.getMessage());
                        IOUtil.closeQuietly(fileInputStream);
                        IOUtil.closeQuietly(fileChannel);
                        return null;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                fileInputStream = null;
                fileChannel = null;
            }
            return null;
        }
    }

    public String toString() {
        return this.mLockName + ": " + this.mFile.getName();
    }

    protected void finalize() throws Throwable {
        super.finalize();
        release();
    }
}
