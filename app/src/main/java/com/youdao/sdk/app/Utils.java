package com.youdao.sdk.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class Utils {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean hasChineseCharacter(String str) {
        return Pattern.compile("[\\u4e00-\\u9fa5]").matcher(str).find();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int checkYoudaoDictInstalled(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.youdao.dict", 0);
            if (packageInfo != null) {
                return packageInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return -1;
    }

    public static boolean isFirstTime(String str, Context context) {
        boolean z = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(str, true);
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(str, false).commit();
        return z;
    }

    public static boolean isChinese(Context context) {
        return context.getResources().getConfiguration().locale.getLanguage().endsWith("zh");
    }
}
