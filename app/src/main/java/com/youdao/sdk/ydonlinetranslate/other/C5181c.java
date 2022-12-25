package com.youdao.sdk.ydonlinetranslate.other;

import com.youdao.sdk.app.HttpErrorCode;
import com.youdao.sdk.app.HttpHelper;
import com.youdao.sdk.common.YouDaoLog;
import com.youdao.sdk.ydonlinetranslate.OCRTranslateResult;
import com.youdao.sdk.ydonlinetranslate.OcrTranslateListener;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.youdao.sdk.ydonlinetranslate.other.c */
/* loaded from: classes4.dex */
public final class C5181c implements HttpHelper.HttpJsonListener {

    /* renamed from: a */
    final /* synthetic */ OcrTranslateListener f5937a;

    /* renamed from: b */
    final /* synthetic */ String f5938b;

    /* renamed from: c */
    final /* synthetic */ String f5939c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public C5181c(OcrTranslateListener ocrTranslateListener, String str, String str2) {
        this.f5937a = ocrTranslateListener;
        this.f5938b = str;
        this.f5939c = str2;
    }

    @Override // com.youdao.sdk.app.HttpHelper.HttpJsonListener
    public void onResult(String str) {
        TranslateErrorCode m156b;
        if (this.f5937a != null) {
            OCRTranslateResult oCRTranslateResult = new OCRTranslateResult(str);
            C5179a.m161a(oCRTranslateResult);
            if (oCRTranslateResult.success()) {
                this.f5937a.onResult(oCRTranslateResult, this.f5938b, this.f5939c);
                return;
            }
            OcrTranslateListener ocrTranslateListener = this.f5937a;
            m156b = C5180b.m156b(oCRTranslateResult.getErrorCode());
            ocrTranslateListener.onError(m156b, this.f5939c);
        }
    }

    @Override // com.youdao.sdk.app.HttpHelper.HttpJsonListener
    public void onError(HttpErrorCode httpErrorCode) {
        YouDaoLog.m165w("recogniz voice  http error:" + httpErrorCode.name());
        OcrTranslateListener ocrTranslateListener = this.f5937a;
        if (ocrTranslateListener != null) {
            ocrTranslateListener.onError(TranslateErrorCode.HTTP_REQUEST_ERROR, this.f5939c);
        }
    }
}
