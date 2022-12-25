package com.tomatolive.library.utils.language;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import com.blankj.utilcode.util.SPUtils;
import java.util.Locale;
import org.greenrobot.eventbus.EventBus;

/* loaded from: classes4.dex */
public class MultiLanguageUtil {
    public static final String SAVE_LANGUAGE = "MultiLanguageSave";
    private static final String TAG = "MultiLanguageUtil";

    private MultiLanguageUtil() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class SingletonHolder {
        private static final MultiLanguageUtil INSTANCE = new MultiLanguageUtil();

        private SingletonHolder() {
        }
    }

    public static MultiLanguageUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void setConfiguration(Context context) {
        Locale languageLocale = getLanguageLocale();
        Configuration configuration = context.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= 17) {
            configuration.setLocale(languageLocale);
        } else {
            configuration.locale = languageLocale;
        }
        Resources resources = context.getResources();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    private Locale getLanguageLocale() {
        int i = SPUtils.getInstance().getInt(SAVE_LANGUAGE, 0);
        if (i == 0) {
            return getSysLocale();
        }
        if (i == 1) {
            return Locale.ENGLISH;
        }
        if (i == 2) {
            return Locale.SIMPLIFIED_CHINESE;
        }
        if (i == 3) {
            return Locale.TAIWAN;
        }
        if (i == 5) {
            return Locale.JAPAN;
        }
        if (i == 4) {
            return Locale.KOREA;
        }
        getSystemLanguage(getSysLocale());
        return Locale.SIMPLIFIED_CHINESE;
    }

    private String getSystemLanguage(Locale locale) {
        return locale.getLanguage() + "_" + locale.getCountry();
    }

    public Locale getSysLocale() {
        if (Build.VERSION.SDK_INT >= 24) {
            LocaleList localeList = LocaleList.getDefault();
            if (SPUtils.getInstance().getInt(SAVE_LANGUAGE, 0) != 0 && localeList.size() > 1) {
                return localeList.get(1);
            }
            return localeList.get(0);
        }
        return Locale.getDefault();
    }

    public void updateLanguage(Context context, int i) {
        SPUtils.getInstance().put(SAVE_LANGUAGE, i);
        setConfiguration(context);
        EventBus.getDefault().post(new OnChangeLanguageEvent(i));
    }

    public int getLanguageType() {
        int i = SPUtils.getInstance().getInt(SAVE_LANGUAGE, 0);
        if (i == 2) {
            return 2;
        }
        if (i == 3) {
            return 3;
        }
        if (i != 0) {
            return i;
        }
        return 0;
    }

    public Context attachBaseContext(Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return createConfigurationResources(context);
        }
        setConfiguration(context);
        return context;
    }

    @TargetApi(24)
    private static Context createConfigurationResources(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(getInstance().getLanguageLocale());
        return context.createConfigurationContext(configuration);
    }
}
