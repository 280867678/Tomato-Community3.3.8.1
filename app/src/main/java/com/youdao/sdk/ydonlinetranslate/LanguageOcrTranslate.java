package com.youdao.sdk.ydonlinetranslate;

/* loaded from: classes4.dex */
public enum LanguageOcrTranslate {
    AUTO("自动", "auto"),
    CHINESE("中文", "zh-CHS"),
    ENGLISH("英文", "en"),
    KOREAN("韩文", "ko"),
    FRENCH("法文", "fr"),
    PORTUGUESE("葡萄牙文", "pt"),
    RUSSIAN("俄文", "ru"),
    JAPANESE("日文", "ja"),
    SPANISH("西班牙文", "es"),
    TraditionalChinese("繁体中文", "zh-CHT"),
    POLISH("波兰文", "pl"),
    DANISH("丹麦文", "da"),
    GERMAN("德文", "de"),
    FINNISH("芬兰文", "fi"),
    NEDERLANDS("荷兰文", "nl"),
    NORWAY("挪威文", "no"),
    SWEDISH("瑞典文", "sv"),
    ITALIAN("意大利文", "it"),
    TURKEY("土耳其文", "tr"),
    CZECH("希腊文", "el"),
    GREEK("捷克文", "cs"),
    HUNGARY("匈牙利文", "hu");
    
    private final String code;
    private final String name;

    LanguageOcrTranslate(String str, String str2) {
        this.name = str;
        this.code = str2;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public static LanguageOcrTranslate getLanguage(String str) {
        if (CHINESE.getCode().equals(str)) {
            return CHINESE;
        }
        if (KOREAN.getCode().equals(str)) {
            return KOREAN;
        }
        if (FRENCH.getCode().equals(str)) {
            return FRENCH;
        }
        if (PORTUGUESE.getCode().equals(str)) {
            return PORTUGUESE;
        }
        if (RUSSIAN.getCode().equals(str)) {
            return RUSSIAN;
        }
        if (JAPANESE.getCode().equals(str)) {
            return JAPANESE;
        }
        if (SPANISH.getCode().equals(str)) {
            return SPANISH;
        }
        return ENGLISH;
    }
}
