package com.blankj.utilcode.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import java.util.Locale;

/* loaded from: classes2.dex */
public class LanguageUtils {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static void applyLanguage(@NonNull Activity activity) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
        }
        String string = SPUtils.getInstance().getString("KEY_LOCALE");
        if (TextUtils.isEmpty(string)) {
            Locale locale = Resources.getSystem().getConfiguration().locale;
            updateLanguage(Utils.getApp(), locale);
            updateLanguage(activity, locale);
            return;
        }
        String[] split = string.split("\\$");
        if (split.length != 2) {
            Log.e("LanguageUtils", "The string of " + string + " is not in the correct format.");
            return;
        }
        Locale locale2 = new Locale(split[0], split[1]);
        updateLanguage(Utils.getApp(), locale2);
        updateLanguage(activity, locale2);
    }

    private static void updateLanguage(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale2 = configuration.locale;
        if (!equals(locale2.getLanguage(), locale.getLanguage()) || !equals(locale2.getCountry(), locale.getCountry())) {
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            if (Build.VERSION.SDK_INT >= 17) {
                configuration.setLocale(locale);
                context.createConfigurationContext(configuration);
            } else {
                configuration.locale = locale;
            }
            resources.updateConfiguration(configuration, displayMetrics);
        }
    }

    private static boolean equals(CharSequence charSequence, CharSequence charSequence2) {
        int length;
        if (charSequence == charSequence2) {
            return true;
        }
        if (charSequence == null || charSequence2 == null || (length = charSequence.length()) != charSequence2.length()) {
            return false;
        }
        if ((charSequence instanceof String) && (charSequence2 instanceof String)) {
            return charSequence.equals(charSequence2);
        }
        for (int i = 0; i < length; i++) {
            if (charSequence.charAt(i) != charSequence2.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
