package com.youdao.sdk.common;

import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/* loaded from: classes4.dex */
public class YouDaoLog {
    private static final Logger LOGGER = Logger.getLogger("com.youdao");
    private static final C5178a LOG_HANDLER = new C5178a();

    static {
        LogManager.getLogManager().addLogger(LOGGER);
        LOGGER.addHandler(LOG_HANDLER);
        LOGGER.setLevel(Level.FINE);
    }

    /* renamed from: d */
    public static void m169d(String str) {
        m168d(str, null);
    }

    /* renamed from: w */
    public static void m165w(String str) {
        m164w(str, null);
    }

    /* renamed from: e */
    public static void m167e(String str) {
        m166e(str, null);
    }

    /* renamed from: d */
    public static void m168d(String str, Throwable th) {
        LOGGER.log(Level.CONFIG, str, th);
    }

    /* renamed from: w */
    public static void m164w(String str, Throwable th) {
        LOGGER.log(Level.WARNING, str, th);
    }

    /* renamed from: e */
    public static void m166e(String str, Throwable th) {
        LOGGER.log(Level.SEVERE, str, th);
    }

    /* renamed from: com.youdao.sdk.common.YouDaoLog$a */
    /* loaded from: classes4.dex */
    static final class C5178a extends Handler {

        /* renamed from: a */
        private static final Map<Level, Integer> f5933a = new HashMap(7);

        @Override // java.util.logging.Handler
        public void close() {
        }

        @Override // java.util.logging.Handler
        public void flush() {
        }

        private C5178a() {
        }

        static {
            f5933a.put(Level.FINEST, 2);
            f5933a.put(Level.FINER, 2);
            f5933a.put(Level.FINE, 2);
            f5933a.put(Level.CONFIG, 3);
            f5933a.put(Level.INFO, 4);
            f5933a.put(Level.WARNING, 5);
            f5933a.put(Level.SEVERE, 6);
        }

        @Override // java.util.logging.Handler
        public void publish(LogRecord logRecord) {
            Throwable thrown;
            if (isLoggable(logRecord)) {
                int intValue = f5933a.containsKey(logRecord.getLevel()) ? f5933a.get(logRecord.getLevel()).intValue() : 2;
                String str = logRecord.getMessage() + "\n";
                if (logRecord.getThrown() != null) {
                    str = str + Log.getStackTraceString(thrown);
                }
                Log.println(intValue, "YouDao", str);
            }
        }
    }
}
