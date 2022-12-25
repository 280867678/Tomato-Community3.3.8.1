package com.youdao.sdk.ydonlinetranslate.other;

import com.youdao.sdk.app.HttpErrorCode;
import com.youdao.sdk.app.HttpHelper;
import com.youdao.sdk.common.YouDaoLog;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.youdao.sdk.ydonlinetranslate.other.h */
/* loaded from: classes4.dex */
public final class C5186h implements HttpHelper.HttpJsonListener {

    /* renamed from: a */
    final /* synthetic */ String f5947a;

    /* renamed from: b */
    final /* synthetic */ TranslateListener f5948b;

    /* renamed from: c */
    final /* synthetic */ String f5949c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public C5186h(String str, TranslateListener translateListener, String str2) {
        this.f5947a = str;
        this.f5948b = translateListener;
        this.f5949c = str2;
    }

    @Override // com.youdao.sdk.app.HttpHelper.HttpJsonListener
    public void onResult(String str) {
        Translate m146a = C5185g.m146a(str, this.f5947a);
        if (m146a == null || !m146a.success()) {
            this.f5948b.onError(C5185g.m148a(m146a == null ? 1 : m146a.getErrorCode()), this.f5949c);
        } else {
            this.f5948b.onResult(m146a, this.f5947a, this.f5949c);
        }
    }

    @Override // com.youdao.sdk.app.HttpHelper.HttpJsonListener
    public void onError(HttpErrorCode httpErrorCode) {
        YouDaoLog.m167e("query word " + this.f5947a + " http error:" + httpErrorCode.name());
        TranslateListener translateListener = this.f5948b;
        if (translateListener != null) {
            translateListener.onError(TranslateErrorCode.HTTP_REQUEST_ERROR, this.f5949c);
        }
    }
}
