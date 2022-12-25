package com.youdao.sdk.app;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public class YouDaoApplication {
    private static YouDaoApplication application = null;
    private static boolean isForeignVersion = false;
    public static String mAppKey;
    private static Context mContext;

    public static boolean isForeignVersion() {
        return isForeignVersion;
    }

    static {
        System.loadLibrary("dict-parser");
    }

    private YouDaoApplication(Context context, String str) {
        mContext = context.getApplicationContext();
        mAppKey = str;
        if (!LanguageUtils.USER_PROFILE.equals("oppo")) {
            init();
        }
    }

    private void init() {
        Stats.init(mContext);
        Stats.checkAndUpload();
        Auth.valid(mContext);
    }

    public static void init(Context context, String str) {
        if (application == null || mContext == null || TextUtils.isEmpty(str)) {
            application = new YouDaoApplication(context, str);
        }
    }

    public static Context getApplicationContext() {
        return mContext;
    }
}
