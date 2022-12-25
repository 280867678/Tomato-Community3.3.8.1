package com.youdao.sdk.ydonlinetranslate;

import android.content.Context;
import com.youdao.sdk.app.YouDaoApplication;
import com.youdao.sdk.common.YouDaoLog;
import com.youdao.sdk.ydonlinetranslate.other.C5180b;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;

/* loaded from: classes4.dex */
public class OcrTranslate {
    OcrTranslateParameters parameters;

    private OcrTranslate(OcrTranslateParameters ocrTranslateParameters) {
        this.parameters = ocrTranslateParameters;
    }

    public static OcrTranslate getInstance(OcrTranslateParameters ocrTranslateParameters) {
        return new OcrTranslate(ocrTranslateParameters);
    }

    public void lookup(String str, String str2, OcrTranslateListener ocrTranslateListener) {
        if (ocrTranslateListener == null) {
            YouDaoLog.m167e("translate result callback is null listener!");
            return;
        }
        Context applicationContext = YouDaoApplication.getApplicationContext();
        if (applicationContext == null) {
            YouDaoLog.m167e("This application may be not init,please use YouDaoApplication init");
            ocrTranslateListener.onError(TranslateErrorCode.CONTEXT_ERROR, str2);
        } else if (this.parameters.getTo() == LanguageOcrTranslate.AUTO) {
            ocrTranslateListener.onError(TranslateErrorCode.TRANS_LANGUAGE_ERROR, str2);
        } else {
            C5180b.m157a(str, ocrTranslateListener, this.parameters, applicationContext, str2);
        }
    }
}
