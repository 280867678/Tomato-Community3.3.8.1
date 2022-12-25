package com.youdao.sdk.ydonlinetranslate;

import android.content.Context;
import com.youdao.sdk.app.YouDaoApplication;
import com.youdao.sdk.common.YouDaoLog;
import com.youdao.sdk.ydonlinetranslate.other.C5185g;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;
import com.youdao.sdk.ydtranslate.TranslateParameters;

/* loaded from: classes4.dex */
public class Translator {
    TranslateParameters parameters;

    private Translator(TranslateParameters translateParameters) {
        this.parameters = translateParameters;
    }

    public static Translator getInstance(TranslateParameters translateParameters) {
        return new Translator(translateParameters);
    }

    public synchronized void lookup(String str, String str2, TranslateListener translateListener) {
        if (translateListener == null) {
            YouDaoLog.m167e("translate result callback is null listener!");
        } else if (translateListener == null) {
            YouDaoLog.m167e("translate result callback is null listener!");
            translateListener.onError(TranslateErrorCode.INPUT_PARAM_ILLEGAL, str2);
        } else {
            Context applicationContext = YouDaoApplication.getApplicationContext();
            if (applicationContext == null) {
                YouDaoLog.m167e("This application may be not init,please use YouDaoApplication init");
                return;
            }
            this.parameters.getFrom();
            this.parameters.getTo();
            C5185g.m147a(str, translateListener, this.parameters, applicationContext, str2);
        }
    }
}
