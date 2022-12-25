package com.one.tomato.mvp.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.one.tomato.thirdpart.domain.DomainRequest;
import com.one.tomato.thirdpart.ipfs.IpfsUtil;
import com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils;
import com.one.tomato.thirdpart.webapp.WebAppUtil;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.CrashHandler;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FileUtil;
import com.one.tomato.utils.LanguageSwithUtils;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.foreground.ForegroundCallbacks;
import com.p089pm.liquidlink.LiquidLink;
import com.security.sdk.open.Security;
import java.util.Locale;
import org.litepal.LitePal;
import org.xutils.C5540x;

/* loaded from: classes3.dex */
public class BaseApplication extends Application {
    public static Gson gson;
    public static BaseApplication instance;
    private static int isMyMessageHave;
    private String apkChannelId;
    private String apkSortId;
    private String channelId;
    private String channelSubId;
    private boolean videoPlayWifiTip = true;
    private Application.ActivityLifecycleCallbacks callbacks = new Application.ActivityLifecycleCallbacks(this) { // from class: com.one.tomato.mvp.base.BaseApplication.1
        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
            String string = PreferencesUtil.getInstance().getString("language_lan");
            String string2 = PreferencesUtil.getInstance().getString("language_country");
            if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2) || LanguageSwithUtils.INSTANCE.isSameWithSetting(activity)) {
                return;
            }
            LanguageSwithUtils.INSTANCE.changeAppLanguage(activity, new Locale(string, string2), false);
        }
    };

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        instance = (BaseApplication) getApplicationContext();
        registerActivityLifecycleCallbacks(this.callbacks);
        init();
    }

    private void init() {
        gson = new Gson();
        LogUtil.init(false);
        C5540x.Ext.setDebug(false);
        clearDomainCacheOver337();
        CrashHandler.getInstance().init(instance);
        DomainRequest.getInstance();
        LitePal.initialize(instance);
        C5540x.Ext.init(instance);
        initAppChannel();
        ForegroundCallbacks.init(this);
        TomatoLiveSDKUtils.getSingleton().initSDK(instance);
        Security.init(instance);
        if (TextUtils.isEmpty(PreferencesUtil.getInstance().getString("language_country"))) {
            PreferencesUtil.getInstance().putString("language_country", "TW");
            PreferencesUtil.getInstance().putString("language_lan", "zh");
        }
        if (isMainProcess() && isLiquidLink()) {
            LiquidLink.init(this);
        }
        WebAppUtil.init(this);
        IpfsUtil.init();
    }

    public static BaseApplication getApplication() {
        return instance;
    }

    public static Gson getGson() {
        return gson;
    }

    public boolean isVideoPlayWifiTip() {
        return this.videoPlayWifiTip;
    }

    public void setVideoPlayWifiTip(boolean z) {
        this.videoPlayWifiTip = z;
    }

    public static int isMyMessageHave() {
        return isMyMessageHave;
    }

    public static void setIsMyMessageHave(int i) {
        isMyMessageHave = i;
    }

    public boolean isMainProcess() {
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) getSystemService("activity")).getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == myPid) {
                return getApplicationInfo().packageName.equals(runningAppProcessInfo.processName);
            }
        }
        return false;
    }

    private void initAppChannel() {
        try {
            String[] split = getPackageManager().getApplicationInfo(getPackageName(), 128).metaData.getString("CHANNEL_KEY").split("_");
            this.channelId = split[0];
            this.apkSortId = split[1];
            this.apkChannelId = this.channelId;
            LogUtil.m3784i("启动的时候读取渠道，channelId ：" + this.channelId + "，apkSortId：" + this.apkSortId);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getApkChannelId() {
        if (TextUtils.isEmpty(this.apkChannelId)) {
            this.apkChannelId = "";
        }
        return this.apkChannelId;
    }

    public String getChannelId() {
        if (TextUtils.isEmpty(this.channelId)) {
            this.channelId = DBUtil.getInitAppInfo().getChannelId();
        }
        if (TextUtils.isEmpty(this.channelId)) {
            initAppChannel();
        }
        return this.channelId;
    }

    public String getChannelSubId() {
        return this.channelSubId;
    }

    public void setChannelInfo(String str, String str2) {
        this.channelId = str;
        this.channelSubId = str2;
        LogUtil.m3784i("BaseApplication重新设置渠道信息，channelId ：" + str + "，channelSubId：" + str2);
    }

    public String getApkSortId() {
        if (TextUtils.isEmpty(this.apkSortId)) {
            initAppChannel();
        }
        return this.apkSortId;
    }

    public boolean isLiquidLink() {
        return "A005".equals(getApkSortId()) || "A009".equals(getApkSortId()) || "A016".equals(getApkSortId());
    }

    public boolean isChess() {
        return "A016".equals(getApkSortId());
    }

    @Override // android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(LanguageSwithUtils.INSTANCE.attachBaseContext(context));
    }

    private void clearDomainCacheOver337() {
        if (AppUtil.getVersionCode() < 337 || PreferencesUtil.getInstance().getBoolean("domain_337")) {
            return;
        }
        LogUtil.m3784i("337版本以上的重新把asset文件到sd中");
        FileUtil.deleteFolderFile(FileUtil.getDomainDir("line").getPath());
        PreferencesUtil.getInstance().putBoolean("domain_337", true);
    }
}
