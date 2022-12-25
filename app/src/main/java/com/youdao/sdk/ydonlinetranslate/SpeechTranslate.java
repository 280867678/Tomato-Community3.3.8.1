package com.youdao.sdk.ydonlinetranslate;

import android.content.Context;
import com.youdao.sdk.app.YouDaoApplication;
import com.youdao.sdk.common.YouDaoLog;
import com.youdao.sdk.ydonlinetranslate.other.C5183e;
import com.youdao.sdk.ydtranslate.TranslateListener;

/* loaded from: classes4.dex */
public class SpeechTranslate {
    SpeechTranslateParameters parameters;

    private SpeechTranslate(SpeechTranslateParameters speechTranslateParameters) {
        this.parameters = speechTranslateParameters;
    }

    public static SpeechTranslate getInstance(SpeechTranslateParameters speechTranslateParameters) {
        return new SpeechTranslate(speechTranslateParameters);
    }

    public void lookup(String str, String str2, TranslateListener translateListener) {
        if (translateListener == null) {
            YouDaoLog.m167e("translate result callback is null listener!");
            return;
        }
        Context applicationContext = YouDaoApplication.getApplicationContext();
        if (applicationContext == null) {
            YouDaoLog.m167e("This application may be not init,please use YouDaoApplication init");
        }
        C5183e.m150a(str, translateListener, this.parameters, applicationContext, str2);
    }
}
