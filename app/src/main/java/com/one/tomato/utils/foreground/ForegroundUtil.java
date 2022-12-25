package com.one.tomato.utils.foreground;

import android.app.Activity;
import android.text.TextUtils;
import com.one.tomato.constants.Constants;
import com.one.tomato.entity.LockScreenRecodeInfo;
import com.one.tomato.p085ui.lockscreen.LockScreenPwdEnterActivity;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DataUploadUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.UserInfoManager;
import com.one.tomato.utils.foreground.ForegroundCallbacks;
import com.tomatolive.library.utils.DateUtils;

/* loaded from: classes3.dex */
public class ForegroundUtil {
    private static ForegroundUtil foregroundUtil;
    private long appResumeTime = System.currentTimeMillis();
    private LockScreenRecodeInfo lockScreenRecodeInfo = new LockScreenRecodeInfo();

    private ForegroundUtil() {
    }

    public static ForegroundUtil getInstance() {
        if (foregroundUtil == null) {
            synchronized (ForegroundUtil.class) {
                if (foregroundUtil == null) {
                    foregroundUtil = new ForegroundUtil();
                }
            }
        }
        return foregroundUtil;
    }

    public void addListener() {
        ForegroundCallbacks.get().addListener(new ForegroundCallbacks.Listener() { // from class: com.one.tomato.utils.foreground.ForegroundUtil.1
            @Override // com.one.tomato.utils.foreground.ForegroundCallbacks.Listener
            public void onBecameForeground(Activity activity) {
                LogUtil.m3784i("当前程序切换到前台");
                ForegroundUtil.this.appResumeTime = System.currentTimeMillis();
                ForegroundUtil foregroundUtil2 = ForegroundUtil.this;
                foregroundUtil2.lockScreen(foregroundUtil2.lockScreenRecodeInfo);
            }

            @Override // com.one.tomato.utils.foreground.ForegroundCallbacks.Listener
            public void onBecameBackground(Activity activity) {
                LogUtil.m3784i("当前程序切换到后台");
                DataUploadUtil.uploadUseTime();
                ForegroundUtil.this.recodeLockScreen(activity);
                UserInfoManager.requestUserInfo(false);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recodeLockScreen(Activity activity) {
        if (!TextUtils.isEmpty(DBUtil.getLockScreenInfo().getLockScreenPwd()) && activity != null) {
            int i = 0;
            while (true) {
                Class[] clsArr = Constants.FILTER_LOCK_SCREEN_ACTIVITY;
                if (i < clsArr.length) {
                    if (clsArr[i].isInstance(activity)) {
                        this.lockScreenRecodeInfo = null;
                        return;
                    }
                    i++;
                } else {
                    if (this.lockScreenRecodeInfo == null) {
                        this.lockScreenRecodeInfo = new LockScreenRecodeInfo();
                    }
                    this.lockScreenRecodeInfo.setCurrentActivity(activity);
                    this.lockScreenRecodeInfo.setBackgroundTime(System.currentTimeMillis());
                    this.lockScreenRecodeInfo.setNeedLock(true);
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lockScreen(LockScreenRecodeInfo lockScreenRecodeInfo) {
        if (TextUtils.isEmpty(DBUtil.getLockScreenInfo().getLockScreenPwd())) {
            return;
        }
        Activity currentActivity = lockScreenRecodeInfo.getCurrentActivity();
        int i = 0;
        if (currentActivity != null) {
            currentActivity.getWindow().getDecorView().setDrawingCacheEnabled(false);
            currentActivity.getWindow().getDecorView().destroyDrawingCache();
        }
        if (!lockScreenRecodeInfo.isNeedLock() || currentActivity == null) {
            return;
        }
        while (true) {
            Class[] clsArr = Constants.FILTER_LOCK_SCREEN_ACTIVITY;
            if (i < clsArr.length) {
                if (clsArr[i].isInstance(currentActivity)) {
                    return;
                }
                i++;
            } else {
                try {
                    lockScreenRecodeInfo.setForegroundTime(System.currentTimeMillis());
                    if (DateUtils.ONE_MINUTE_MILLIONS >= lockScreenRecodeInfo.getForegroundTime() - lockScreenRecodeInfo.getBackgroundTime()) {
                        lockScreenRecodeInfo.setBackgroundTime(0L);
                        lockScreenRecodeInfo.setForegroundTime(0L);
                    } else if (currentActivity != null) {
                        LockScreenPwdEnterActivity.startActivity(currentActivity);
                    } else {
                        lockScreenRecodeInfo.setBackgroundTime(0L);
                        lockScreenRecodeInfo.setForegroundTime(0L);
                    }
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    lockScreenRecodeInfo.setBackgroundTime(0L);
                    lockScreenRecodeInfo.setForegroundTime(0L);
                    return;
                }
            }
        }
    }

    public long getAppResumeTime() {
        return this.appResumeTime;
    }
}
