package com.youdao.sdk.ydonlinetranslate;

import com.youdao.sdk.ydtranslate.TranslateErrorCode;

/* loaded from: classes4.dex */
public interface OcrTranslateListener {
    void onError(TranslateErrorCode translateErrorCode, String str);

    void onResult(OCRTranslateResult oCRTranslateResult, String str, String str2);
}
