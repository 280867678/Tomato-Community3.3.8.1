package com.tomatolive.library.utils;

import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;
import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.common.Constants;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;
import com.youdao.sdk.ydtranslate.TranslateParameters;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class TranslationUtils {

    /* renamed from: CH */
    public static final String f5876CH = "zh-CN";
    public static final String CH_HK = "zh-HK";
    public static final String CH_TW = "zh-TW";
    public static final String EN_US = "en-US";
    public static final String ES_ES = "es-ES";
    public static final String FR_FR = "fr-FR";
    public static final String JA_JP = "ja-JP";
    public static final String KO_KR = "ko-KR";
    public static final String PT_PT = "pt-PT";
    public static final String RU_RU = "ru-RU";
    public static final String VI_VN = "vi-VN";

    /* loaded from: classes4.dex */
    public interface OnTransListener {
        void onSuc(String str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String listStr(List<String> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null) {
            for (String str : list) {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    public static void translationFromText(final String str, final OnTransListener onTransListener) {
        Language langByName = LanguageUtils.getLangByName(Language.AUTO.getName());
        Translator.getInstance(new TranslateParameters.Builder().source("youdao").from(langByName).m145to(LanguageUtils.getLangByName(getLocalLanguage())).sound(Constants.SOUND_OUTPUT_MP3).voice(Constants.VOICE_BOY_UK).timeout(3000).build()).lookup(str, "requestId", new TranslateListener() { // from class: com.tomatolive.library.utils.TranslationUtils.1
            private String meansStr;
            private String translateStr;

            @Override // com.youdao.sdk.ydtranslate.TranslateListener
            public void onResult(List<Translate> list, List<String> list2, List<TranslateErrorCode> list3, String str2) {
            }

            @Override // com.youdao.sdk.ydtranslate.TranslateListener
            public void onResult(Translate translate, String str2, String str3) {
                if (OnTransListener.this != null) {
                    try {
                        this.translateStr = TranslationUtils.listStr(translate.getTranslations());
                        this.meansStr = TranslationUtils.listStr(translate.getExplains());
                        if (TextUtils.isEmpty(this.translateStr) && TextUtils.isEmpty(this.meansStr)) {
                            return;
                        }
                        OnTransListener.this.onSuc(TextUtils.isEmpty(this.translateStr) ? this.meansStr : this.translateStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override // com.youdao.sdk.ydtranslate.TranslateListener
            public void onError(TranslateErrorCode translateErrorCode, String str2) {
                OnTransListener onTransListener2 = OnTransListener.this;
                if (onTransListener2 != null) {
                    onTransListener2.onSuc(str);
                }
            }
        });
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x005b, code lost:
        if (r1.equals(com.tomatolive.library.utils.TranslationUtils.f5876CH) != false) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static String getLocalLanguage() {
        Locale locale;
        String name = Language.CHINESE.getName();
        char c = 0;
        if (Build.VERSION.SDK_INT >= 24) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        String str = locale.getLanguage() + "-" + locale.getCountry();
        switch (str.hashCode()) {
            case 96598594:
                if (str.equals(EN_US)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 96747053:
                if (str.equals(ES_ES)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 97640813:
                if (str.equals(FR_FR)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 100828572:
                if (str.equals(JA_JP)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 102169200:
                if (str.equals(KO_KR)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 106935917:
                if (str.equals(PT_PT)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 108812813:
                if (str.equals(RU_RU)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 112149522:
                if (str.equals(VI_VN)) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 115813226:
                break;
            case 115813378:
                if (str.equals(CH_HK)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 115813762:
                if (str.equals(CH_TW)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return Language.CHINESE.getName();
            case 1:
            case 2:
                return Language.TraditionalChinese.getName();
            case 3:
                return Language.JAPANESE.getName();
            case 4:
                return Language.ENGLISH.getName();
            case 5:
                return Language.KOREAN.getName();
            case 6:
                return Language.FRENCH.getName();
            case 7:
                return Language.RUSSIAN.getName();
            case '\b':
                return Language.PORTUGUESE.getName();
            case '\t':
                return Language.SPANISH.getName();
            case '\n':
                return Language.Vietnamese.getName();
            default:
                return name;
        }
    }
}
