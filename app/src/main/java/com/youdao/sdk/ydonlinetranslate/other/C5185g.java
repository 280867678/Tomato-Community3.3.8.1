package com.youdao.sdk.ydonlinetranslate.other;

import android.content.Context;
import android.text.TextUtils;
import com.youdao.sdk.app.EncryptHelper;
import com.youdao.sdk.app.HttpHelper;
import com.youdao.sdk.app.JsonHelper;
import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.YouDaoApplication;
import com.youdao.sdk.common.YouDaoLog;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;
import com.youdao.sdk.ydtranslate.TranslateParameters;
import com.youdao.sdk.ydtranslate.WebExplain;
import com.zzz.ipfssdk.callback.exception.CodeState;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: com.youdao.sdk.ydonlinetranslate.other.g */
/* loaded from: classes4.dex */
public class C5185g {
    /* renamed from: a */
    public static void m147a(String str, TranslateListener translateListener, TranslateParameters translateParameters, Context context, String str2) {
        HashMap hashMap = new HashMap();
        String[] generateEncryptV1 = EncryptHelper.generateEncryptV1(translateParameters.paramString(context, str));
        hashMap.put("s", generateEncryptV1[0]);
        hashMap.put("et", generateEncryptV1[1]);
        String str3 = (YouDaoApplication.isForeignVersion() ? "http://openapi-sg.youdao.com" : "http://openapi.youdao.com") + "/openapi?";
        if (translateParameters.getFrom() == Language.INDO || translateParameters.getTo() == Language.INDO) {
            str3 = "http://fanyi-test.youdao.com/translate_a?q=" + URLEncoder.encode(str) + "&from=" + translateParameters.getFrom().getCode() + "&to=" + translateParameters.getTo().getCode();
        }
        HttpHelper.postRequest(str3, hashMap, new C5186h(str, translateListener, str2), translateParameters.getTimeout());
    }

    /* renamed from: a */
    public static Translate m146a(String str, String str2) {
        Translate translate = new Translate();
        try {
            JSONObject jSONObject = new JSONObject(str);
            translate.setTranslations(JsonHelper.parseList(jSONObject, "translation"));
            translate.setErrorCode(JsonHelper.parseValue(jSONObject, "errorCode", TranslateErrorCode.JSON_PARSE_ERROR.getCode()));
            translate.setQuery(JsonHelper.parseValue(jSONObject, "query", str2));
            JSONObject parseJSONObject = JsonHelper.parseJSONObject(jSONObject, "webdict");
            if (parseJSONObject != null) {
                translate.setDeeplink(JsonHelper.parseValue(parseJSONObject, "url", ""));
            }
            JSONObject parseJSONObject2 = JsonHelper.parseJSONObject(jSONObject, "dict");
            if (parseJSONObject2 != null) {
                translate.setDictDeeplink(JsonHelper.parseValue(parseJSONObject2, "url", ""));
            }
            String parseValue = JsonHelper.parseValue(jSONObject, "l", "");
            if (!TextUtils.isEmpty(parseValue)) {
                String[] split = parseValue.split("2");
                if (split.length == 2) {
                    translate.setFrom(split[0]);
                    translate.setTo(split[1]);
                    Language language = Language.getLanguage(translate.getFrom());
                    Language language2 = Language.getLanguage(translate.getTo());
                    if (language == Language.CHINESE) {
                        if (language2 == Language.CHINESE) {
                            translate.setLe(Language.ENGLISH.getVoiceCode());
                        } else {
                            translate.setLe(language2.getVoiceCode());
                        }
                    } else {
                        translate.setLe(language.getVoiceCode());
                    }
                }
            }
            translate.setSpeakUrl(JsonHelper.parseValue(jSONObject, "speakUrl", ""));
            translate.setResultSpeakUrl(JsonHelper.parseValue(jSONObject, "tSpeakUrl", ""));
            if (!jSONObject.isNull("basic")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("basic");
                translate.setPhonetic(JsonHelper.parseValue(jSONObject2, "phonetic", ""));
                translate.setUkPhonetic(JsonHelper.parseValue(jSONObject2, "uk-phonetic", ""));
                translate.setUsPhonetic(JsonHelper.parseValue(jSONObject2, "us-phonetic", ""));
                translate.setUSSpeakUrl(JsonHelper.parseValue(jSONObject2, "us-speech", ""));
                translate.setUKSpeakUrl(JsonHelper.parseValue(jSONObject2, "uk-speech", ""));
                translate.setExplains(JsonHelper.parseList(jSONObject2, "explains"));
            }
            if (!jSONObject.isNull("web")) {
                JSONArray jSONArray = jSONObject.getJSONArray("web");
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                    WebExplain webExplain = new WebExplain();
                    webExplain.setKey(JsonHelper.parseValue(jSONObject3, "key", ""));
                    webExplain.setMeans(JsonHelper.parseList(jSONObject3, "value"));
                    arrayList.add(webExplain);
                }
                translate.setWebExplains(arrayList);
            }
        } catch (Exception e) {
            YouDaoLog.m166e(CodeState.MSGS.MSG_JSON_PARSE_ERROR, e);
        }
        return translate;
    }

    /* renamed from: a */
    public static TranslateErrorCode m148a(int i) {
        if (i == 1) {
            return TranslateErrorCode.HTTP_REQUEST_ERROR;
        }
        if (i == 100) {
            return TranslateErrorCode.INPUT_PARAM_ILLEGAL;
        }
        if (i == 101) {
            return TranslateErrorCode.INPUT_PARAM_ILLEGAL_MUST;
        }
        if (i == 102) {
            return TranslateErrorCode.INPUT_PARAM_ILLEGAL_NOT_SPPORT_LANG;
        }
        if (i == 103) {
            return TranslateErrorCode.INPUT_PARAM_ILLEGAL_TEXT_TOO_LONG;
        }
        if (i == 104) {
            return TranslateErrorCode.INPUT_PARAM_ILLEGAL_VER_NOT_SUPPORTED;
        }
        if (i == 105) {
            return TranslateErrorCode.INPUT_PARAM_ILLEGAL_SIGN_NOT_SUPPORTED;
        }
        if (i == 106) {
            return TranslateErrorCode.INPUT_PARAM_ILLEGAL_RESPONSE;
        }
        if (i == 107) {
            return TranslateErrorCode.INPUT_PARAM_ILLEGAL_ENCRYPT;
        }
        if (i == 108) {
            return TranslateErrorCode.INPUT_PARAM_ILLEGAL_KEY_INVALID;
        }
        if (i == 109) {
            return TranslateErrorCode.INVALID_BATCH_LOG;
        }
        if (i == 110) {
            return TranslateErrorCode.INVALID_INSTANCE_KEY;
        }
        if (i == 111) {
            return TranslateErrorCode.INVALID_DEVELOPERID;
        }
        if (i == 112) {
            return TranslateErrorCode.INVALID_PRODUCTID;
        }
        if (i == 113) {
            return TranslateErrorCode.INVALID_TEXTS_INPUT;
        }
        if (i == 201) {
            return TranslateErrorCode.INPUT_DECRYPTION_ERROR;
        }
        if (i == 202) {
            return TranslateErrorCode.INPUT_DECRYPTION_ERROR_SIGN;
        }
        if (i == 203) {
            return TranslateErrorCode.INVALID_IP;
        }
        if (i == 301) {
            return TranslateErrorCode.SERVER_LOOKUP_DICT_ERROR;
        }
        if (i == 302) {
            return TranslateErrorCode.SERVER_LOOKUP_MINORITY_ERROR;
        }
        if (i == 303) {
            return TranslateErrorCode.SERVER_LOOKUP_ERROR;
        }
        if (i == 401) {
            return TranslateErrorCode.ACCOUNT_OVERDUE_BILL;
        }
        if (i == 411) {
            return TranslateErrorCode.TRANS_MAX_QUERY_COUNT_ERROR;
        }
        if (i == 412) {
            return TranslateErrorCode.TRANS_MAX_QUERY_LENGTH_ERROR;
        }
        if (i == 2003) {
            return TranslateErrorCode.TRANS_LANGUAGE_ERROR;
        }
        if (i == 2004) {
            return TranslateErrorCode.TRANS_CHARACTER_ERROR;
        }
        return TranslateErrorCode.UN_SPECIFIC_ERROR;
    }
}
