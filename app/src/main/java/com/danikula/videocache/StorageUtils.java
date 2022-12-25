package com.danikula.videocache;

import android.content.Context;
import android.os.Environment;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: classes2.dex */
public class StorageUtils {
    private static final Logger LOG = LoggerFactory.getLogger("StorageUtils");

    public static File getIndividualCacheDirectory(Context context) {
        return new File(getCacheDirectory(context, true), "video");
    }

    private static File getCacheDirectory(Context context, boolean z) {
        String str;
        try {
            str = Environment.getExternalStorageState();
        } catch (NullPointerException unused) {
            str = "";
        }
        File externalCacheDir = (!z || !"mounted".equals(str)) ? null : getExternalCacheDir(context);
        if (externalCacheDir == null) {
            externalCacheDir = context.getCacheDir();
        }
        if (externalCacheDir == null) {
            String str2 = "/data/data/" + context.getPackageName() + "/cache/";
            LOG.warn("Can't define system cache directory! '" + str2 + "%s' will be used.");
            return new File(str2);
        }
        return externalCacheDir;
    }

    private static File getExternalCacheDir(Context context) {
        File file = new File(new File(new File(new File(Environment.getExternalStorageDirectory(), "Android"), AopConstants.APP_PROPERTIES_KEY), context.getPackageName()), "files");
        if (file.exists() || file.mkdirs()) {
            return file;
        }
        LOG.warn("Unable to create external cache directory");
        return null;
    }

    public static File getSDDir(Context context) {
        if (hasSdcard()) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            return externalStorageDirectory == null ? context.getCacheDir() : externalStorageDirectory;
        }
        return context.getCacheDir();
    }

    public static File getSDAppDir(Context context) {
        File file = new File(getSDDir(context), "tomato");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File getVideoDownloadDir(Context context) {
        File file = hasSdcard() ? new File(getSDAppDir(context), "video") : null;
        if (file == null) {
            file = new File(context.getCacheDir(), "video");
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static boolean hasSdcard() {
        return "mounted".equals(Environment.getExternalStorageState());
    }
}
