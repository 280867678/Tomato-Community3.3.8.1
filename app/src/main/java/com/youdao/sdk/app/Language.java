package com.youdao.sdk.app;

/* loaded from: classes4.dex */
public enum Language {
    AUTO("自动", "auto", "auto"),
    CHINESE("中文", "zh-CHS", "chn"),
    ENGLISH("英文", "en", "eng"),
    KOREAN("韩文", "ko", "ko"),
    FRENCH("法文", "fr", "fr"),
    PORTUGUESE("葡萄牙文", "pt", "pt"),
    RUSSIAN("俄文", "ru", "ru"),
    JAPANESE("日文", "ja", "jap"),
    SPANISH("西班牙文", "es", "es"),
    Vietnamese("越南文", "vi", "vi"),
    TraditionalChinese("繁体中文", "zh-CHT", "zh-CHT"),
    ENCH("中英", "ench", "ench"),
    INDO("印地文", "hi", "hi");
    
    private final String code;
    private final String name;
    private final String voiceCode;

    Language(String str, String str2, String str3) {
        this.name = str;
        this.code = str2;
        this.voiceCode = str3;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public String getVoiceCode() {
        return this.voiceCode;
    }

    public static Language getLanguage(String str) {
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
        if (Vietnamese.getCode().equals(str)) {
            return Vietnamese;
        }
        if (TraditionalChinese.getCode().equals(str)) {
            return TraditionalChinese;
        }
        if (INDO.getCode().equals(str)) {
            return INDO;
        }
        return ENGLISH;
    }
}
