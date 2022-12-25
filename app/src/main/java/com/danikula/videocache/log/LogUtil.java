package com.danikula.videocache.log;

import android.os.Environment;
import android.util.Log;
import com.tomatolive.library.utils.DateUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes2.dex */
public class LogUtil {
    private static Boolean MYLOG_SWITCH = false;
    public static Boolean MYLOG_WRITE_TO_FILE = true;
    private static char MYLOG_TYPE = 'i';
    private static String MYLOG_PATH_SDCARD_DIR = "MH-VTM";
    private static String MYLOGFILEName = "anzer_explain_log.txt";
    private static SimpleDateFormat myLogSdf = new SimpleDateFormat(DateUtils.C_TIME_PATTON_DEFAULT);
    private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");

    /* renamed from: i */
    public static void m4184i(String str, String str2) {
        log(str, str2, 'i');
    }

    private static void log(String str, String str2, char c) {
        char c2;
        char c3;
        char c4;
        char c5;
        if (MYLOG_SWITCH.booleanValue()) {
            if ('e' == c && ('e' == (c5 = MYLOG_TYPE) || 'v' == c5)) {
                Log.e(str, str2);
            } else if ('w' == c && ('w' == (c4 = MYLOG_TYPE) || 'v' == c4)) {
                Log.w(str, str2);
            } else if ('d' == c && ('d' == (c3 = MYLOG_TYPE) || 'v' == c3)) {
                Log.d(str, str2);
            } else if ('i' == c && ('d' == (c2 = MYLOG_TYPE) || 'v' == c2)) {
                Log.i(str, str2);
            } else {
                Log.v(str, str2);
            }
            if (!MYLOG_WRITE_TO_FILE.booleanValue()) {
                return;
            }
            writeLogtoFile(String.valueOf(c), str, str2);
        }
    }

    private static String getLogPath() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            return externalStorageDirectory.getAbsolutePath() + File.separator + MYLOG_PATH_SDCARD_DIR;
        }
        return "";
    }

    private static void writeLogtoFile(String str, String str2, String str3) {
        Date date;
        String format = logfile.format(new Date());
        String str4 = myLogSdf.format(date) + "    " + str + "    " + str2 + "    " + str3;
        String logPath = getLogPath();
        if (logPath == null || "".equals(logPath)) {
            return;
        }
        try {
            File file = new File(logPath);
            if (!file.exists()) {
                file.mkdir();
            }
            FileWriter fileWriter = new FileWriter(new File(logPath + File.separator + format + MYLOGFILEName), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(str4);
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
