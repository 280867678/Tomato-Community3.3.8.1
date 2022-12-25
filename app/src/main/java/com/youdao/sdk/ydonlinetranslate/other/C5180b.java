package com.youdao.sdk.ydonlinetranslate.other;

import android.content.Context;
import com.youdao.sdk.app.EncryptHelper;
import com.youdao.sdk.app.HttpHelper;
import com.youdao.sdk.app.YouDaoApplication;
import com.youdao.sdk.ydonlinetranslate.OcrTranslateListener;
import com.youdao.sdk.ydonlinetranslate.OcrTranslateParameters;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import java.util.HashMap;

/* renamed from: com.youdao.sdk.ydonlinetranslate.other.b */
/* loaded from: classes4.dex */
public class C5180b {
    /* renamed from: a */
    public static void m157a(String str, OcrTranslateListener ocrTranslateListener, OcrTranslateParameters ocrTranslateParameters, Context context, String str2) {
        HashMap hashMap = new HashMap();
        String[] generateEncryptV1 = EncryptHelper.generateEncryptV1(ocrTranslateParameters.paramString(context, str));
        hashMap.put("s", generateEncryptV1[0]);
        hashMap.put("et", generateEncryptV1[1]);
        String str3 = YouDaoApplication.isForeignVersion() ? "http://openapi-sg.youdao.com" : "http://nb036x.corp.youdao.com:8681";
        HttpHelper.postRequest(str3 + "/ocrtransopenapi", hashMap, new C5181c(ocrTranslateListener, str, str2), ocrTranslateParameters.getTimeout());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public static TranslateErrorCode m156b(int i) {
        TranslateErrorCode m148a = C5185g.m148a(i);
        TranslateErrorCode translateErrorCode = TranslateErrorCode.UN_SPECIFIC_ERROR;
        if (m148a != translateErrorCode) {
            return m148a;
        }
        if (i == 5001) {
            return TranslateErrorCode.TRANS_UNSUPPORT_OCRTYPE;
        }
        if (i == 5002) {
            return TranslateErrorCode.TRANS_UNSUPPORT_OCR_IMG_TYPE;
        }
        if (i == 5003) {
            return TranslateErrorCode.TRANS_UNSUPPORT_OCR_LANG_TYPE;
        }
        if (i == 5004) {
            return TranslateErrorCode.TRANS_QUERY_IMAGE_TOO_LARGE;
        }
        if (i == 5005) {
            return TranslateErrorCode.TRANS_UNSUPPORT_OCR_IMG_FILE;
        }
        if (i == 5006) {
            return TranslateErrorCode.TRANS_EMPTY_IMG_FILE;
        }
        if (i == 5201) {
            return TranslateErrorCode.TRANS_IMG_DECRYPT_ERROR;
        }
        if (i == 5301) {
            return TranslateErrorCode.TRANS_OCR_PARA_QUERY_FAILED;
        }
        if (i == 5411) {
            return TranslateErrorCode.TRANS_OCR_MAX_QUERY_COUNT_ERROR;
        }
        return i == 5412 ? TranslateErrorCode.TRANS_OCR_MAX_QUERY_SIZE_ERROR : translateErrorCode;
    }
}
