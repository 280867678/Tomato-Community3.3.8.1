package com.amazonaws.logging;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class LogFactory {
    private static final String TAG = "LogFactory";
    private static Map<String, Log> logMap = new HashMap();

    public static synchronized Log getLog(Class cls) {
        Log log;
        synchronized (LogFactory.class) {
            log = getLog(cls.getSimpleName());
        }
        return log;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0030 A[Catch: all -> 0x003c, TRY_LEAVE, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x000d, B:12:0x0013, B:14:0x0018, B:18:0x0025, B:9:0x0030), top: B:3:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static synchronized Log getLog(String str) {
        Log log;
        Log log2;
        Exception e;
        synchronized (LogFactory.class) {
            log = logMap.get(str);
            if (log == null) {
                if (checkApacheCommonsLoggingExists()) {
                    try {
                        log2 = new ApacheCommonsLogging(str);
                        try {
                            logMap.put(str, log2);
                        } catch (Exception e2) {
                            e = e2;
                            android.util.Log.w(TAG, "Could not create log from org.apache.commons.logging.LogFactory", e);
                            log = log2;
                            if (log == null) {
                            }
                            return log;
                        }
                    } catch (Exception e3) {
                        log2 = log;
                        e = e3;
                    }
                    log = log2;
                }
                if (log == null) {
                    log = new AndroidLog(str);
                    logMap.put(str, log);
                }
            }
        }
        return log;
    }

    private static boolean checkApacheCommonsLoggingExists() {
        try {
            Class.forName("org.apache.commons.logging.LogFactory");
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        } catch (Exception e) {
            android.util.Log.e(TAG, e.getMessage());
            return false;
        }
    }
}
