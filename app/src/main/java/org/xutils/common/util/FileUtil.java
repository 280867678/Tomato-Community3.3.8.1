package org.xutils.common.util;

import android.os.Environment;
import android.os.StatFs;
import com.gen.p059mh.webapps.listener.WebAppInformation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.xutils.C5540x;

/* loaded from: classes4.dex */
public class FileUtil {
    private FileUtil() {
    }

    public static File getCacheDir(String str) {
        File file;
        if (existsSdcard().booleanValue()) {
            File externalCacheDir = C5540x.app().getExternalCacheDir();
            if (externalCacheDir == null) {
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                file = new File(externalStorageDirectory, "Android/data/" + C5540x.app().getPackageName() + "/cache/" + str);
            } else {
                file = new File(externalCacheDir, str);
            }
        } else {
            file = new File(C5540x.app().getCacheDir(), str);
        }
        if (file.exists() || file.mkdirs()) {
            return file;
        }
        return null;
    }

    public static boolean isDiskAvailable() {
        return getDiskAvailableSize() > WebAppInformation.maxCacheSize;
    }

    public static long getDiskAvailableSize() {
        if (!existsSdcard().booleanValue()) {
            return 0L;
        }
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        return statFs.getAvailableBlocks() * statFs.getBlockSize();
    }

    public static Boolean existsSdcard() {
        return Boolean.valueOf(Environment.getExternalStorageState().equals("mounted"));
    }

    public static long getFileOrDirSize(File file) {
        long j = 0;
        if (!file.exists()) {
            return 0L;
        }
        if (!file.isDirectory()) {
            return file.length();
        }
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File file2 : listFiles) {
                j += getFileOrDirSize(file2);
            }
        }
        return j;
    }

    public static boolean copy(String str, String str2) {
        FileInputStream fileInputStream;
        File file = new File(str);
        if (!file.exists()) {
            return false;
        }
        File file2 = new File(str2);
        IOUtil.deleteFileOrDir(file2);
        File parentFile = file2.getParentFile();
        if (!parentFile.exists() && !parentFile.mkdirs()) {
            return false;
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                fileOutputStream = new FileOutputStream(file2);
                try {
                    IOUtil.copy(fileInputStream, fileOutputStream);
                    return true;
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream;
                    try {
                        LogUtil.m45d(th.getMessage(), th);
                        return false;
                    } finally {
                        IOUtil.closeQuietly(fileInputStream);
                        IOUtil.closeQuietly(fileOutputStream);
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
        }
    }
}
