package com.zzz.ipfssdk;

import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes4.dex */
public class LogUtil {
    public static final int LOG_DEBUG = 1;
    public static final int LOG_ERROR = 4;
    public static final int LOG_INFO = 2;
    public static final int LOG_OFF = 5;
    public static final int LOG_VERBOSE = 0;
    public static final int LOG_WARN = 3;
    public static final String TAG_IPFS_SDK = "IpfsSDK";
    public static final String TAG_IPFS_SDK_CZ = "IpfsSDKByCZ";

    /* renamed from: a */
    public static volatile int f5958a = 3;

    /* renamed from: b */
    public static volatile boolean f5959b = false;

    /* renamed from: c */
    public static FileOutputStream f5960c;

    /* renamed from: d */
    public static void m121d(String str, String str2) {
        if (f5958a <= 1) {
            Log.d(str, str2 != null ? str2 : "the msg is null");
            saveLog(str, str2);
        }
    }

    /* renamed from: e */
    public static void m120e(String str, String str2) {
        if (f5958a <= 4) {
            Log.e(str, str2 != null ? str2 : "the msg is null");
            saveLog(str, str2);
        }
    }

    /* renamed from: i */
    public static void m119i(String str, String str2) {
        if (f5958a <= 2) {
            Log.i(str, str2 != null ? str2 : "the msg is null");
            saveLog(str, str2);
        }
    }

    public static void saveLog(String str, String str2) {
        if (f5959b) {
            String format = String.format("%s %s:%s", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS").format(new Date()), str, str2);
            FileOutputStream fileOutputStream = f5960c;
            if (fileOutputStream == null) {
                return;
            }
            try {
                fileOutputStream.write(format.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setLogLevel(int i) {
        if (i < 0 || i > 5) {
            i = 3;
        }
        f5958a = i;
    }

    public static void setSaveFlag(boolean z) {
        f5959b = z;
        if (z) {
            FileOutputStream fileOutputStream = f5960c;
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    f5960c.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String str = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ipfs/log";
            File file = new File(str);
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                f5960c = new FileOutputStream(new File(str + "/" + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()) + ".log"));
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: v */
    public static void m118v(String str, String str2) {
        if (f5958a <= 0) {
            Log.v(str, str2 != null ? str2 : "the msg is null");
            saveLog(str, str2);
        }
    }

    /* renamed from: w */
    public static void m117w(String str, String str2) {
        if (f5958a <= 3) {
            Log.w(str, str2 != null ? str2 : "the msg is null");
            saveLog(str, str2);
        }
    }
}
