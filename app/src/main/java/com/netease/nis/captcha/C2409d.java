package com.netease.nis.captcha;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.netease.nis.captcha.CaptchaConfiguration;
import com.tomatolive.library.utils.TranslationUtils;
import java.util.Locale;

/* renamed from: com.netease.nis.captcha.d */
/* loaded from: classes3.dex */
public class C2409d {

    /* renamed from: a */
    private static boolean f1649a;

    /* renamed from: com.netease.nis.captcha.d$1 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class C24101 {

        /* renamed from: a */
        static final /* synthetic */ int[] f1650a = new int[CaptchaConfiguration.LangType.values().length];

        static {
            try {
                f1650a[CaptchaConfiguration.LangType.LANG_ZH_TW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1650a[CaptchaConfiguration.LangType.LANG_EN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f1650a[CaptchaConfiguration.LangType.LANG_JA.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f1650a[CaptchaConfiguration.LangType.LANG_KO.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f1650a[CaptchaConfiguration.LangType.LANG_TH.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f1650a[CaptchaConfiguration.LangType.LANG_VI.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f1650a[CaptchaConfiguration.LangType.LANG_FR.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f1650a[CaptchaConfiguration.LangType.LANG_RU.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f1650a[CaptchaConfiguration.LangType.LANG_AR.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f1650a[CaptchaConfiguration.LangType.LANG_DE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f1650a[CaptchaConfiguration.LangType.LANG_IT.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f1650a[CaptchaConfiguration.LangType.LANG_HE.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f1650a[CaptchaConfiguration.LangType.LANG_HI.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f1650a[CaptchaConfiguration.LangType.LANG_ID.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f1650a[CaptchaConfiguration.LangType.LANG_MY.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f1650a[CaptchaConfiguration.LangType.LANG_LO.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f1650a[CaptchaConfiguration.LangType.LANG_MS.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                f1650a[CaptchaConfiguration.LangType.LANG_PL.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                f1650a[CaptchaConfiguration.LangType.LANG_PT.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                f1650a[CaptchaConfiguration.LangType.LANG_ES.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                f1650a[CaptchaConfiguration.LangType.LANG_TR.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
        }
    }

    /* renamed from: a */
    public static String m3801a(CaptchaConfiguration.LangType langType) {
        switch (C24101.f1650a[langType.ordinal()]) {
            case 1:
                return TranslationUtils.CH_TW;
            case 2:
                return "en";
            case 3:
                return "ja";
            case 4:
                return "ko";
            case 5:
                return "th";
            case 6:
                return "vi";
            case 7:
                return "fr";
            case 8:
                return "ru";
            case 9:
                return "ar";
            case 10:
                return "de";
            case 11:
                return "it";
            case 12:
                return "he";
            case 13:
                return "hi";
            case 14:
                return DatabaseFieldConfigLoader.FIELD_NAME_ID;
            case 15:
                return "my";
            case 16:
                return "lo";
            case 17:
                return "ms";
            case 18:
                return "pl";
            case 19:
                return "pt";
            case 20:
                return "es";
            case 21:
                return "tr";
            default:
                return "";
        }
    }

    /* renamed from: a */
    public static void m3803a(Context context, CaptchaConfiguration.LangType langType) {
        Locale locale;
        switch (C24101.f1650a[langType.ordinal()]) {
            case 1:
                locale = Locale.TAIWAN;
                break;
            case 2:
                locale = Locale.US;
                break;
            case 3:
                locale = Locale.JAPAN;
                break;
            case 4:
                locale = Locale.KOREA;
                break;
            case 5:
                locale = new Locale("th", "TH");
                break;
            case 6:
                locale = new Locale("vi", "VN");
                break;
            case 7:
                locale = Locale.FRANCE;
                break;
            case 8:
                locale = new Locale("ru", "RU");
                break;
            case 9:
                locale = new Locale("ar", "SA");
                break;
            case 10:
                locale = new Locale("de", "DE");
                break;
            case 11:
                locale = new Locale("it", "IT");
                break;
            case 12:
                locale = new Locale("iw", "IL");
                break;
            case 13:
                locale = new Locale("hi", "IN");
                break;
            case 14:
                locale = new Locale("in", "ID");
                break;
            case 15:
                locale = new Locale("my", "MM");
                break;
            case 16:
                locale = new Locale("lo", "LA");
                break;
            case 17:
                locale = new Locale("ms", "MY");
                break;
            case 18:
                locale = new Locale("pl", "PL");
                break;
            case 19:
                locale = new Locale("pt", "PT");
                break;
            case 20:
                locale = new Locale("es", "ES");
                break;
            case 21:
                locale = new Locale("tr", "TR");
                break;
            default:
                locale = Locale.SIMPLIFIED_CHINESE;
                break;
        }
        m3802a(context, locale);
    }

    /* renamed from: a */
    private static void m3802a(Context context, Locale locale) {
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        int i = Build.VERSION.SDK_INT;
        if (i >= 24) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            configuration.setLocales(localeList);
            context.createConfigurationContext(configuration);
        } else if (i >= 19) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, displayMetrics);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public static void m3800a(String str, Object... objArr) {
        if (f1649a) {
            Log.d(Captcha.TAG, String.format(str, objArr));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public static void m3799a(boolean z) {
        f1649a = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public static boolean m3805a() {
        return f1649a;
    }

    /* renamed from: a */
    public static boolean m3804a(Context context) {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return connectivityManager != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null && activeNetworkInfo.isConnected() && activeNetworkInfo.getState() == NetworkInfo.State.CONNECTED;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: b */
    public static void m3798b(String str, Object... objArr) {
        if (f1649a) {
            Log.e(Captcha.TAG, String.format(str, objArr));
        }
    }
}
