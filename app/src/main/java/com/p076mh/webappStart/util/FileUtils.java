package com.p076mh.webappStart.util;

import android.os.Environment;
import com.gen.p059mh.webapps.utils.Logger;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

/* renamed from: com.mh.webappStart.util.FileUtils */
/* loaded from: classes3.dex */
public class FileUtils {
    public static File createDir(String str) throws IOException {
        File file = new File(str);
        if (Environment.getExternalStorageState().equals("mounted")) {
            PrintStream printStream = System.out;
            printStream.println("createDir:" + file.getAbsolutePath());
        }
        if (!file.exists() && !file.mkdirs()) {
            Logger.m4112i("FileUtils", "创建文件夹" + file.getAbsolutePath() + "失败");
        }
        return file;
    }

    public static File createFile(String str) throws IOException {
        File file = new File(str);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            createDir(parentFile.getAbsolutePath());
        }
        if (Environment.getExternalStorageState().equals("mounted")) {
            PrintStream printStream = System.out;
            printStream.println("createFile:" + file.getAbsolutePath());
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }
}
