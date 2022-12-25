package com.youdao.sdk.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public class WordHelper {
    public static boolean supportWordDeepLink(Context context) {
        return Utils.checkYoudaoDictInstalled(context) >= 6060100;
    }

    public static void openMore(Context context, String str, String str2, Language language, Language language2) {
        if (!TextUtils.isEmpty(str)) {
            if (supportWordDeepLink(context)) {
                openDict(context, str, str2, language, language2);
            } else {
                openBrowser(context, getWordDetailUrl(str, str2, language, language2));
            }
        }
    }

    public static void openMore_Foreigner(Context context, String str, String str2, String str3, String str4, String str5) {
        if (!TextUtils.isEmpty(str)) {
            if (!TextUtils.isEmpty(str2)) {
                openBrowser(context, str2);
            } else if (Utils.checkYoudaoDictInstalled(context) >= 6060100) {
                openDict(context, str, str5, LanguageUtils.getLangByCode(str3), LanguageUtils.getLangByCode(str4));
            } else {
                String format = String.format("http://www.u-dictionary.com/home/word/%s/from/" + str3 + "/to/" + str4, str);
                Stats.doOtherStatistics("querysdk_jump_to_dict", str, "web", LanguageUtils.getLangByCode(str3), LanguageUtils.getLangByCode(str4));
                openBrowser(context, format);
            }
        }
    }

    public static boolean openDeepLink(Context context, String str, String str2, Language language, Language language2) {
        if (TextUtils.isEmpty(str) || !supportWordDeepLink(context)) {
            return false;
        }
        openDict(context, str, str2, language, language2);
        return true;
    }

    public static String getWordDetailUrl(String str, String str2, Language language, Language language2) {
        Stats.doOtherStatistics("querysdk_jump_to_dict", str, "web", language, language2);
        return String.format("http://m.youdao.com/dict?le=%s&q=%s", str2, str);
    }

    private static void openBrowser(Context context, String str) {
        if (!TextUtils.isEmpty(str)) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(str));
            context.startActivity(intent);
        }
    }

    private static void openDict(Context context, String str, String str2, Language language, Language language2) {
        if (supportWordDeepLink(context)) {
            Stats.doOtherStatistics("querysdk_jump_to_dict", str, "app", language, language2);
            String format = String.format("yddict://m.youdao.com/dict?q=%s&le=%s", str, str2);
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(format));
            context.startActivity(intent);
        }
    }

    private static void openDictWithDeeplink(Context context, String str, String str2, Language language, Language language2, String str3) {
        if (supportWordDeepLink(context)) {
            Stats.doOtherStatistics("querysdk_jump_to_dict", str, "app", language, language2);
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(str3));
            context.startActivity(intent);
        }
    }

    public static void openMore(Context context, String str, String str2, Language language, Language language2, String str3, String str4) {
        if (!TextUtils.isEmpty(str)) {
            if (supportWordDeepLink(context)) {
                openDictWithDeeplink(context, str, str2, language, language2, str4);
            } else {
                openBrowser(context, str3);
            }
        }
    }
}
