package com.youdao.sdk.ydonlinetranslate.other;

import android.content.Context;
import com.youdao.sdk.app.EncryptHelper;
import com.youdao.sdk.app.HttpHelper;
import com.youdao.sdk.app.YouDaoApplication;
import com.youdao.sdk.ydonlinetranslate.SpeechTranslateParameters;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;
import java.util.HashMap;

/* renamed from: com.youdao.sdk.ydonlinetranslate.other.e */
/* loaded from: classes4.dex */
public class C5183e {
    /* renamed from: a */
    public static void m150a(String str, TranslateListener translateListener, SpeechTranslateParameters speechTranslateParameters, Context context, String str2) {
        HashMap hashMap = new HashMap();
        String[] generateEncryptV1 = EncryptHelper.generateEncryptV1(speechTranslateParameters.paramString(context, str));
        hashMap.put("s", generateEncryptV1[0]);
        hashMap.put("et", generateEncryptV1[1]);
        String str3 = YouDaoApplication.isForeignVersion() ? "http://openapi-sg.youdao.com" : "http://openapi.youdao.com";
        HttpHelper.postRequest(str3 + "/speechtransopenapi", hashMap, new C5184f(translateListener, str, str2), speechTranslateParameters.getTimeout());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public static TranslateErrorCode m149b(int i) {
        TranslateErrorCode m148a = C5185g.m148a(i);
        TranslateErrorCode translateErrorCode = TranslateErrorCode.UN_SPECIFIC_ERROR;
        if (m148a != translateErrorCode) {
            return m148a;
        }
        if (i == 2005) {
            return TranslateErrorCode.TRANS_SPEECH_FORMAT_ERROR;
        }
        if (i == 2006) {
            return TranslateErrorCode.TRANS_SPEECH_SOUND_ERROR;
        }
        if (i == 2201) {
            return TranslateErrorCode.TRANS_SPEECH_DECRYPT_ERROR;
        }
        if (i == 2301) {
            return TranslateErrorCode.TRANS_SPEECH_SERVER_ERROR;
        }
        if (i == 2411) {
            return TranslateErrorCode.TRANS_SPEECH_FREQUENCY_ERROR;
        }
        if (i == 2412) {
            return TranslateErrorCode.TRANS_SPEECH_MAX_QUERY_LENGTH_ERROR;
        }
        if (i == 3001) {
            return TranslateErrorCode.TRANS_SPEECH_SOUND_FORMAT_ERROR;
        }
        if (i == 3002) {
            return TranslateErrorCode.TRANS_SPEECH_SOUND_RATE_ERROR;
        }
        if (i == 3003) {
            return TranslateErrorCode.TRANS_SPEECH_SOUND_CHANNEL_ERROR;
        }
        if (i == 3004) {
            return TranslateErrorCode.TRANS_SPEECH_SOUND_UPLOADTYPE_ERROR;
        }
        if (i == 3005) {
            return TranslateErrorCode.TRANS_SPEECH_SOUND_LANGUAGE_ERROR;
        }
        if (i == 3006) {
            return TranslateErrorCode.TRANS_SPEECH_SOUND_RECOGNIZE_ERROR;
        }
        if (i == 3007) {
            return TranslateErrorCode.TRANS_SPEECH_SOUND_LARGE_ERROR;
        }
        if (i == 3008) {
            return TranslateErrorCode.TRANS_SPEECH_SOUND_LONG_ERROR;
        }
        if (i == 3009) {
            return TranslateErrorCode.TRANS_SPEECH_SOUND_VOICE_TYPE_ERROR;
        }
        if (i == 3201) {
            return TranslateErrorCode.TRANS_SPEECH_SOUND_DECRYPT_ERROR;
        }
        if (i == 3301) {
            return TranslateErrorCode.TRANS_SPEECH_RECOGNIZE_ERROR;
        }
        if (i == 3302) {
            return TranslateErrorCode.TRANS_SPEECH_TRANSLATE_ERROR;
        }
        if (i == 3303) {
            return TranslateErrorCode.TRANS_SPEECH_TRANSLATE_SERVER_ERROR;
        }
        if (i == 3411) {
            return TranslateErrorCode.TRANS_SPEECH_TRANSLATE_FREQUENCY_ERROR;
        }
        return i == 3412 ? TranslateErrorCode.TRANS_SPEECH_TRANSLATE_MAX_QUERY_LENGTH_ERROR : translateErrorCode;
    }
}
