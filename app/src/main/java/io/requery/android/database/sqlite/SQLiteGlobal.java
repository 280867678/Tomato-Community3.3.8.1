package io.requery.android.database.sqlite;

import android.os.StatFs;

/* loaded from: classes4.dex */
public final class SQLiteGlobal {
    private static int sDefaultPageSize;
    private static final Object sLock = new Object();

    public static String getDefaultJournalMode() {
        return "TRUNCATE";
    }

    public static String getDefaultSyncMode() {
        return "FULL";
    }

    public static int getJournalSizeLimit() {
        return 524288;
    }

    public static int getWALAutoCheckpoint() {
        return 100;
    }

    public static int getWALConnectionPoolSize() {
        return 4;
    }

    public static String getWALSyncMode() {
        return "FULL";
    }

    private static native int nativeReleaseMemory();

    public static int releaseMemory() {
        return nativeReleaseMemory();
    }

    public static int getDefaultPageSize() {
        synchronized (sLock) {
            if (sDefaultPageSize == 0) {
                sDefaultPageSize = new StatFs("/data").getBlockSize();
            }
        }
        return 1024;
    }
}
