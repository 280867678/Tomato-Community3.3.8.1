package com.one.tomato.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import java.util.Locale;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LanguageSwithUtils.kt */
/* loaded from: classes3.dex */
public final class LanguageSwithUtils {
    public static final LanguageSwithUtils INSTANCE = new LanguageSwithUtils();

    private LanguageSwithUtils() {
    }

    public final void changeAppLanguage(Context context, Locale locale, boolean z) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(locale, "locale");
        Resources resources = context.getResources();
        Intrinsics.checkExpressionValueIsNotNull(resources, "resources");
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        int i = Build.VERSION.SDK_INT;
        if (i >= 24) {
            configuration.setLocale(locale);
            Intrinsics.checkExpressionValueIsNotNull(configuration, "configuration");
            configuration.setLocales(new LocaleList(locale));
            context.createConfigurationContext(configuration);
        } else if (i >= 17) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, displayMetrics);
        if (z) {
            saveLanguageSetting(locale);
        }
    }

    @RequiresApi(24)
    private final Context createConfigurationResources(Context context) {
        Resources resources;
        if (context == null || (resources = context.getResources()) == null) {
            return context;
        }
        Configuration configuration = resources.getConfiguration();
        Locale appLocale = getAppLocale(context);
        String spLanguage = PreferencesUtil.getInstance(context).getString("language_lan");
        String spCountry = PreferencesUtil.getInstance(context).getString("language_country");
        if (!TextUtils.isEmpty(spLanguage) && !TextUtils.isEmpty(spCountry)) {
            Intrinsics.checkExpressionValueIsNotNull(spLanguage, "spLanguage");
            Intrinsics.checkExpressionValueIsNotNull(spCountry, "spCountry");
            if (!isSameLocal(appLocale, spLanguage, spCountry)) {
                appLocale = new Locale(spLanguage, spCountry);
            }
        }
        configuration.setLocale(appLocale);
        Intrinsics.checkExpressionValueIsNotNull(configuration, "configuration");
        configuration.setLocales(new LocaleList(appLocale));
        Context createConfigurationContext = context.createConfigurationContext(configuration);
        Intrinsics.checkExpressionValueIsNotNull(createConfigurationContext, "context.createConfigurationContext(configuration)");
        return createConfigurationContext;
    }

    public final void setConfiguration(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Locale appLocale = getAppLocale(context);
        String spLanguage = PreferencesUtil.getInstance(context).getString("language_lan");
        String spCountry = PreferencesUtil.getInstance(context).getString("language_country");
        if (!TextUtils.isEmpty(spLanguage) && !TextUtils.isEmpty(spCountry)) {
            Intrinsics.checkExpressionValueIsNotNull(spLanguage, "spLanguage");
            Intrinsics.checkExpressionValueIsNotNull(spCountry, "spCountry");
            if (!isSameLocal(appLocale, spLanguage, spCountry)) {
                appLocale = new Locale(spLanguage, spCountry);
            }
        }
        Resources resources = context.getResources();
        Intrinsics.checkExpressionValueIsNotNull(resources, "context.resources");
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= 17) {
            configuration.setLocale(appLocale);
        } else {
            configuration.locale = appLocale;
        }
        Resources resources2 = context.getResources();
        Intrinsics.checkExpressionValueIsNotNull(resources2, "resources");
        resources2.updateConfiguration(configuration, resources2.getDisplayMetrics());
    }

    public final Context attachBaseContext(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        if (Build.VERSION.SDK_INT >= 24) {
            return createConfigurationResources(context);
        }
        setConfiguration(context);
        return context;
    }

    public final boolean isSameWithSetting(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Locale appLocale = getAppLocale(context);
        String str = null;
        String language = appLocale != null ? appLocale.getLanguage() : null;
        if (appLocale != null) {
            str = appLocale.getCountry();
        }
        return Intrinsics.areEqual(language, PreferencesUtil.getInstance(context).getString("language_lan")) && Intrinsics.areEqual(str, PreferencesUtil.getInstance(context).getString("language_country"));
    }

    public final boolean isSameLocal(Locale locale, String sp_language, String sp_country) {
        Intrinsics.checkParameterIsNotNull(sp_language, "sp_language");
        Intrinsics.checkParameterIsNotNull(sp_country, "sp_country");
        String str = null;
        String language = locale != null ? locale.getLanguage() : null;
        if (locale != null) {
            str = locale.getCountry();
        }
        return Intrinsics.areEqual(language, sp_language) && Intrinsics.areEqual(str, sp_country);
    }

    public final Locale getAppLocale(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Resources resources = context.getResources();
        if (resources != null) {
            Configuration configuration = resources.getConfiguration();
            if (Build.VERSION.SDK_INT >= 24) {
                Intrinsics.checkExpressionValueIsNotNull(configuration, "configuration");
                return configuration.getLocales().get(0);
            }
            return configuration.locale;
        }
        return null;
    }

    public final void saveLanguageSetting(Locale locale) {
        Intrinsics.checkParameterIsNotNull(locale, "locale");
        PreferencesUtil.getInstance().putString("language_lan", locale.getLanguage());
        PreferencesUtil.getInstance().putString("language_country", locale.getCountry());
    }
}
