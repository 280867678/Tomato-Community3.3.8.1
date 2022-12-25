package com.one.tomato.utils;

import android.content.Context;
import com.broccoli.p150bh.R;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.one.tomato.mvp.base.AppManager;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.net.ApiImplService;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.utils.DateUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler mInstance;
    private Map<String, String> info = new LinkedHashMap();
    private long lastTime = 0;

    public static CrashHandler getInstance() {
        if (mInstance == null) {
            mInstance = new CrashHandler();
        }
        return mInstance;
    }

    public void init(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        handleException(th);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AppManager.getAppManager().exitApp();
    }

    private void handleException(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        while (th != null) {
            th.printStackTrace(printWriter);
            th = th.getCause();
        }
        printWriter.close();
        handleException(stringWriter.toString(), 0);
    }

    public void handleException(String str, int i) {
        long currentTimeMillis = System.currentTimeMillis();
        String str2 = "crash_" + FormatUtil.formatTime(DateUtils.C_TIME_PATTON_DEFAULT, new Date(currentTimeMillis));
        Map<String, String> map = this.info;
        if (map == null) {
            this.info = new LinkedHashMap();
        } else {
            map.clear();
        }
        this.info.put("app", AppUtil.getString(R.string.app_name));
        this.info.put(AopConstants.TIME_KEY, str2);
        this.info.put("error", str);
        this.info.put("memberId", DBUtil.getMemberId() + "");
        this.info.put("versionNo", AppUtil.getVersionCodeStr());
        this.info.put("channelId", BaseApplication.getApplication().getChannelId());
        this.info.put("subChannelId", BaseApplication.getApplication().getChannelSubId());
        this.info.put("apkChannelId", BaseApplication.getApplication().getApkChannelId());
        this.info.put("deviceNo", DeviceInfoUtil.getUniqueDeviceID());
        this.info.put("brand", DeviceInfoUtil.getDeviceBrand());
        this.info.put("model", DeviceInfoUtil.getDeviceTypeName());
        this.info.put("osVersion", DeviceInfoUtil.getPhoneOSVersion());
        this.info.put("netStatus", NetWorkUtil.getNetWorkType());
        this.info.put("phoneResolution", DisplayMetricsUtils.getResolution());
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : this.info.entrySet()) {
            sb.append(entry.getKey());
            sb.append(SimpleComparison.EQUAL_TO_OPERATION);
            sb.append(entry.getValue());
            sb.append("\r\n");
        }
        if (currentTimeMillis - this.lastTime > i * 60 * 1000) {
            this.lastTime = currentTimeMillis;
            sendExceptionToServer(sb.toString());
        }
        saveExceptionToSD(str2, sb.toString());
    }

    private void sendExceptionToServer(String str) {
        ApiImplService.Companion.getApiImplService().reportCrashInfo(str).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<Object>(this) { // from class: com.one.tomato.utils.CrashHandler.1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                LogUtil.m3784i("奔溃信息上报成功");
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                LogUtil.m3786e("奔溃信息上报失败");
            }
        });
    }

    private void saveExceptionToSD(String str, String str2) {
        FileOutputStream fileOutputStream;
        Throwable th;
        File file = new File(FileUtil.getCrashCacheDir().getPath() + File.separator + (str + ".txt"));
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
