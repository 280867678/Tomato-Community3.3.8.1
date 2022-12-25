package com.one.tomato.utils;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import com.one.tomato.mvp.base.BaseApplication;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/* loaded from: classes3.dex */
public class FileUtil {

    /* renamed from: KB */
    private static float f1756KB = 1024.0f;

    /* renamed from: MB */
    private static float f1757MB = f1756KB * 1024.0f;

    /* renamed from: GB */
    private static float f1755GB = f1757MB * 1024.0f;

    public static boolean hasSD() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static File getSDDir() {
        if (hasSD()) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            return externalStorageDirectory == null ? BaseApplication.getApplication().getCacheDir() : externalStorageDirectory;
        }
        return BaseApplication.getApplication().getCacheDir();
    }

    public static File getSDDCIMDir() {
        File file = new File(getSDDir().getPath() + File.separator + "DCIM" + File.separator + "tomato" + File.separator);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File getSDAppDir() {
        File file = new File(getSDDir(), "tomato");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File getCacheDir() {
        File file = null;
        if (hasSD()) {
            file = BaseApplication.getApplication().getExternalFilesDir(null);
        }
        return file == null ? BaseApplication.getApplication().getCacheDir() : file;
    }

    private static File getDir(String str, boolean z) {
        String path;
        if (z) {
            path = getCacheDir().getPath();
        } else {
            path = getSDAppDir().getPath();
        }
        File file = new File(path + File.separator + str + File.separator);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File getApkCacheDir() {
        return getDir("apk", true);
    }

    public static File getImageCacheDir() {
        return getDir("image", true);
    }

    public static File getVideoDownDir(boolean z) {
        if (z) {
            return getDir("video", false);
        }
        return getDir("video", true);
    }

    public static File getVideoBeanDownDir() {
        return getDir("videoBean", false);
    }

    public static File getCrashCacheDir() {
        return getDir("crash", true);
    }

    public static File getTextCacheDir() {
        return getDir("text", false);
    }

    public static File getDomainDir(String str) {
        return getDir(str, true);
    }

    public static void deleteFolderFile(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        File file = new File(str);
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                return;
            }
            if (listFiles.length == 0) {
                file.delete();
                return;
            }
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isDirectory()) {
                    deleteFolderFile(listFiles[i].getAbsolutePath());
                } else {
                    listFiles[i].delete();
                }
            }
            file.delete();
            return;
        }
        file.delete();
    }

    public static long getFolderSize(File file) {
        long length;
        File[] listFiles = file.listFiles();
        long j = 0;
        if (listFiles == null) {
            return 0L;
        }
        for (int i = 0; i < listFiles.length; i++) {
            if (listFiles[i].isDirectory()) {
                length = getFolderSize(listFiles[i]);
            } else {
                length = listFiles[i].length();
            }
            j += length;
        }
        return j;
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

    public static long getFreeSpaceOfExternalStorage() {
        return getFreeSpaceOfPath(Environment.getExternalStorageDirectory());
    }

    public static long getFreeSpaceOfPath(File file) {
        StatFs statFs = new StatFs(file.getPath());
        return statFs.getAvailableBlocks() * statFs.getBlockSize();
    }

    public static String readAssetFile(String str) {
        InputStream inputStream = null;
        try {
            try {
                inputStream = BaseApplication.getApplication().getAssets().open(str);
                byte[] bArr = new byte[inputStream.available()];
                inputStream.read(bArr);
                inputStream.close();
                String str2 = new String(bArr);
                if (inputStream == null) {
                    return str2;
                }
                try {
                    inputStream.close();
                    return str2;
                } catch (IOException e) {
                    e.printStackTrace();
                    return str2;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                return "";
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static String readSDCardData(String str) {
        StringBuilder sb = new StringBuilder("");
        File file = new File(str);
        BufferedReader bufferedReader = null;
        try {
            try {
                try {
                    if (file.exists()) {
                        BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                        try {
                            new String();
                            while (true) {
                                String readLine = bufferedReader2.readLine();
                                if (readLine == null) {
                                    break;
                                }
                                sb.append(readLine);
                            }
                            bufferedReader = bufferedReader2;
                        } catch (IOException e) {
                            e = e;
                            bufferedReader = bufferedReader2;
                            e.printStackTrace();
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            return sb.toString();
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    }
                } catch (IOException e3) {
                    e = e3;
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        }
        return sb.toString();
    }

    public static void writeSDCardData(String str, String str2) {
        FileOutputStream fileOutputStream;
        Throwable th;
        File file = new File(str);
        try {
            if (file.exists()) {
                file.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (str2 != null && str2.length() != 0) {
            file.createNewFile();
            try {
                fileOutputStream = new FileOutputStream(file);
                try {
                    fileOutputStream.write(str2.getBytes());
                    fileOutputStream.flush();
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        th.printStackTrace();
                        if (fileOutputStream == null) {
                            return;
                        }
                        fileOutputStream.close();
                    } catch (Throwable th3) {
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (Exception unused) {
                            }
                        }
                        throw th3;
                    }
                }
            } catch (Throwable th4) {
                fileOutputStream = null;
                th = th4;
            }
            try {
                fileOutputStream.close();
            } catch (Exception unused2) {
            }
        }
    }

    public static String formatFileSize(long j) {
        float f = (float) j;
        float f2 = f1755GB;
        if (f >= f2) {
            return String.format("%.1f GB", Float.valueOf(f / f2));
        }
        float f3 = f1757MB;
        if (f >= f3) {
            float f4 = f / f3;
            return String.format(f4 > 100.0f ? "%.0f MB" : "%.1f MB", Float.valueOf(f4));
        }
        float f5 = f1756KB;
        if (f < f5) {
            return String.format("%d B", Long.valueOf(j));
        }
        float f6 = f / f5;
        return String.format(f6 > 100.0f ? "%.0f KB" : "%.1f KB", Float.valueOf(f6));
    }
}
