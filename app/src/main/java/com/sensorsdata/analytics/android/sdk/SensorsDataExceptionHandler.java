package com.sensorsdata.analytics.android.sdk;

import android.os.Process;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.data.DbAdapter;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataTimer;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class SensorsDataExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final int SLEEP_TIMEOUT_MS = 3000;
    private static boolean isTrackCrash;
    private static SensorsDataExceptionHandler sInstance;
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

    private SensorsDataExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static synchronized void init() {
        synchronized (SensorsDataExceptionHandler.class) {
            if (sInstance == null) {
                sInstance = new SensorsDataExceptionHandler();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void enableAppCrash() {
        isTrackCrash = true;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, final Throwable th) {
        try {
            SensorsDataAPI.allInstances(new SensorsDataAPI.InstanceProcessor() { // from class: com.sensorsdata.analytics.android.sdk.SensorsDataExceptionHandler.1
                @Override // com.sensorsdata.analytics.android.sdk.SensorsDataAPI.InstanceProcessor
                public void process(SensorsDataAPI sensorsDataAPI) {
                    if (SensorsDataExceptionHandler.isTrackCrash) {
                        try {
                            JSONObject jSONObject = new JSONObject();
                            try {
                                StringWriter stringWriter = new StringWriter();
                                PrintWriter printWriter = new PrintWriter(stringWriter);
                                th.printStackTrace(printWriter);
                                for (Throwable cause = th.getCause(); cause != null; cause = cause.getCause()) {
                                    cause.printStackTrace(printWriter);
                                }
                                printWriter.close();
                                jSONObject.put(AopConstants.APP_CRASHED_REASON, stringWriter.toString());
                            } catch (Exception e) {
                                SALog.printStackTrace(e);
                            }
                            SALog.m3675i("异常采集 ===> messageProp = " + jSONObject);
                            sensorsDataAPI.track(AopConstants.APP_CRASH_EVENT_NAME, jSONObject);
                        } catch (Exception e2) {
                            SALog.printStackTrace(e2);
                        }
                    }
                    SensorsDataTimer.getInstance().shutdownTimerTask();
                    DbAdapter.getInstance().commitAppEndTime(System.currentTimeMillis());
                    DbAdapter.getInstance().commitActivityCount(0);
                    sensorsDataAPI.flushSync();
                }
            });
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                SALog.printStackTrace(e);
            }
            if (this.mDefaultExceptionHandler != null) {
                this.mDefaultExceptionHandler.uncaughtException(thread, th);
            } else {
                killProcessAndExit();
            }
        } catch (Exception unused) {
        }
    }

    private void killProcessAndExit() {
        try {
            Process.killProcess(Process.myPid());
            System.exit(10);
        } catch (Exception unused) {
        }
    }
}
