package com.danikula.videocache.file.strategy;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class StorageCacheManager {
    private static StorageCacheManager storageManager;
    private FileStrategy fileStrategy;
    private List<StorageWatch> storageStrategys = new ArrayList();
    private final String[] EXTERNAL_GROUP = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};

    public static StorageCacheManager getInstance() {
        if (storageManager == null) {
            storageManager = new StorageCacheManager();
        }
        return storageManager;
    }

    public StorageCacheManager() {
        init();
    }

    private void init() {
        StorageWatch storageWatch = new StorageWatch(1, 0L, 524288000L);
        StorageWatch storageWatch2 = new StorageWatch(2, 524288001L, 1073741824L);
        StorageWatch storageWatch3 = new StorageWatch(3, 1073741825L, 2147483648L);
        StorageWatch storageWatch4 = new StorageWatch(4, 2147483649L, 4294967296L);
        StorageWatch storageWatch5 = new StorageWatch(5, 4294967297L, 6442450944L);
        StorageWatch storageWatch6 = new StorageWatch(6, 6442450945L, 8589934592L);
        StorageWatch storageWatch7 = new StorageWatch(7, 8589934593L, 10737418240L);
        StorageWatch storageWatch8 = new StorageWatch(8, 10737418241L, 1099511627776L);
        this.storageStrategys.add(storageWatch);
        this.storageStrategys.add(storageWatch2);
        this.storageStrategys.add(storageWatch3);
        this.storageStrategys.add(storageWatch4);
        this.storageStrategys.add(storageWatch5);
        this.storageStrategys.add(storageWatch6);
        this.storageStrategys.add(storageWatch7);
        this.storageStrategys.add(storageWatch8);
    }

    public void runningCacheTs(String str) {
        FileStrategy fileStrategy = this.fileStrategy;
        if (fileStrategy == null) {
            return;
        }
        try {
            fileStrategy.runningCacheTs(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean useNotCache() {
        if (this.fileStrategy == null) {
        }
        return false;
    }

    public boolean hasStoragePermissions(Context context) {
        String[] strArr = this.EXTERNAL_GROUP;
        int length = strArr.length;
        int i = 0;
        boolean z = false;
        while (i < length) {
            if (!hasPermission(context, strArr[i])) {
                return false;
            }
            i++;
            z = true;
        }
        return z;
    }

    private boolean hasPermission(Context context, String str) {
        return ContextCompat.checkSelfPermission(context, str) == 0;
    }
}
