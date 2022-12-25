package com.youdao.sdk.app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public class LanguageUtils {
    public static String USER_PROFILE = "aicloud";
    public static Set<String> USER_PROFILE_SET = new HashSet();
    private static Map<String, Language> languageNameMap = new HashMap();
    private static Map<String, Language> languageCodeMap = new HashMap();
    public static Set<String> YOUDAO_OFFLINE_SUPPORT_LANGUAGE_SET = new HashSet();
    public static Set<String> ZHANGYUE_OFFLINE_SUPPORT_LANGUAGE_SET = new HashSet();
    public static Set<String> OPPO_OFFLINE_SUPPORT_LANGUAGE_SET = new HashSet();

    static {
        new String[]{"自动", "中文", "日文", "英文", "韩文", "法文", "俄文", "西班牙文", "葡萄牙文", "越南文", "繁体中文", "印地文"};
        languageNameMap.put("自动", Language.AUTO);
        languageNameMap.put("中文", Language.CHINESE);
        languageNameMap.put("日文", Language.JAPANESE);
        languageNameMap.put("英文", Language.ENGLISH);
        languageNameMap.put("韩文", Language.KOREAN);
        languageNameMap.put("法文", Language.FRENCH);
        languageNameMap.put("西班牙文", Language.SPANISH);
        languageNameMap.put("俄文", Language.RUSSIAN);
        languageNameMap.put("葡萄牙文", Language.PORTUGUESE);
        languageNameMap.put("越南文", Language.Vietnamese);
        languageNameMap.put("繁体中文", Language.TraditionalChinese);
        languageNameMap.put("印地文", Language.INDO);
        languageCodeMap.put(Language.AUTO.getCode(), Language.AUTO);
        languageCodeMap.put(Language.CHINESE.getCode(), Language.CHINESE);
        languageCodeMap.put(Language.JAPANESE.getCode(), Language.JAPANESE);
        languageCodeMap.put(Language.ENGLISH.getCode(), Language.ENGLISH);
        languageCodeMap.put(Language.KOREAN.getCode(), Language.KOREAN);
        languageCodeMap.put(Language.FRENCH.getCode(), Language.FRENCH);
        languageCodeMap.put(Language.SPANISH.getCode(), Language.SPANISH);
        languageCodeMap.put(Language.RUSSIAN.getCode(), Language.RUSSIAN);
        languageCodeMap.put(Language.PORTUGUESE.getCode(), Language.PORTUGUESE);
        languageCodeMap.put(Language.Vietnamese.getCode(), Language.Vietnamese);
        languageCodeMap.put(Language.TraditionalChinese.getCode(), Language.TraditionalChinese);
        languageCodeMap.put(Language.INDO.getCode(), Language.INDO);
        ZHANGYUE_OFFLINE_SUPPORT_LANGUAGE_SET.add("zh-CHS");
        ZHANGYUE_OFFLINE_SUPPORT_LANGUAGE_SET.add("en");
        YOUDAO_OFFLINE_SUPPORT_LANGUAGE_SET.add("zh-CHS");
        YOUDAO_OFFLINE_SUPPORT_LANGUAGE_SET.add("en");
        YOUDAO_OFFLINE_SUPPORT_LANGUAGE_SET.add("ja");
        YOUDAO_OFFLINE_SUPPORT_LANGUAGE_SET.add("ko");
        YOUDAO_OFFLINE_SUPPORT_LANGUAGE_SET.add("fr");
        YOUDAO_OFFLINE_SUPPORT_LANGUAGE_SET.add("es");
        YOUDAO_OFFLINE_SUPPORT_LANGUAGE_SET.add("vi");
        USER_PROFILE_SET.add("oppo");
        USER_PROFILE_SET.add("aicloud");
        USER_PROFILE_SET.add("zhangyue");
        OPPO_OFFLINE_SUPPORT_LANGUAGE_SET.add("en");
        OPPO_OFFLINE_SUPPORT_LANGUAGE_SET.add("hi");
    }

    public static Language getLangByName(String str) {
        return languageNameMap.get(str);
    }

    public static Language getLangByCode(String str) {
        return languageCodeMap.get(str);
    }
}
