package com.youdao.sdk.ydtranslate;

import java.util.List;

/* loaded from: classes4.dex */
public interface TranslateListener {
    void onError(TranslateErrorCode translateErrorCode, String str);

    void onResult(Translate translate, String str, String str2);

    void onResult(List<Translate> list, List<String> list2, List<TranslateErrorCode> list3, String str);
}
