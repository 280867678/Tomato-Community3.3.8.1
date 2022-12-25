package com.youdao.sdk.ydonlinetranslate.other;

import com.youdao.sdk.app.HttpErrorCode;
import com.youdao.sdk.app.HttpHelper;
import com.youdao.sdk.common.YouDaoLog;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.youdao.sdk.ydonlinetranslate.other.f */
/* loaded from: classes4.dex */
public final class C5184f implements HttpHelper.HttpJsonListener {

    /* renamed from: a */
    final /* synthetic */ TranslateListener f5944a;

    /* renamed from: b */
    final /* synthetic */ String f5945b;

    /* renamed from: c */
    final /* synthetic */ String f5946c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public C5184f(TranslateListener translateListener, String str, String str2) {
        this.f5944a = translateListener;
        this.f5945b = str;
        this.f5946c = str2;
    }

    @Override // com.youdao.sdk.app.HttpHelper.HttpJsonListener
    public void onResult(String str) {
        TranslateErrorCode m149b;
        if (this.f5944a != null) {
            Translate m146a = C5185g.m146a(str, null);
            if (m146a.success()) {
                this.f5944a.onResult(m146a, this.f5945b, this.f5946c);
                return;
            }
            TranslateListener translateListener = this.f5944a;
            m149b = C5183e.m149b(m146a.getErrorCode());
            translateListener.onError(m149b, this.f5946c);
        }
    }

    @Override // com.youdao.sdk.app.HttpHelper.HttpJsonListener
    public void onError(HttpErrorCode httpErrorCode) {
        YouDaoLog.m165w("recogniz voice  http error:" + httpErrorCode.name());
        TranslateListener translateListener = this.f5944a;
        if (translateListener != null) {
            translateListener.onError(TranslateErrorCode.HTTP_REQUEST_ERROR, this.f5946c);
        }
    }
}
